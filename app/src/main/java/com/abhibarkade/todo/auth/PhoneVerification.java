package com.abhibarkade.todo.auth;

import static com.abhibarkade.todo.auth.helper.DB.USER;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abhibarkade.todo.R;
import com.abhibarkade.todo.auth.helper.DB;
import com.abhibarkade.todo.databinding.ActivityPhoneVerificationBinding;
import com.abhibarkade.todo.pojo.POJO_User;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {

    ActivityPhoneVerificationBinding binding;

    String verificationToken;
    PhoneAuthProvider.ForceResendingToken resendToken;
    String selectedCountryCode = "+91";


    private FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    ProgressDialog dialog;

    CountDownTimer countDownTimer;
    String currentTime = "";
    boolean isTimerRunning = false;

    POJO_User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Extras
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                user = getIntent().getSerializableExtra("User", POJO_User.class);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        // Dialog
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        listeners();
    }

    void listeners() {
        binding.txtSendOTP.setOnClickListener(view -> {
            if (isTimerRunning) {
                Toast.makeText(this, "Please try again after " + currentTime + " seconds", Toast.LENGTH_SHORT).show();
            } else {
                DB.firestore.collection(USER)
                        .whereEqualTo("phone", binding.phone.getText().toString())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.getResult().isEmpty()) {
                                dialog.show();
                                sendOTP();
                            } else {
                                Toast.makeText(this, "Phone number already Used!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(PhoneVerification.this, "FAILED!!", Toast.LENGTH_SHORT).show());

            }
        });

        binding.btnVerify.setOnClickListener(view -> {
            dialog.show();
            verifyOTP();

        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneVerification.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String verificationCode,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                verificationToken = verificationCode;
                resendToken = token;

                if (dialog.isShowing())
                    dialog.dismiss();
                if (!isTimerRunning)
                    startCountdownTimer();
                Toast.makeText(PhoneVerification.this, "OTP Sent", Toast.LENGTH_SHORT).show();
            }
        };
    }

    void sendOTP() {
        dialog.show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(selectedCountryCode + "" + binding.phone.getText())
                        .setTimeout(30L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void resendVerificationCode(PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(selectedCountryCode + "" + binding.phone.getText())
                        .setTimeout(30L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    void verifyOTP() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationToken, binding.otp.getText().toString());
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        Toast.makeText(PhoneVerification.this, "Phone Verified" + (user != null), Toast.LENGTH_SHORT).show();
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(PhoneVerification.this, "Phone verification Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isTimerRunning = true;
                long seconds = millisUntilFinished / 1000;
                currentTime = "" + seconds;
                binding.txtSendOTP.setText(getString(R.string.resendOTPTimer, seconds));
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                binding.txtSendOTP.setText(getString(R.string.resendOTP));
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop the timer when the activity is stopped to prevent it from running in the background
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
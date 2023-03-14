package com.abhibarkade.todo.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abhibarkade.todo.R;
import com.abhibarkade.todo.databinding.ActivityPhoneVerificationBinding;
import com.abhibarkade.todo.pojo.POJO_User;
import com.abhibarkade.todo.utils.dialogs.MessageDialog;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    boolean isResendOTP;
    POJO_User user;
    SmsRetrieverClient smsRetrieverClient;
    ReadOTP smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        isResendOTP = false;

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

        startSmsUserConsent();
        listeners();
    }

    void listeners() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                MessageDialog.showDialog(PhoneVerification.this, R.raw.otp_sent, "Please enter valid phone number", "Try Again");
                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String verificationCode,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                verificationToken = verificationCode;
                resendToken = token;
                isResendOTP = true;

                if (dialog.isShowing())
                    dialog.dismiss();
                if (!isTimerRunning) {
                    startCountdownTimer();
                    MessageDialog.showDialog(PhoneVerification.this, R.raw.otp_sent, "OTP is sent to +91 " + binding.phone.getText(), "Continue");
                }
            }
        };

        binding.txtSendOTP.setOnClickListener(view -> {
            if (isTimerRunning) {
                Toast.makeText(this, "Please try again after " + currentTime + " seconds", Toast.LENGTH_SHORT).show();
            } else {
                dialog.show();
                sendOTP();
//                DB.firestore.collection(USER)
//                        .whereEqualTo("phone", binding.phone.getText().toString())
//                        .get()
//                        .addOnCompleteListener(task -> {
//                            if (task.getResult().isEmpty()) {
//                                if (!isResendOTP)
//                                    sendOTP();
//                                else
//                                    resendVerificationCode();
//                            } else {
//                                if(dialog.isShowing())
//                                    dialog.dismiss();
//                                Toast.makeText(this, "Phone number already Used!!", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(e -> Toast.makeText(PhoneVerification.this, "FAILED!!", Toast.LENGTH_SHORT).show());

            }
        });

        binding.btnVerify.setOnClickListener(view -> {
            dialog.show();
            verifyOTP();
        });
    }

    void sendOTP() {
        dialog.show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(selectedCountryCode + "" + binding.phone.getText())
                        .setTimeout(0L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void resendVerificationCode() {
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
        Toast.makeText(this, "HIT", Toast.LENGTH_SHORT).show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "SIGN IN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        user.setPhone(binding.phone.getText().toString());
                        //DB.addUser(this, user);
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
        if (countDownTimer != null)
            countDownTimer.cancel();

        unregisterReceiver(smsBroadcastReceiver);
    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new ReadOTP();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new ReadOTP.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Pattern pattern = Pattern.compile("(|^)\\d{6}");
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    binding.otp.setText(matcher.group(0));
                }
            }
        }
    }
}
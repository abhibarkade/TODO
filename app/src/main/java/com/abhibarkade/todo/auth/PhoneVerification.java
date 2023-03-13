package com.abhibarkade.todo.auth;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abhibarkade.todo.databinding.ActivityPhoneVerificationBinding;
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

    String verificationId;

    boolean flg = false;
    private FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.btnContinue.setOnClickListener(view -> sendOTP());

        binding.btnVerify.setOnClickListener(view -> {
            Toast.makeText(this, "HIT", Toast.LENGTH_SHORT).show();
            verifyOTP();
        });
    }

    void sendOTP() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                Toast.makeText(PhoneVerification.this, "Phone Verified", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneVerification.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationCode,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                verificationId = verificationCode;
                Toast.makeText(PhoneVerification.this, "OTP Sent", Toast.LENGTH_SHORT).show();
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+918799861500")
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    void verifyOTP() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, "111111");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        Toast.makeText(PhoneVerification.this, "Success : " + (user != null), Toast.LENGTH_SHORT).show();
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(PhoneVerification.this, "Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
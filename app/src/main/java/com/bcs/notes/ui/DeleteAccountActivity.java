package com.bcs.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcs.notes.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class DeleteAccountActivity extends AppCompatActivity {
    private static final String TAG = "E";
    private EditText deleteEmail;
    private EditText deletePassword;
    private TextView deleteConfirm;
    private FirebaseAuth mAuth;

    public void initVar() {
        deleteEmail = findViewById(R.id.activity_confirm_delete_email);
        deletePassword = findViewById(R.id.activity_confirm_delete_password);
        deleteConfirm = findViewById(R.id.activity_confirm_delete_sure);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delete);
        buttonConfirmDelete();
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    @Override
    protected void onStart() {
        super.onStart();
        initVar();
    }

    protected void buttonConfirmDelete() {
        Button buttonRegister = findViewById(R.id.activity_confirm_delete_delete);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }

    private void deleteAccount() {
        String email = deleteEmail.getText().toString();
        String password = deletePassword.getText().toString();
        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Email ou Senha vazio", Toast.LENGTH_SHORT).show();
            finish();
            goToDeleteAccount();
        } else {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(email, password);
            // Prompt the user to re-provide their sign-in credentials
            mAuth.getCurrentUser().reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "User re-authenticated.");
                            mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User account deleted.");
                                        finish();
                                        goToMain();
                                    }
                                }
                            });
                        }
                    });
        }
    }

    public void goToDeleteAccount() {
        startActivity(new Intent(this, DeleteAccountActivity.class));
    }

    public void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
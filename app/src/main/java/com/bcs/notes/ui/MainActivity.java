package com.bcs.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bcs.notes.R;
import com.bcs.notes.UserAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    UserAuth userAuth = new UserAuth();
    private EditText mainEmail;
    private EditText mainPassword;
    // [START declare_auth]
    private FirebaseAuth mAuth;

    // [END declare_auth]
    public void initEditText() {
        mainEmail = findViewById(R.id.activity_main_email);
        mainPassword = findViewById(R.id.activity_main_password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEditText();
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        buttonRegister();
        buttonLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and goToAcitivity UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            finish();
            goToDashboard();
        }
    }

    private void buttonRegister() {
        Button buttonRegister = findViewById(R.id.activity_main_button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void buttonLogin() {
        Button buttonLogin = findViewById(R.id.activity_main_button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        String email = mainEmail.getText().toString();
        String password = mainPassword.getText().toString();
        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Email ou Senha vazio", Toast.LENGTH_SHORT).show();
            finish();
            goToMain();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        goToDashboard();
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    private void signUp() {
        String email = mainEmail.getText().toString();
        String password = mainPassword.getText().toString();
        // [START create_user_with_email]
        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Email ou Senha vazio", Toast.LENGTH_SHORT).show();
            finish();
            goToMain();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                goToMain();
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();
                                goToMain();
                            }
                        }
                    });
            // [END create_user_with_email]
        }
    }

    public void goToDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    public void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
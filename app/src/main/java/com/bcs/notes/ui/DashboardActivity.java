package com.bcs.notes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bcs.notes.R;
import com.bcs.notes.UserAuth;

public class DashboardActivity extends AppCompatActivity {

    UserAuth userAuth = new UserAuth();
    private TextView UserId;
    private TextView UserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        buttonDelete();
        UserId = findViewById(R.id.activity_dashboard_textview_uid);
        UserEmail = findViewById(R.id.activity_dashboard_textview_email);
        buttonSignOut();
    }

    @Override
    protected void onStart() {
        super.onStart();
        UserId.setText("User ID: " + userAuth.getCurrentUserUID());
        UserEmail.setText("User Email: " + userAuth.getCurrentUserEmail());
        if (userAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User isn't logged", Toast.LENGTH_SHORT).show();
            goToMain();
        }
    }

    protected void buttonSignOut() {
        Button buttonSignout = findViewById(R.id.activity_dashboard_button_signout);
        buttonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAuth.singOut();
                finish();
                goToMain();
            }
        });
    }

    protected void buttonDelete() {
        Button buttonRegister = findViewById(R.id.activity_dashboard_button_delete);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToComfirm();
            }
        });
    }

    public void goToComfirm() {
        startActivity(new Intent(this, DeleteAccountActivity.class));
    }

    public void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}



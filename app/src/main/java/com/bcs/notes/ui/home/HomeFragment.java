package com.bcs.notes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bcs.notes.R;
import com.bcs.notes.model.UserAuth;
import com.bcs.notes.ui.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textView_userID;
    private TextView textView_userEmail;
    private Button button_exit;
    private Button button_reset;

    private UserAuth userAuth = new UserAuth();

    private void irParaLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        textView_userID = root.findViewById(R.id.fragment_home_textView_userID);
        homeViewModel.getUserID().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView_userID.setText(s);
            }
        });

        textView_userEmail = root.findViewById(R.id.fragment_home_textView_userEmail);
        homeViewModel.getUserEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView_userEmail.setText(s);
            }
        });

        button_exit = root.findViewById(R.id.fragment_home_button_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAuth.singOut();
                getActivity().finish();
                irParaLoginActivity();
            }
        });

        button_reset = root.findViewById(R.id.fragment_home_button_reset);
        button_reset.setOnClickListener(new View.OnClickListener() {
            String email = userAuth.getCurrentUserEmail();

            @Override
            public void onClick(View v) {
                userAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    button_reset.setEnabled(false);
                                    Toast.makeText(getActivity(), "Email enviado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return root;
    }
}
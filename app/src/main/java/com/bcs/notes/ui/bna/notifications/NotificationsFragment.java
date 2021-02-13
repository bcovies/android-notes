package com.bcs.notes.ui.bna.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bcs.notes.R;
import com.bcs.notes.model.UserAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private UserAuth userAuth = new UserAuth();
    final ArrayList<String> labels = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/notes");

        final Button button_update = root.findViewById(R.id.fragment_notifications_button_update);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    labels.add(data.getValue().toString());
                }
                for (String s : labels) {
                    System.out.println(s.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error: Database Disconnected!", Toast.LENGTH_SHORT).show();
            }
        };
        path.addValueEventListener(postListener);

        return root;
    }
}
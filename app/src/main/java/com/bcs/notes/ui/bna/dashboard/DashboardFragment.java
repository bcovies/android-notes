package com.bcs.notes.ui.bna.dashboard;

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
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private UserAuth userAuth = new UserAuth();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final EditText note = root.findViewById(R.id.fragment_dashboard_editText_note);
        final Button button_insert = root.findViewById(R.id.fragment_dashboard_button_insert);

        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), note.getText().toString(), Toast.LENGTH_SHORT).show();
                String note1 = note.getText().toString();
                writeNewPost(note1);
            }
        });

        return root;
    }

    private void writeNewPost(String note1) {
        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/notes");
        String key = path.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, note1);
        path.updateChildren(childUpdates);
        Toast.makeText(getActivity(), "Posted!", Toast.LENGTH_SHORT).show();
    }

}
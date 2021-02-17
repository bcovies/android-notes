package com.bcs.notes.ui.bna.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;
import com.bcs.notes.model.RecyclerAdapter;
import com.bcs.notes.model.UserAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DashboardFragment extends Fragment {

    UserAuth userAuth = new UserAuth();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //////////////////////////////////////////
        ////////////////  ///////////
        /////////////////////////////////////////

        EditText note = view.findViewById(R.id.fragment_dashboard_editText_produto);
        Button button_insert = view.findViewById(R.id.fragment_dashboard_button_insert);
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), note.getText().toString(), Toast.LENGTH_SHORT).show();
                String note1 = note.getText().toString();
                writeNewPost(note1);
            }
        });
        //////////////////////////////////////////
        //////////////// RECYCLER VIEW ///////////
        /////////////////////////////////////////

        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/notes");

        ArrayList<String> stringArray = new ArrayList<String>();

        path.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dss : snapshot.getChildren()) {
                    stringArray.add(dss.getValue().toString());
                }
                initRecyclerView();
            }

            private void initRecyclerView() {
                RecyclerView recyclerView = view.findViewById(R.id.fragment_dashboard_recyclerView);
                RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), stringArray);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return view;
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
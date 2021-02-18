package com.bcs.notes.ui.notifications;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bcs.notes.R;
import com.bcs.notes.model.UserAuth;
import com.bcs.notes.ui.dashboard.RecyclerAdapterDashboard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;


public class NotificationsFragment extends Fragment {

    //https://pt.stackoverflow.com/questions/25167/salvar-valor-em-sharedpreference
    UserAuth userAuth = new UserAuth();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Set<String> set = pref.getStringSet("key", null);

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button button = view.findViewById(R.id.fragment_notifications_button_adicionarLista);
        EditText editText = view.findViewById(R.id.fragment_notifications_editText_nomeLista);
        CheckBox checkBox = view.findViewById(R.id.fragment_notifications_recyclerview_list_checkbox);
        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID());
        ArrayList<String> stringArray = new ArrayList<String>();
        ArrayList<String> testeArray = new ArrayList<String>();


        RecyclerAdapterNotifications recyclerAdapterNotifications = new RecyclerAdapterNotifications();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(editText.getText().toString());
                Toast.makeText(getContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();
                System.out.println("SharedPreferences: "+set);
            }
        });




        //////////////////////////////////////////
        //////////////// RECYCLER VIEW ///////////
        /////////////////////////////////////////


        path.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    for (DataSnapshot filho : pai.getChildren()) {
                        stringArray.add(filho.getValue().toString());
                    }
                }
                initRecyclerView();
            }

            private void initRecyclerView() {
                RecyclerView recyclerView = view.findViewById(R.id.fragment_notifications_recyclerView);
                RecyclerAdapterNotifications adapter = new RecyclerAdapterNotifications(getActivity(), stringArray);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;
    }
}
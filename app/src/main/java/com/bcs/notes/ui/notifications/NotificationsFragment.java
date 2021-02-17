package com.bcs.notes.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;
import com.bcs.notes.model.RecyclerAdapterDashboard;
import com.bcs.notes.model.UserAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {

    UserAuth userAuth = new UserAuth();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        //////////////////////////////////////////
        //////////////// RECYCLER VIEW ///////////
        /////////////////////////////////////////

        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID());

        ArrayList<String> stringArray = new ArrayList<String>();
        ArrayList<String> stringArrayPai = new ArrayList<String>();

        path.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    System.out.println("Título: " + pai.getKey());
                    stringArrayPai.add(pai.getValue().toString());
                    for (DataSnapshot filho : pai.getChildren()) {
                        System.out.println("Título: " + filho.getKey());
                        stringArray.add(filho.getValue().toString());
                    }
                }


                initRecyclerView();
            }

            private void initRecyclerView() {
                RecyclerView recyclerView = view.findViewById(R.id.fragment_notifications_recyclerview);
                RecyclerAdapterDashboard adapter = new RecyclerAdapterDashboard(getActivity(), stringArray);
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
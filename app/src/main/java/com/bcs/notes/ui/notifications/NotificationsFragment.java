package com.bcs.notes.ui.notifications;

import android.content.SharedPreferences;
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
import com.bcs.notes.model.UserAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class NotificationsFragment extends Fragment {

    //https://pt.stackoverflow.com/questions/25167/salvar-valor-em-sharedpreference
    UserAuth userAuth = new UserAuth();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button button = view.findViewById(R.id.fragment_notifications_button_adicionarLista);
        EditText editText = view.findViewById(R.id.fragment_notifications_editText_nomeLista);

        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/mercado");

        ArrayList<String> stringArray = new ArrayList<String>();
        ArrayList<String> stringArrayLista = new ArrayList<String>();

        SharedPreferences pref = getContext().getSharedPreferences("TAG", getContext().MODE_PRIVATE);

        RecyclerAdapterNotifications recyclerAdapterNotifications = new RecyclerAdapterNotifications();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(editText.getText().toString());
                Toast.makeText(getContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();

                Set<String> set = pref.getStringSet("ARRAY", null);

                System.out.println(set.toString());
                List<String> list = new ArrayList<String>(set);


                criarLista(editText.getText().toString(),list);
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

        //////////////////////////////////////////
        //////////////// RECYCLER VIEW 2 ///////////
        /////////////////////////////////////////
        DatabaseReference path2 = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/lista");

        path2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    stringArrayLista.add(pai.getKey());

                }
                initRecyclerView();
            }

            private void initRecyclerView() {
                RecyclerView recyclerView2 = view.findViewById(R.id.fragment_notifications_recyclerView_lista);
                RecyclerAdapterNotificationsLista adapter = new RecyclerAdapterNotificationsLista(getActivity(), stringArrayLista);
                recyclerView2.setAdapter(adapter);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;
    }

    public void criarLista(String key,List<String> lista) {
        DatabaseReference path_lista = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/lista");

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, lista);
        path_lista.updateChildren(childUpdates);
        Toast.makeText(getActivity(), "Posted!", Toast.LENGTH_SHORT).show();
    }

}
package com.bcs.notes.ui.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;
import com.bcs.notes.model.RecyclerAdapterDashboard;
import com.bcs.notes.model.UserAuth;
import com.google.firebase.database.ChildEventListener;
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
        ////////////////  SPINNER      ///////////
        /////////////////////////////////////////
        Spinner spinnerSetores = view.findViewById(R.id.fragment_dashboard_spinner);
        String[] listaSpinnerSetores = getResources().getStringArray(R.array.fragment_dashboard_spinner_list);
        spinnerSetores.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, listaSpinnerSetores));


        //////////////////////////////////////////
        ////////// BUTTOM && EDTITEXT  ///////////
        /////////////////////////////////////////
        Button button_insert = view.findViewById(R.id.fragment_dashboard_button_insert);
        EditText note = view.findViewById(R.id.fragment_dashboard_editText_produto);


        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setor = spinnerSetores.getSelectedItem().toString();
                String produto = note.getText().toString();
                if (produto.isEmpty()) {
                    Toast.makeText(getContext(), "Campos vazios!!", Toast.LENGTH_SHORT).show();
                } else {
                    writeNewPost(setor, produto);
                }
            }
        });

        //////////////////////////////////////////
        //////////////// RECYCLER VIEW ///////////
        /////////////////////////////////////////

        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID());

        ArrayList<String> stringArray = new ArrayList<String>();
        path.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    System.out.println("Título: " + pai.getKey());
                    for (DataSnapshot filho : pai.getChildren()) {
                        System.out.println("Título: " + filho.getKey());
                        stringArray.add(filho.getValue().toString());
                    }
                }
                initRecyclerView();
            }

            private void initRecyclerView() {
                RecyclerView recyclerView = view.findViewById(R.id.fragment_dashboard_recyclerView);
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

    private void writeNewPost(String setor, String produto) {
        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/" + setor);
        String key = path.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, produto);
        path.updateChildren(childUpdates);
        Toast.makeText(getActivity(), "Posted!", Toast.LENGTH_SHORT).show();

    }
}
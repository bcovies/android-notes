package com.bcs.notes.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Map;


public class DashboardFragment extends Fragment {

    private UserAuth userAuth = new UserAuth();
    private ArrayList<String> stringArray;
    private String[] listaSpinnerSetores;
    private Button button_insert;
    private Spinner spinnerSetores;
    private EditText editText_produto;
    private DatabaseReference databaseReference_mercado;
    private DatabaseReference databaseReference_setor;
    private RecyclerView recyclerView_dashboard;
    private RecyclerAdapterDashboard recyclerAdapterDashboard;

    private void criarUmaPostagem(String setor, String produto) {
        databaseReference_setor = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/" + "mercado/" + setor);
        String key = databaseReference_setor.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, produto);
        databaseReference_setor.updateChildren(childUpdates);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        databaseReference_mercado = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/mercado");

        spinnerSetores = view.findViewById(R.id.fragment_dashboard_spinner);
        listaSpinnerSetores = getResources().getStringArray(R.array.fragment_dashboard_spinner_list);
        spinnerSetores.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, listaSpinnerSetores));

        button_insert = view.findViewById(R.id.fragment_dashboard_button_insert);
        editText_produto = view.findViewById(R.id.fragment_dashboard_editText_produto);


        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setor = spinnerSetores.getSelectedItem().toString();
                String produto = editText_produto.getText().toString();
                if (produto.isEmpty()) {
                } else {
                    criarUmaPostagem(setor, produto);
                }
            }
        });

        stringArray = new ArrayList<String>();
        databaseReference_mercado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    for (DataSnapshot filho : pai.getChildren()) {
                        stringArray.add(filho.getValue().toString());
                    }
                }
                inicializarRecyclerView();
            }

            private void inicializarRecyclerView() {
                recyclerView_dashboard = view.findViewById(R.id.fragment_dashboard_recyclerView);
                recyclerAdapterDashboard = new RecyclerAdapterDashboard(getActivity(), stringArray);
                recyclerView_dashboard.setAdapter(recyclerAdapterDashboard);
                recyclerView_dashboard.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return view;
    }
}
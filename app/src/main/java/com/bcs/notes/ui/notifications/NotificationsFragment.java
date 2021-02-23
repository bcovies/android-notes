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

    private UserAuth userAuth = new UserAuth();
    private Button button_adicionarLista;
    private EditText editText_nomeLista;
    private View view;

    private DatabaseReference databaseReference_mercado;
    private DatabaseReference databaseReference_lista;

    private ArrayList<String> stringArray_produtos = new ArrayList<String>();
    private ArrayList<String> stringArray_lista = new ArrayList<String>();

    private SharedPreferences sharedPreferences_Notifications;

    private RecyclerAdapterNotificationsProduto recyclerAdapterNotificationsProduto;
    private RecyclerAdapterNotificationsLista recyclerAdapterNotificationsLista;
    private RecyclerView recyclerView_produtos;
    private RecyclerView recyclerView_lista;

    private void inicializarRecyclerViewNotificationsMercado() {
        recyclerView_lista = view.findViewById(R.id.fragment_notifications_recyclerView_lista);
        recyclerAdapterNotificationsLista = new RecyclerAdapterNotificationsLista(getActivity(), stringArray_lista);
        recyclerView_lista.setAdapter(recyclerAdapterNotificationsLista);
        recyclerView_lista.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void inicializarRecyclerViewNotificationsProduto() {
        recyclerView_produtos = view.findViewById(R.id.fragment_notifications_recyclerView);
        recyclerAdapterNotificationsProduto = new RecyclerAdapterNotificationsProduto(getActivity(), stringArray_produtos);
        recyclerView_produtos.setAdapter(recyclerAdapterNotificationsProduto);
        recyclerView_produtos.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void criarLista(String key, List<String> lista) {
        DatabaseReference path_lista = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/lista");
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, lista);
        path_lista.updateChildren(childUpdates);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        button_adicionarLista = view.findViewById(R.id.fragment_notifications_button_adicionarLista);
        editText_nomeLista = view.findViewById(R.id.fragment_notifications_editText_nomeLista);

        databaseReference_mercado = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/mercado");
        databaseReference_lista = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/lista");

        sharedPreferences_Notifications = getContext().getSharedPreferences("TAG-PRODUTO", getContext().MODE_PRIVATE);

        button_adicionarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> set = sharedPreferences_Notifications.getStringSet("ARRAY-PRODUTO", null);
                List<String> list = new ArrayList<String>(set);
                String nomeLista = editText_nomeLista.getText().toString();
                nomeLista = nomeLista.replaceAll("[{}()\\[\\].+*?^$|/\\[-]]", "");
                String nomeListaFinal = nomeLista.replaceAll("/", "");
                criarLista(nomeListaFinal, list);
            }
        });

        databaseReference_mercado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    for (DataSnapshot filho : pai.getChildren()) {
                        stringArray_produtos.add(filho.getValue().toString());
                    }
                }
                inicializarRecyclerViewNotificationsProduto();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference_lista.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    stringArray_lista.add(pai.getKey());
                }
                inicializarRecyclerViewNotificationsMercado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }
}
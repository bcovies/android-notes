package com.bcs.notes.ui.listaFinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.bcs.notes.R;
import com.bcs.notes.model.UserAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListaFinal extends AppCompatActivity {


    private UserAuth userAuth = new UserAuth();
    private ArrayList<String> stringArray_listaFinal = new ArrayList<String>();
    private DatabaseReference databaseReference_listaFinal;
    private RecyclerView recyclerView_listaFinal;
    private RecyclerAdapterViewListaFinal recyclerAdapterViewListaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lista_final);
        SharedPreferences sharedPreferences_listaFinal = getSharedPreferences("TAG-LISTA", MODE_PRIVATE);
        String caminhoListaFinal = sharedPreferences_listaFinal.getString("ARRAY-LISTA", null);
        databaseReference_listaFinal = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/lista" + "/" + caminhoListaFinal);
        databaseReference_listaFinal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    stringArray_listaFinal.add(pai.getValue().toString());
                }
                inicializarRecyclerViewListaFinal();
            }

            private void inicializarRecyclerViewListaFinal() {
                recyclerView_listaFinal = findViewById(R.id.activity_view_lista_final_recyclerView);
                recyclerAdapterViewListaFinal = new RecyclerAdapterViewListaFinal(ViewListaFinal.this, stringArray_listaFinal);
                recyclerView_listaFinal.setAdapter(recyclerAdapterViewListaFinal);
                recyclerView_listaFinal.setLayoutManager(new LinearLayoutManager(ViewListaFinal.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
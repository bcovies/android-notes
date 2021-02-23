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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lista_final);




        //////////////////////////////////////////
        //////////////// RECYCLER VIEW 2 ///////////
        /////////////////////////////////////////
        UserAuth userAuth = new UserAuth();

        ArrayList<String> stringArrayFinal = new ArrayList<String>();

        SharedPreferences pref = getSharedPreferences("TAGG", MODE_PRIVATE);
        String pathcomplementar = pref.getString("STRING", null);

        System.out.println(pathcomplementar);

        DatabaseReference path2 = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/lista" + "/" + pathcomplementar);

        path2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pai : snapshot.getChildren()) {
                    stringArrayFinal.add(pai.getValue().toString());
                    System.out.println(stringArrayFinal.toString());
                }
                initRecyclerView();
            }

            private void initRecyclerView() {
                RecyclerView recyclerView2 = findViewById(R.id.activity_view_lista_final_recyclerView);
                RecyclerAdapterViewListaFinal adapterteste = new RecyclerAdapterViewListaFinal(ViewListaFinal.this, stringArrayFinal);
                recyclerView2.setAdapter(adapterteste);
                recyclerView2.setLayoutManager(new LinearLayoutManager(ViewListaFinal.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

}
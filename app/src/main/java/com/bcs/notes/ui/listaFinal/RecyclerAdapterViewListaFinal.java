package com.bcs.notes.ui.listaFinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;

import java.util.ArrayList;

public class RecyclerAdapterViewListaFinal extends RecyclerView.Adapter<RecyclerAdapterViewListaFinal.MyViewHolder> {

    private ArrayList<String> arrayList_listaFinal = new ArrayList<>();
    private Context context;

    public RecyclerAdapterViewListaFinal(Context context, ArrayList<String> arrayList_listaFinal) {
        this.context = context;
        this.arrayList_listaFinal = arrayList_listaFinal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_lista_final_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView(arrayList_listaFinal.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList_listaFinal.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.activity_view_lista_final_textView);
        }

        public void bindView(String row) {
            textView.setText(row);
        }
    }
}





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

    public ArrayList<String> mNames = new ArrayList<>();
    public Context mContext;

    public RecyclerAdapterViewListaFinal(Context context, ArrayList<String> Names) {
        mNames = Names;
        mContext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.activity_view_lista_final_textView);

        }

        public void bindView(String row) {
            textView.setText(row);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_lista_final_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView(mNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
}





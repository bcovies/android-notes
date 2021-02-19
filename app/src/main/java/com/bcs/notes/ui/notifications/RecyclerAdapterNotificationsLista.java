package com.bcs.notes.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;
import com.bcs.notes.ui.listaFinal.ViewListaFinal;

import java.util.ArrayList;

public class RecyclerAdapterNotificationsLista extends RecyclerView.Adapter<RecyclerAdapterNotificationsLista.MyViewHolder> {

    public ArrayList<String> mNames = new ArrayList<>();
    public Context mContext;

    public RecyclerAdapterNotificationsLista(Context context, ArrayList<String> Names) {
        mNames = Names;
        mContext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_textView_list);
            button = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_textView_button);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("TAGG", Context.MODE_PRIVATE).edit();

                    System.out.println(textView.getText().toString());
                    String pathcomplementar = textView.getText().toString();

                    editor.putString("STRING", pathcomplementar);
                    editor.commit();



                    mContext.startActivity(new Intent(mContext, ViewListaFinal.class));
                }
            });
        }

        public void bindView(String row) {
            textView.setText(row);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notifications_recyclerview_list_list_item, parent, false);
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





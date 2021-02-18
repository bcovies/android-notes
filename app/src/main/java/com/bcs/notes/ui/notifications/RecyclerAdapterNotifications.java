package com.bcs.notes.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecyclerAdapterNotifications extends RecyclerView.Adapter<RecyclerAdapterNotifications.MyViewHolder> {

    public ArrayList<String> mNames = new ArrayList<>();
    public Set<String> set = new HashSet<String>();
    public Context mContext;


    public RecyclerAdapterNotifications() {

    }

    public RecyclerAdapterNotifications(Context context, ArrayList<String> Names) {
        mNames = Names;
        mContext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_textView);
            checkBox = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_checkbox);
            button = itemView.findViewById(R.id.fragment_notifications_button_adicionarLista);
        }

        public void bindView(String row) {
            textView.setText(row);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notifications_recyclerview_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();

        holder.bindView(mNames.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    System.out.println("[ON]ANTES:" + set.toString());
                    set.add(mNames.get(position));
                    System.out.println("[ON]DPS: " + set.toString());
                    editor.putStringSet("ARRAY",set);
                    editor.commit();
                } else {
                    System.out.println("[OFF]ANTES:" + set.toString());
                    set.remove(mNames.get(position));
                    System.out.println("[OFF]DPS CLEAR: " + set.toString());
                    editor.clear();
                    editor.putStringSet("ARRAY",set);
                    editor.commit();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
}





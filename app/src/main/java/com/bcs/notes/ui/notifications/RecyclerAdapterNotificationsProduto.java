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

public class RecyclerAdapterNotificationsProduto extends RecyclerView.Adapter<RecyclerAdapterNotificationsProduto.MyViewHolder> {

    private ArrayList<String> arrayList_produto = new ArrayList<>();
    private Set<String> set = new HashSet<String>();
    private Context context;

    public RecyclerAdapterNotificationsProduto(Context context, ArrayList<String> arrayList_produto) {
        this.arrayList_produto = arrayList_produto;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notifications_recyclerview_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SharedPreferences.Editor editor = this.context.getSharedPreferences("TAG-PRODUTO", Context.MODE_PRIVATE).edit();

        holder.bindView(arrayList_produto.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    set.add(arrayList_produto.get(position));
                    editor.putStringSet("ARRAY-PRODUTO", set);
                    editor.commit();
                } else {
                    set.remove(arrayList_produto.get(position));
                    editor.clear();
                    editor.commit();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList_produto.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_textView);
            checkBox = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_checkbox);
        }

        public void bindView(String row) {
            textView.setText(row);
        }
    }
}





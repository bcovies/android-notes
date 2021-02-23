package com.bcs.notes.ui.dashboard;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

public class RecyclerAdapterDashboard extends RecyclerView.Adapter<RecyclerAdapterDashboard.MyViewHolder> {

    //https://www.youtube.com/watch?v=Zd0TUuoPP-s&ab_channel=AwsRh
    //https://stackoverflow.com/questions/6626006/android-custom-dialog-cant-get-text-from-edittext/14091604#14091604
    private ArrayList<String> arrayList_produtos = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView_dashboard;

    public RecyclerAdapterDashboard(Context context, ArrayList<String> arrayList_produtos) {
        this.arrayList_produtos = arrayList_produtos;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dashboard_recyclerview_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView(arrayList_produtos.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList_produtos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        private TextView textView_produto;
        private ImageButton imageButton;
        private UserAuth userAuth = new UserAuth();
        private DatabaseReference databaseReference_mercado = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/mercado");


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_produto = itemView.findViewById(R.id.fragment_dashboard_recyclerview_list_item_textView);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);
        }

        void bindView(String row) {
            textView_produto.setText(row);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }


        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_popup_edit:
                    databaseReference_mercado.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot pai : snapshot.getChildren()) {
                                for (DataSnapshot filho : pai.getChildren()) {
                                    if (filho.getValue().toString().contains(textView_produto.getText().toString())) {

                                        AlertDialog.Builder alertDialog;
                                        LayoutInflater layoutInflater;
                                        View view;
                                        Map<String, Object> childUpdates = new HashMap<>();
                                        alertDialog = new AlertDialog.Builder(context);
                                        layoutInflater = LayoutInflater.from(context);

                                        view = layoutInflater.inflate(R.layout.dialog_edit_dashboard, null);
                                        alertDialog.setTitle("Editando o item: " + textView_produto.getText().toString());
                                        alertDialog.setView(view);
                                        final EditText editText_rowRecyclerView = view.findViewById(R.id.dialog_dashboard_editText);
                                        editText_rowRecyclerView.setText(filho.getValue().toString());
                                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                String string_rowRecyclerView = editText_rowRecyclerView.getText().toString();
                                                filho.getRef().setValue(string_rowRecyclerView);

                                            }
                                        });

                                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.cancel();
                                            }
                                        });
                                        alertDialog.show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                    notifyDataSetChanged();
                    return true;


                case R.id.action_popup_delete:

                    databaseReference_mercado.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot pai : snapshot.getChildren()) {
                                for (DataSnapshot filho : pai.getChildren()) {
                                    if (filho.getValue().toString().contains(textView_produto.getText().toString())) {

                                        AlertDialog.Builder alertDialog;
                                        LayoutInflater layoutInflater;
                                        View view;
                                        Map<String, Object> childUpdates = new HashMap<>();
                                        alertDialog = new AlertDialog.Builder(context);
                                        layoutInflater = LayoutInflater.from(context);
                                        view = layoutInflater.inflate(R.layout.dialog_remove_dashboard, null);

                                        alertDialog.setTitle("Deseja excluir: " + textView_produto.getText().toString() + "?");
                                        alertDialog.setView(view);

                                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                filho.getRef().removeValue();
                                            }
                                        });

                                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.cancel();
                                            }
                                        });

                                        alertDialog.show();
                                    }

                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    return true;
                default:
                    return false;
            }
        }
    }
}

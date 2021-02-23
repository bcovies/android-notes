package com.bcs.notes.ui.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
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

    private ArrayList<String> mNames = new ArrayList<>();
    private Context mContext;
    Dialog myDialog;
    //https://www.youtube.com/watch?v=Zd0TUuoPP-s&ab_channel=AwsRh
    //https://stackoverflow.com/questions/6626006/android-custom-dialog-cant-get-text-from-edittext/14091604#14091604
    AlertDialog.Builder alert;
    LayoutInflater linf;
    View inflator;

    public RecyclerAdapterDashboard(Context context, ArrayList<String> Names) {
        mNames = Names;
        mContext = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dashboard_recyclerview_list_item, parent, false);


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


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        private static final String TAG = "MyViewHolder";
        TextView textView;
        ImageButton imageButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            textView = itemView.findViewById(R.id.fragment_dashboard_recyclerview_list_item_textView);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);
        }

        void bindView(String row) {
            textView.setText(row);
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

        UserAuth userAuth = new UserAuth();
        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID()+"/mercado");
        // Initialize activity


        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_popup_edit:
                    path.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot pai : snapshot.getChildren()) {
                                for (DataSnapshot filho : pai.getChildren()) {
                                    System.out.println("KEY FILHO: " + filho.getKey());
                                    if (filho.getValue().toString().contains(textView.getText().toString())) {

                                        Map<String, Object> childUpdates = new HashMap<>();
                                        alert = new AlertDialog.Builder(mContext);
                                        linf = LayoutInflater.from(mContext);
                                        inflator = linf.inflate(R.layout.dialog_edit_dashboard, null);
                                        alert.setTitle("Tilte");
                                        alert.setMessage("Message");
                                        alert.setView(inflator);

                                        final EditText et1 = (EditText) inflator.findViewById(R.id.dialog_dashboard_editText);
                                        et1.setText(filho.getValue().toString());

                                        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                String s1 = et1.getText().toString();
                                                Toast.makeText(mContext, s1, Toast.LENGTH_SHORT).show();
                                                filho.getRef().setValue(s1);
                                            }
                                        });

                                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.cancel();
                                            }
                                        });

                                        alert.show();
                                    }

                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    return true;
                case R.id.action_popup_delete:

                    path.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot pai : snapshot.getChildren()) {
                                for (DataSnapshot filho : pai.getChildren()) {
                                    System.out.println("KEY FILHO: " + filho.getKey());
                                    if (filho.getValue().toString().contains(textView.getText().toString())) {

                                        Map<String, Object> childUpdates = new HashMap<>();
                                        alert = new AlertDialog.Builder(mContext);
                                        linf = LayoutInflater.from(mContext);
                                        inflator = linf.inflate(R.layout.dialog_remove_dashboard, null);
                                        alert.setTitle("Tilte");
                                        alert.setMessage("Message");
                                        alert.setView(inflator);


                                        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                filho.getRef().removeValue();
                                            }
                                        });

                                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.cancel();
                                            }
                                        });

                                        alert.show();
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

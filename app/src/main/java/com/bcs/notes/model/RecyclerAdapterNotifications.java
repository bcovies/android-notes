package com.bcs.notes.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;

import java.util.ArrayList;

public class RecyclerAdapterNotifications extends RecyclerView.Adapter<RecyclerAdapterNotifications.MyViewHolder> {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mPai = new ArrayList<>();
    private Context mContext;


    public RecyclerAdapterNotifications(Context context, ArrayList<String> Names ) {
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
        TextView textView_pai;
        ImageButton imageButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_item_textView);
            textView_pai = itemView.findViewById(R.id.fragment_notifications_recyclerview_list_item_pai);
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

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_popup_edit:
                    Log.d(TAG, "onMenuItemClick: action_popup_edit @ " + getAdapterPosition());
                    return true;
                case R.id.action_popup_delete:
                    Log.d(TAG, "onMenuItemClick: action_popup_delete @ " + getAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
    }
}
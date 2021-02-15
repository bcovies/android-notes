package com.bcs.notes.ui.bna.notifications;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bcs.notes.R;
import com.bcs.notes.model.UserAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.bcs.notes.R.id.fragment_notifications_listview;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private UserAuth userAuth = new UserAuth();
    final ArrayList<String> labels = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final ListView listView_array = root.findViewById(R.id.fragment_notifications_listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, labels);

        DatabaseReference path = userAuth.returnReference().child("/users" + "/" + userAuth.getCurrentUserUID() + "/notes");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    labels.add(data.getValue().toString());
                    listView_array.setAdapter(arrayAdapter);
                    listView_array.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int pos = 0; pos < labels.size(); pos++) {
                                if (position == pos) {
                                    Toast.makeText(getActivity(), labels.get(pos).toString(), Toast.LENGTH_SHORT).show();

                                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                        if (labels.get(pos).toString() == childSnapshot.getValue()  ){
                                            System.out.println("OK");
                                            System.out.println(labels.get(pos).toString());
                                            System.out.println( childSnapshot.getValue());
                                            childSnapshot.getRef().removeValue();
                                            getActivity().recreate();
                                        }
                                    }
                                }
                            }
                            //
                            return false;
                        }
                    });
                }
                for (String s : labels) {
                    System.out.println(s.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error: Database Disconnected!", Toast.LENGTH_SHORT).show();
            }
        };
        path.addValueEventListener(postListener);

        return root;
    }
}
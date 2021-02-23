package com.bcs.notes.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserAuth {

    public FirebaseAuth getInstance() {
        FirebaseAuth currentUser = FirebaseAuth.getInstance();
        return currentUser;
    }

    public FirebaseUser getCurrentUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser;
    }

    public String getCurrentUserUID() {
        String currentUserId = FirebaseAuth.getInstance().getUid();
        return currentUserId;
    }

    public String getCurrentUserEmail() {
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return currentUserEmail;
    }

    public void singOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public  DatabaseReference  returnReference(){
        DatabaseReference dataBaseRoot = FirebaseDatabase.getInstance().getReference();
        return dataBaseRoot;
    }

}

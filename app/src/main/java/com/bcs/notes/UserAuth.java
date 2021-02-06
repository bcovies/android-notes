package com.bcs.notes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserAuth {

    public FirebaseAuth getInstance() {
        FirebaseAuth currentUser = FirebaseAuth.getInstance();
        System.out.println("INSTANCE:" + currentUser);
        return currentUser;
    }

    public FirebaseUser getCurrentUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("Current User: " + currentUser);
        return currentUser;
    }

    public String getCurrentUserUID() {
        String currentUserId = FirebaseAuth.getInstance().getUid();
        System.out.println("Current User ID: " + currentUserId);
        return currentUserId;
    }

    public String getCurrentUserEmail() {
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        System.out.println("Current User EMAIL: " + currentUserEmail);
        return currentUserEmail;
    }

    public void singOut() {
        System.out.println("TAG: USER LOGOUT");
        FirebaseAuth.getInstance().signOut();
    }


}

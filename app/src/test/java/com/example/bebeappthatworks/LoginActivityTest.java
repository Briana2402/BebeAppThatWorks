package com.example.bebeappthatworks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.bebeappthatworks.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivityTest {
    private LoginActivity loginActivity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Before
    public void setUp() {
        loginActivity = new LoginActivity();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loginActivity.mAuth = mAuth;
        loginActivity.db = db;
    }

//    @Test
//    public void testCheckForBelongingAttendee() {
//        // Sign in with a test user (Assume the user is in the Attendees collection)
//        mAuth.signInWithEmailAndPassword("alexia1@gmail.com", "Parola12@");
//        assertTrue(loginActivity.checkForBelongingAttendee());
//
//        // Sign out the test user
//        mAuth.signOut();
//
//        // Sign in with a different test user (Assume the user is not in the Attendees collection)
//        mAuth.signInWithEmailAndPassword("testorganiser@email.com", "password123");
//        assertFalse(loginActivity.checkForBelongingAttendee());
//    }
}

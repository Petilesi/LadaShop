package com.example.ladashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = ListingsActivity.class.getName();
    private FirebaseUser user;


    private RecyclerView vRecyclerView;
    private ArrayList<ListingItem> vItems;
    private ListingsAdapter vAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(LOG_TAG, "Authenticated user!");
        }else {
            Log.d(LOG_TAG, "Unauthenticated user+");
            finish();
        }
    }
}
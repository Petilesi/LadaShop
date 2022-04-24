package com.example.ladashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = ListingsActivity.class.getName();
    private FirebaseUser user;


    private RecyclerView vRecyclerView;
    private ArrayList<ListingItem> vPartsData;
    private ListingsAdapter vAdapter;
    private FirebaseFirestore vFirestore;
    private CollectionReference vParts;
    private SharedPreferences preferences;
    private int gridNumber = 1;

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

        vRecyclerView = findViewById(R.id.recyclerView);

        vRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));

        vPartsData = new ArrayList<>();

        vAdapter = new ListingsAdapter(this, vPartsData);

        vRecyclerView.setAdapter(vAdapter);

        vFirestore = FirebaseFirestore.getInstance();
        vParts = vFirestore.collection("Parts");
        queryData();
    }

    private void initializeData(){
        String[] itemList =  getResources().getStringArray(R.array.shopping_item_names);
        String[] itemsInfo =  getResources().getStringArray(R.array.shopping_item_desc);
        String[] itemsPrice =  getResources().getStringArray(R.array.shopping_item_price);
        TypedArray itemsImageResource =  getResources().obtainTypedArray(R.array.shopping_item_images);

      //  vPartsData.clear();

        for (int i = 0; i < itemList.length; i++)
            vParts.add(new ListingItem(
                    itemList[i],
                    itemsInfo[i],
                    itemsPrice[i],
                    itemsImageResource.getResourceId(i,0)));

        itemsImageResource.recycle();
       // vAdapter.notifyDataSetChanged();
    }

    private void queryData(){
        vPartsData.clear();
        vParts.orderBy("nev").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                ListingItem part = document.toObject(ListingItem.class);
                vPartsData.add(part);
            }

            if (vPartsData.size() == 0){
                initializeData();
                queryData();
            }
            vAdapter.notifyDataSetChanged();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.shop_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.filter);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                vAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.filter:
                return true;
            case R.id.options:
                return true;
            case R.id.cart:
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }
}
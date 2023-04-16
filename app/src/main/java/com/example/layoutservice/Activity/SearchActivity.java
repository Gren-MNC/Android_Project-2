package com.example.layoutservice.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layoutservice.Adapter.FirebaseSongAdapter;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<SongFireBase> songFireBaseArrayList;
    FirebaseSongAdapter firebaseSongAdapter;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    Context context;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView btn_back = findViewById(R.id.search_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewSearchMusic);
        databaseReference = FirebaseDatabase.getInstance().getReference("SongFireBase");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        songFireBaseArrayList = new ArrayList<>();
        firebaseSongAdapter = new FirebaseSongAdapter(context, songFireBaseArrayList);

        recyclerView.setAdapter(firebaseSongAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SongFireBase songFireBase = dataSnapshot.getValue(SongFireBase.class);
                    songFireBaseArrayList.add(songFireBase);
                }
                firebaseSongAdapter = new FirebaseSongAdapter(SearchActivity.this,songFireBaseArrayList);
                recyclerView.setAdapter(firebaseSongAdapter);
                firebaseSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        SearchView searchView = findViewById(R.id.SearchView);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSongAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSongAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}
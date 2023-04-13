package com.example.layoutservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.databinding.LayoutActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSong extends AppCompatActivity {

    private String songname, singername, imgUri, songUri;
//    private EditText song, singer, img, uri;
//    private Button saveBtn;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        EditText song = (EditText) findViewById(R.id.songName);
        EditText singer =(EditText) findViewById(R.id.singerName);
        EditText img =(EditText) findViewById(R.id.imagesLink);
        EditText uri =(EditText) findViewById(R.id.songLink);
        Button saveBtn = (Button) findViewById(R.id.save_song_into_DB);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songname = String.valueOf(song.getText());
                singername= String.valueOf(singer.getText());
                imgUri = String.valueOf(img.getText());
                songUri = String.valueOf(uri.getText());

                if(songname != null && singername != null && imgUri != null ){
                    SongFireBase songFireBase = new SongFireBase(songname,singername,imgUri,songUri);
                    db = FirebaseDatabase.getInstance();
                    databaseReference = db.getReference("SongFireBase");
                    databaseReference.child(songname).setValue(songFireBase).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            song.setText("");
                            singer.setText("");
                            img.setText("");
                            uri.setText("");
                            Toast.makeText(AddSong.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
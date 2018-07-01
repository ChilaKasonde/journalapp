package io.exception.journalapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AddEntry extends Activity {
    EditText journalTitle, journalEntry;
    FloatingActionButton commitFab;
    FirebaseFirestore mFirestore;
    Intent intentBundle;
    String date;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        commitFab = (FloatingActionButton)findViewById(R.id.fabCommit);


        journalEntry = (EditText)findViewById(R.id.journalEntry);
        journalTitle = (EditText)findViewById(R.id.journalTitle);
        mFirestore = FirebaseFirestore.getInstance();
        intentBundle = getIntent();

        uid = intentBundle.getStringExtra("uid");

        commitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String title = journalTitle.getText().toString();
            String entry = journalEntry.getText().toString();
                SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                String formatDate = s.format(new Date());

                date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Map<String, Object> addEntry = new HashMap<>();
                addEntry.put("title", title);
                addEntry.put("entry", entry);
                addEntry.put("date", date);
                addEntry.put("time", formatDate);
            mFirestore.collection("Journal/"+uid+"/Entry").add(addEntry).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AddEntry.this, "Success", Toast.LENGTH_LONG).show();
                    finish();
                }
            });




            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

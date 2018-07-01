package io.exception.journalapp;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class EditEntry extends Activity {
    private EditText journalTitle, journalEntry;
    private FloatingActionButton editFab;
    private FirebaseFirestore mFirestore;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    String document_title;
    String document_entry;
    String document_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        Intent bundle = getIntent();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        editFab = (FloatingActionButton)findViewById(R.id.editFab);
        final String document_id = bundle.getStringExtra("document_id");


        document_title = bundle.getStringExtra("document_title");


        document_entry = bundle.getStringExtra("document_entry");


        document_date = bundle.getStringExtra("document_date");

        journalEntry = (EditText)findViewById(R.id.journalEditEntry);
        journalTitle = (EditText)findViewById(R.id.journalEditTitle);
        journalEntry.setText(document_entry);
        journalTitle.setText(document_title);
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        final String formatDate = s.format(new Date());

        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String title = journalTitle.getText().toString();
            String entry = journalEntry.getText().toString();
            String uid = currentUser.getUid();
                Map<String, Object> editEntry = new HashMap<>();
                editEntry.put("title", title);
                editEntry.put("entry", entry);
                editEntry.put("time", formatDate);
                editEntry.put("date", document_date);

             mFirestore.collection("Journal/"+uid+"/Entry").document(document_id).set(editEntry).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     Toast.makeText(EditEntry.this, "Document Edited", Toast.LENGTH_LONG).show();
                    Intent intentHome = new Intent(EditEntry.this, HomeActivity.class);
                     startActivity(intentHome);
                     finish();
                 }
             });
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_entry, menu);
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

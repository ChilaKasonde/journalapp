package io.exception.journalapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class DetailView extends Activity {
     private FirebaseAuth mAuth;
     private FirebaseFirestore mFirestore;
     private FirebaseUser currentUser;
     private Button btnDelete, btnEdit;
     private String uid;
     private String document_id;
     private String document_title;
     private String document_entry;
     private String document_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent bundle = getIntent();

        document_id = bundle.getStringExtra("document_id");

        document_title = bundle.getStringExtra("document_title");

        document_entry = bundle.getStringExtra("document_entry");

        document_date = bundle.getStringExtra("document_date");

        TextView mTitle = (TextView)findViewById(R.id.mTitle);
        TextView mEntry = (TextView)findViewById(R.id.mEntry);
        TextView mDate = (TextView)findViewById(R.id.mDate);
        mTitle.setText(document_title);
        mEntry.setText(document_entry);
        mDate.setText(document_date);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        uid = currentUser.getUid();


        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore.collection("Journal/"+uid+"/Entry").document(document_id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DetailView.this, "Document successfully deleted!", Toast.LENGTH_LONG);
                                switchtoHome();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetailView.this, "Error "+ e, Toast.LENGTH_LONG);
                            }
                        });


            }
        });

        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMove = new Intent(DetailView.this, EditEntry.class);
                intentMove.putExtra("document_id", document_id);
                intentMove.putExtra("document_title", document_title);
                intentMove.putExtra("document_entry", document_entry);
                intentMove.putExtra("document_date", document_date);
                startActivity(intentMove);
            }
        });



    }

    private void switchtoHome() {
        Intent intentHome = new Intent(DetailView.this, HomeActivity.class);
        startActivity(intentHome);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view, menu);
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

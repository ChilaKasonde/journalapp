package io.exception.journalapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;


public class HomeActivity extends Activity {
         private FirebaseAuth mAuth;
         private Entries entries;
         private FirebaseUser currentUser;
         private FloatingActionButton addFab;
         private RecyclerView mMainList;
         private String uid;
         private FirebaseFirestore mFirestore;
         private List<Entries> entriesList;
         private EntriesListAdapter entriesListAdapter;
         private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mMainList = (RecyclerView)findViewById(R.id.entry_recyclerView);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        entriesList = new ArrayList<>();
        entriesListAdapter = new EntriesListAdapter(getApplicationContext(),entriesList);
        mMainList.setAdapter(entriesListAdapter);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("Journal/"+uid+"/Entry").orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                }
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){

                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        switch(doc.getType()){
                            case ADDED:
                                entries = doc.getDocument().toObject(Entries.class).withID(doc.getDocument().getId());
                                entriesList.add(entries);
                                entriesListAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                entriesList.clear();
                                entriesListAdapter.notifyDataSetChanged();
                                break;
                            case REMOVED:
                                entriesList.remove(entries);
                                entriesListAdapter.notifyDataSetChanged();
                                break;
                            default:
                                entriesList.add(entries);
                                entriesListAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });



        uid = currentUser.getUid();
        addFab = (FloatingActionButton)findViewById(R.id.fab);



        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(HomeActivity.this, AddEntry.class);
                intentAdd.putExtra("uid", uid);
                startActivity(intentAdd);
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        entriesListAdapter.notifyDataSetChanged();



    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.sign_out:
                mAuth.signOut();
                Intent intentSignIn = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intentSignIn);
                break;

        }



        return super.onOptionsItemSelected(item);
    }



    private void logInUser() {
        Intent intentLogin = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(intentLogin);
    }

}

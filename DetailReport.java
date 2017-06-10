package com.example.skylix.firebase_auth.Report;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.skylix.firebase_auth.ExpandableListAdapter;
import com.example.skylix.firebase_auth.Model.BillModel;
import com.example.skylix.firebase_auth.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by skylix on 30-5-17.
 */

public class DetailReport extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private List<String> user_set;
    private ExpandableListView exp_list;
    private ExpandableListAdapter adapter;
    private HashMap<String, List<String>> users;
    private List<String> status;
    private ProgressDialog pd;
    EditText etSearch;
    BillModel bilmod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance();
        pd = new ProgressDialog(DetailReport.this);
        pd.setMessage("Loading....");
        pd.show();

        etSearch = (EditText) findViewById(R.id.et_search);
        etSearch.setSingleLine(true);
        etSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
              String  text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                Log.d("result","text-"+text);
                if(bilmod.getBill_amount().equals(text)||bilmod.getBill_date().equals(text)||bilmod.getBill_tableno().equals(text)||bilmod.getIncvoice_no().equals(text)){
                    Toast.makeText(DetailReport.this, "Match", Toast.LENGTH_SHORT).show();

                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        });
        retrive();

    }


    @Override
    public void onClick(View v) {

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void retrive() {

        users = new HashMap<>();

        myRef = database.getReference();
        final DatabaseReference queryrecord = myRef.child("bill");
        queryrecord.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Populate a hashMap of active (or locked) users and their status
                getUpdate(snapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    private void getUpdate(DataSnapshot ds) {

        pd.dismiss();
        for (DataSnapshot userSnapshot : ds.getChildren()) {
            bilmod = userSnapshot.getValue(BillModel.class);

//            bilmod.setBill_tableno(userSnapshot.getValue(BillModel.class).getBill_tableno());
//            users.put(bilmod);

            users.put(bilmod.getBill_date() + "        " + bilmod.getBill_tableno() + "             " + bilmod.getIncvoice_no() + "               " + bilmod.getBill_amount() + "\n", bilmod.getItem_info());

        }
        // Display by an expandable list
        exp_list = (ExpandableListView) findViewById(R.id.expandableListView);
        user_set = new ArrayList<String>(users.keySet());
        adapter = new ExpandableListAdapter(DetailReport.this, users, user_set);
        exp_list.setAdapter(adapter);
    }


}

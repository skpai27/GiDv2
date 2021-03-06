package com.weikang.getindutch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExpenses extends AppCompatActivity {

    Spinner mPayeeSpinner;
    Spinner mGroupSpinner;
    EditText mExpense;
    Button mButtonAdd;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mGroupDatabaseReference;

    String selectedGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expenses_manual);

        //Firebase Auth variables
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        final String mUserId = mUser.getUid();

        mExpense = findViewById(R.id.edittext_Expense);
        //initialise button
        mButtonAdd = findViewById(R.id.button_add);


        //Configuring Payee Spinner
        mPayeeSpinner = findViewById(R.id.spinner_payee);
        //create arrayadapter using String array and a default spinner layout
        ArrayAdapter<CharSequence> payeeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.payee_array, android.R.layout.simple_spinner_item);
        payeeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPayeeSpinner.setAdapter(payeeSpinnerAdapter);

        //Configuring Group Spinner
        mGroupSpinner = findViewById(R.id.spinner_group);
        //create arrayadapter using String array and a default spinner layout
        ArrayAdapter<CharSequence> groupSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.group_array, android.R.layout.simple_spinner_item);
        groupSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupSpinner.setAdapter(groupSpinnerAdapter);

        mGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id){
                selectedGroup = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView parent){

            }
        });

        mButtonAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                long expense = Long.parseLong(mExpense.getText().toString())/2;

                //Firebase Database variables
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                //get reference to the group that was selected
                mGroupDatabaseReference = mFirebaseDatabase.getReference().child("groups").child(selectedGroup).child("members");

                //HARDCODED
                mGroupDatabaseReference.child(mUserId).setValue(expense);
                //below is test1 userid
                mGroupDatabaseReference.child("XIihVerGHgRgqKfAeL2vs9gbkf02").setValue(expense * -1);

                //TODO: retrieve size of group, divide expense, reduce users expense, add to other members expense
                //get original balances\
                //long userBalance =
            }
        });
    }


}

package com.example.nagasudhir.debtonatorsamples;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonEditActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit);
        Button addPersonBtn = (Button) findViewById(R.id.addPersonBtn);
        addPersonBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CustomerDB.KEY_USERNAME, ((EditText) findViewById(R.id.username)).getText().toString());
        initialValues.put(CustomerDB.KEY_PHONE_NUMBER, ((EditText) findViewById(R.id.phone)).getText().toString());
        initialValues.put(CustomerDB.KEY_EMAIL_ID, ((EditText) findViewById(R.id.email)).getText().toString());
        initialValues.put(CustomerDB.KEY_METADATA, ((EditText) findViewById(R.id.metadata)).getText().toString());
        Uri uri = getContentResolver().insert(Customer.CONTENT_URI, initialValues);
        Toast.makeText(PersonEditActivity.this, "Person Inserted", Toast.LENGTH_SHORT).show();
        // starts a new Intent to update/delete a Country
        // pass in row Id to create the Content URI for a single row
        Intent customerView = new Intent(getBaseContext(), PersonViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("uri", uri.toString());
        customerView.putExtras(bundle);
        startActivity(customerView);
    }
}

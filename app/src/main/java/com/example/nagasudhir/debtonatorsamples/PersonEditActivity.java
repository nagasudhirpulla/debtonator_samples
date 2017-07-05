package com.example.nagasudhir.debtonatorsamples;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PersonEditActivity extends AppCompatActivity implements View.OnClickListener {
    private String id = null, mode = "create", uriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit);
        Button addPersonBtn = (Button) findViewById(R.id.addPersonBtn);
        addPersonBtn.setOnClickListener(this);
        // get the values passed to the activity from the calling activity
        // determine the mode - add, update or delete
        if (this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            mode = bundle.getString("mode");
            id = bundle.getString("rowId");
            uriString = bundle.getString("uri");

            Uri uri;
            if (id != null) {
                uri = Uri.parse(Customer.CONTENT_URI + "/" + id);
            } else {
                uri = Uri.parse(uriString);
            }

            if (mode.equals("update")) {
                addPersonBtn.setText("UPDATE");
            }

            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            // Set the Text Attributes from cursor
            cursor.moveToFirst();
            ((TextView) findViewById(R.id.username)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_USERNAME)));
            ((TextView) findViewById(R.id.email)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_EMAIL_ID)));
            ((TextView) findViewById(R.id.phone)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_PHONE_NUMBER)));
            ((TextView) findViewById(R.id.metadata)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_METADATA)));
        }
    }

    @Override
    public void onClick(View v) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(CustomerDB.KEY_USERNAME, ((EditText) findViewById(R.id.username)).getText().toString());
        rowValues.put(CustomerDB.KEY_PHONE_NUMBER, ((EditText) findViewById(R.id.phone)).getText().toString());
        rowValues.put(CustomerDB.KEY_EMAIL_ID, ((EditText) findViewById(R.id.email)).getText().toString());
        rowValues.put(CustomerDB.KEY_METADATA, ((EditText) findViewById(R.id.metadata)).getText().toString());
        Uri uri = Uri.parse(Customer.CONTENT_URI + "/" + id);
        if (mode.equals("create")) {
            uri = getContentResolver().insert(Customer.CONTENT_URI, rowValues);
            Toast.makeText(PersonEditActivity.this, "Person Inserted", Toast.LENGTH_SHORT).show();
        } else if (mode.equals("update")) {
            int numRowsUpdated = getContentResolver().update(Customer.CONTENT_URI, rowValues, "id=?", new String[]{id});
            Toast.makeText(PersonEditActivity.this, numRowsUpdated + " rows affected", Toast.LENGTH_SHORT).show();
        }
        // starts a new Intent to display the affected person
        // pass in row Id to create the Content URI for a single row
        Intent customerView = new Intent(getBaseContext(), PersonViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("uri", uri.toString());
        bundle.putString("rowId", id);
        customerView.putExtras(bundle);
        customerView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(customerView);
    }
}

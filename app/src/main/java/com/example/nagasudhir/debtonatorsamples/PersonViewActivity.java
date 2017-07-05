package com.example.nagasudhir.debtonatorsamples;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class PersonViewActivity extends AppCompatActivity {

    private String id = null, mode, uriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_view);

        // get the values passed to the activity from the calling activity
        // determine the mode - add, update or delete
        if (this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            mode = bundle.getString("mode");
            id = bundle.getString("rowId");
            uriString = bundle.getString("uri");
        }
        Uri uri;
        if (id != null) {
            uri = Uri.parse(Customer.CONTENT_URI + "/" + id);
        } else {
            uri = Uri.parse(uriString);
            id = uri.getLastPathSegment();
        }
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        // Set the Text Attributes from cursor
        cursor.moveToFirst();
        ((TextView) findViewById(R.id.code)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_ROW_ID)));
        ((TextView) findViewById(R.id.username)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_USERNAME)));
        ((TextView) findViewById(R.id.email)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_EMAIL_ID)));
        ((TextView) findViewById(R.id.phone)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_PHONE_NUMBER)));
        ((TextView) findViewById(R.id.metadata)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_METADATA)));
        ((TextView) findViewById(R.id.created_at)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_CREATED_AT)));
        ((TextView) findViewById(R.id.updated_at)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_UPDATED_AT)));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerEdit = new Intent(getBaseContext(), PersonEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "update");
                bundle.putString("rowId", id);
                customerEdit.putExtras(bundle);
                startActivity(customerEdit);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void deletePerson(View v) {
        // starts a new Intent to delete a Person
        // pass in row Id to create the Content URI for a single row
        Intent customerDelete = new Intent(getBaseContext(), PersonDeleteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("mode", "delete");
        bundle.putString("rowId", id);
        customerDelete.putExtras(bundle);
        startActivity(customerDelete);
    }
}

package com.example.nagasudhir.debtonatorsamples;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
        }

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set the Text Attributes from cursor
        cursor.moveToFirst();
        ((TextView) findViewById(R.id.code)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_ROW_ID)));
        ((TextView) findViewById(R.id.username)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_USERNAME)));
        ((TextView) findViewById(R.id.email)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_EMAIL_ID)));
        ((TextView) findViewById(R.id.phone)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_PHONE_NUMBER)));
        ((TextView) findViewById(R.id.metadata)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_METADATA)));
        ((TextView) findViewById(R.id.created_at)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_CREATED_AT)));
        ((TextView) findViewById(R.id.updated_at)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_UPDATED_AT)));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

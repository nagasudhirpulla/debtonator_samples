package com.example.nagasudhir.debtonatorsamples;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PersonDeleteActivity extends AppCompatActivity {
    private String id = null, mode, uriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the values passed to the activity from the calling activity
        // determine the mode - add, update or delete
        if (this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            mode = bundle.getString("mode");
            id = bundle.getString("rowId");
            uriString = bundle.getString("uri");
        }
        Uri uri = Uri.parse(Customer.CONTENT_URI + "/" + id);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        // Set the Text Attributes from cursor
        cursor.moveToFirst();
        ((TextView) findViewById(R.id.username)).setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_USERNAME)));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void deletePersonBtn(View v) {
        int numRowsUpdated = getContentResolver().delete(Customer.CONTENT_URI, "id=?", new String[]{id});
        Toast.makeText(PersonDeleteActivity.this, numRowsUpdated + " rows affected", Toast.LENGTH_SHORT).show();
        Intent customerList = new Intent(getBaseContext(), HomeActivity.class);
        customerList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(customerList);
    }

}

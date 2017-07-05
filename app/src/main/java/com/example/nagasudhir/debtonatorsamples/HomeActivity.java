package com.example.nagasudhir.debtonatorsamples;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // starts a new Intent to update/delete a Country
                // pass in row Id to create the Content URI for a single row
                Intent customerEdit = new Intent(getBaseContext(), PersonEditActivity.class);
                startActivity(customerEdit);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mListView = (ListView) findViewById(R.id.listview);

        mAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_item_layout,
                null,
                new String[]{CustomerDB.KEY_ROW_ID, CustomerDB.KEY_USERNAME, CustomerDB.KEY_PHONE_NUMBER, CustomerDB.KEY_EMAIL_ID, CustomerDB.KEY_METADATA, CustomerDB.KEY_CREATED_AT, CustomerDB.KEY_UPDATED_AT},
                new int[]{R.id.code, R.id.name, R.id.phone, R.id.email, R.id.metadata, R.id.created_at, R.id.updated_at}, 0);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // display the selected country
                String customer_id =
                        cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_USERNAME));
                Toast.makeText(getApplicationContext(),
                        customer_id, Toast.LENGTH_SHORT).show();

                String rowId =
                        cursor.getString(cursor.getColumnIndexOrThrow(CustomerDB.KEY_ROW_ID));

                // starts a new Intent to update/delete a Country
                // pass in row Id to create the Content URI for a single row
                Intent customerView = new Intent(getBaseContext(), PersonViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "view");
                bundle.putString("rowId", rowId);
                customerView.putExtras(bundle);
                startActivity(customerView);
            }
        });
        /** Creating a loader for populating listview from sqlite database */
        /** This statement, invokes the method onCreatedLoader() */
        getSupportLoaderManager().initLoader(0, null, this);
    }

    public void editPersonWithListBtn(View v) {
        String rowId = ((TextView) ((ViewGroup) v.getParent()).findViewById(R.id.code)).getText().toString();
        // starts a new Intent to update/delete a Country
        // pass in row Id to create the Content URI for a single row
        Intent customerEdit = new Intent(getBaseContext(), PersonEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("mode", "update");
        bundle.putString("rowId", rowId);
        customerEdit.putExtras(bundle);
        startActivity(customerEdit);
    }

    public void deletePersonWithListBtn(View v) {
        String rowId = ((TextView) ((ViewGroup) v.getParent()).findViewById(R.id.code)).getText().toString();
        // starts a new Intent to delete a Person
        // pass in row Id to create the Content URI for a single row
        Intent customerDelete = new Intent(getBaseContext(), PersonDeleteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("mode", "delete");
        bundle.putString("rowId", rowId);
        customerDelete.putExtras(bundle);
        startActivity(customerDelete);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A callback method invoked by the loader when initLoader() is called
     */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri uri = Customer.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    /**
     * A callback method, invoked after the requested content provider returned all the data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        mAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);
    }
}

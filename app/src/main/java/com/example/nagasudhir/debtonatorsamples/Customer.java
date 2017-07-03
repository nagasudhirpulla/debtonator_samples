package com.example.nagasudhir.debtonatorsamples;

/**
 * Created by Nagasudhir on 7/2/2017.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * A custom Content Provider to do the database operations
 */
public class Customer extends ContentProvider {

    public static final String PROVIDER_NAME = "com.example.nagasudhir.debtonatorsamples.customer";

    /**
     * A uri to do operations on people_details table. A content provider is identified by its uri
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/customers");

    /**
     * Constants to identify the requested operation
     */
    private static final int CUSTOMERS = 1;
    private static final int CUSTOMER = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "customers", CUSTOMERS);
        uriMatcher.addURI(PROVIDER_NAME, "customers/#", CUSTOMER);
    }

    /**
     * This content provider does the database operations by this object
     */
    CustomerDB mCustomerDB;

    /**
     * A callback method which is invoked when the content provider is starting up
     */
    @Override
    public boolean onCreate() {
        mCustomerDB = new CustomerDB(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * A callback method which is by the default content uri
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) == CUSTOMERS) {
            return mCustomerDB.getAllCustomers();
        } else if (uriMatcher.match(uri) == CUSTOMER) {
            return mCustomerDB.getCustomerById(uri.getLastPathSegment());
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        long id = mCustomerDB.insertCustomer(values);
        return Uri.parse(Customer.CONTENT_URI + "/" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }
}
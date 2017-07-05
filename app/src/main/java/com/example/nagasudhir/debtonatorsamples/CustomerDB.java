package com.example.nagasudhir.debtonatorsamples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nagasudhir on 7/2/2017.
 */
public class CustomerDB extends SQLiteOpenHelper {

    /**
     * Database name
     */
    private static String DBNAME = "debtonator_samples";

    /**
     * Version number of the database
     */
    private static int VERSION = 1;

    /**
     * Field 1 of the table people_details, which is the primary key
     */
    public static final String KEY_ROW_ID = "id";

    /**
     * Field 2 of the table people_details, stores the customer code
     */
    public static final String KEY_USERNAME = "username";

    /**
     * Field 3 of the table people_details, stores the customer name
     */
    public static final String KEY_PHONE_NUMBER = "phone_number";

    /**
     * Field 4 of the table people_details, stores the phone number of the customer
     */
    public static final String KEY_EMAIL_ID = "email_id";

    /**
     * Field 5 of the table people_details, stores the phone number of the customer
     */
    public static final String KEY_METADATA = "metadata";

    /**
     * Field 6 of the table people_details, stores the phone number of the customer
     */
    public static final String KEY_CREATED_AT = "created_at";

    /**
     * Field 7 of the table people_details, stores the phone number of the customer
     */
    public static final String KEY_UPDATED_AT = "updated_at";

    /**
     * A constant, stores the the table name
     */
    private static final String DATABASE_TABLE = "people_details";

    /**
     * An instance variable for SQLiteDatabase
     */
    private SQLiteDatabase mDB;

    /**
     * Constructor
     */
    public CustomerDB(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    /**
     * This is a callback method, invoked when the method
     * getReadableDatabase() / getWritableDatabase() is called
     * provided the database does not exists
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL("BEGIN TRANSACTION");
        String sql = "DROP TABLE IF EXISTS people_details;";
        db.execSQL(sql);
        sql = "CREATE TABLE people_details (" +
                "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "`username` TEXT NOT NULL UNIQUE," +
                "`phone_number` TEXT UNIQUE," +
                "`email_id` TEXT," +
                "`metadata` TEXT," +
                "`created_at` INTEGER NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "`updated_at` INTEGER NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TRIGGER people_details_updated_at_trigger AFTER \n" +
                "  UPDATE \n" +
                "  ON people_details BEGIN \n" +
                "  UPDATE people_details \n" +
                "  SET    updated_at = DATETIME('now') \n" +
                "  WHERE  id = NEW.id; \n" +
                "END;";
        db.execSQL(sql);

        // Create some rows
        CustomerPojo[] persons = new CustomerPojo[5];
        persons[0] = new CustomerPojo("SUDHIR", "9819679462", "nagasudhirpulla@gmail.com", "nothing");
        persons[1] = new CustomerPojo("kishore", "9888545242", "kishore@gmail.com", "na");
        persons[2] = new CustomerPojo("prashanth", "1234567890", "prashanth.eeenitw@gmail.com", "nothing");
        persons[3] = new CustomerPojo("aditya", "3451842451", "adityamahesh810@gmail.com", "stupid fellow");
        persons[4] = new CustomerPojo("naveen", "2645542155", "kotinaveen@gmail.com", "");
        for (int i = 0; i < persons.length; i++) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_USERNAME, persons[i].getUsername());
            initialValues.put(KEY_PHONE_NUMBER, persons[i].getPhone());
            initialValues.put(KEY_EMAIL_ID, persons[i].getEmail());
            initialValues.put(KEY_METADATA, persons[i].getMetadata());
            db.insert(DATABASE_TABLE, null, initialValues);
        }
        /*sql = "INSERT INTO `people_details` VALUES (1,'SUDHIR','9819679462','nagasudhirpulla@gmail.com','nothing',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);";
        db.execSQL(sql);
        sql = "INSERT INTO `people_details` VALUES (2,'kishore','9888545242','kishore@gmail.com','na',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);";
        db.execSQL(sql);
        sql = "INSERT INTO `people_details` VALUES (3,'prashanth','1234567890','prashanth.eeenitw@gmail.com','nothing',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);";
        db.execSQL(sql);
        sql = "INSERT INTO `people_details` VALUES (4,'aditya','3451842451','adityamahesh810@gmail.com','stupid fellow',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);";
        db.execSQL(sql);
        sql = "INSERT INTO `people_details` VALUES (5,'naveen','2645542155','kotinaveen@gmail.com','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);";
        db.execSQL(sql);*/
        db.execSQL("COMMIT");
    }

    /**
     * Returns all the customers in the table
     */
    public Cursor getAllCustomers() {
        return mDB.rawQuery("SELECT id AS _id, * FROM people_details;", null);
    }

    /**
     * Returns a the customers in the table
     */
    public Cursor getCustomerById(String idString) {
        return mDB.rawQuery("SELECT id AS _id, * FROM people_details WHERE id = ?;", new String[]{idString});
    }

    /**
     * Creates a the customers in the table
     */
    public long insertCustomer(ContentValues values) {
        return mDB.insert(DATABASE_TABLE, null, values);
    }

    /**
     * Uodates a the customers in the table
     */
    public long updateCustomer(ContentValues values, String selection, String[] selectionArgs) {
        return mDB.update(DATABASE_TABLE, values, selection, selectionArgs);
    }

    /**
     * Deletes a the customers in the table
     */
    public long deleteCustomer(String selection, String[] selectionArgs) {
        return mDB.delete(DATABASE_TABLE, selection, selectionArgs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}

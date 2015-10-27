package com.example.novak.librarysystem.db_processing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Member;
import model.MemberList;

/**
 * Created by n.thanh on 10/27/2015.
 */
public class DatabaseManager {
    private static final String DB_NAME = "library";
    private static final int DB_VERSION = 1;
    private static final String MEMBER_TABLE = "member_table";
    private static final String MEMBER_ID_ROW = "_id";
    private static final String MEMBER_NAME_ROW = "member_name";
    private static final String MEMBER_PHONE_ROW = "member_phone";
    private static final String MEMBER_ADDRESS_ROW = "member_address";
    private static final String MEMBER_STATUS_ROW = "member_status";
    private static final String MEMBER_IN_JAIL_ROW = "member_in_jail";
    private static final String CREATE_TABLE_MEMBER =
            "CREATE TABLE " + MEMBER_TABLE + "("
                    + MEMBER_ID_ROW + " INTEGER PRIMARY KEY,"
                    + MEMBER_NAME_ROW + " TEXT,"
                    + MEMBER_PHONE_ROW + " TEXT,"
                    + MEMBER_ADDRESS_ROW + " TEXT,"
                    + MEMBER_STATUS_ROW + " INTEGER,"
                    + MEMBER_IN_JAIL_ROW + " INTEGER" + ");";
    private static final String BOOK_TABLE = "BOOK_TABLE";
    private static final String BOOK_ID_ROW = "_ID";
    private static final String BOOK_TITLE_ROW = "BOOK_TITLE";
    private static final String BOOK_AUTHOR_ROW = "BOOK_AUTHOR";
    private static final String BOOK_BORROWED_BY = "BOOK_BORROWED_BY";
    private static final String BOOK_DUE_DATE = "BOOK_DUE_DATE";
    private static final String CREATE_TABLE_BOOK =
            "CREATE TABLE " + BOOK_TABLE + "("
                    + BOOK_ID_ROW + " INTEGER PRIMARY KEY,"
                    + BOOK_TITLE_ROW + " TEXT,"
                    + BOOK_AUTHOR_ROW + " TEXT,"
                    + BOOK_BORROWED_BY + " INTEGER,"
                    + BOOK_DUE_DATE + " INTEGER" + ");";
    private static final String HOLD_TABLE = "HOLD_TABLE";
    private static final String HOLD_MEMBER_ID = "MEMBER_ID";
    private static final String HOLD_BOOK_ID = "BOOK_ID";
    private static final String HOLD_END_DATE = "HOLD_END_DATE";
    private static final String CREATE_TABLE_HOLD =
            "CREATE TABLE " + HOLD_TABLE + "("
                    + HOLD_MEMBER_ID + " INTEGER,"
                    + HOLD_BOOK_ID + " TEXT,"
                    + HOLD_END_DATE + " INTEGER" + ");";
    private static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    private static final String TRANSACTION_MEMBER_ID = "TRANSACTION_MEMBER_ID";
    private static final String TRANSACTION_BOOK_ID = "TRANSACTION_BOOK_ID";
    private static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    private static final String TRANSACTION_DATE = "TRANSACTION_DATE";
    private static final String CREATE_TABLE_TRANSACTION =
            "CREATE TABLE " + TRANSACTION_TABLE + "("
                    + TRANSACTION_MEMBER_ID + " INTEGER,"
                    + TRANSACTION_BOOK_ID + " INTEGER,"
                    + TRANSACTION_TYPE + " TEXT,"
                    + TRANSACTION_DATE + " INTEGER" + ");";
    private SQLiteDatabase mDatabase;
    private LibrarySQLiteOpenHelper mHelper;
    private Context mContext;

    public DatabaseManager(Context context) {
        mContext = context;
        mHelper = new LibrarySQLiteOpenHelper(context);
        //mDatabase = helper.getReadableDatabase();
    }

    public long addMember(Member member) {
        ContentValues values = new ContentValues();
        values.put(MEMBER_ID_ROW, member.getId());
        values.put(MEMBER_NAME_ROW, member.getName());
        values.put(MEMBER_PHONE_ROW, member.getPhone());
        values.put(MEMBER_ADDRESS_ROW, member.getAddress());
        values.put(MEMBER_STATUS_ROW, member.getStatus());
        values.put(MEMBER_IN_JAIL_ROW, (member.isInJail() ? 1 : 0));


        mDatabase = mHelper.getWritableDatabase();
        return mDatabase.insert(MEMBER_TABLE, null, values);
    }

    public MemberList getAllMembers() {
        String selectQuery = "SELECT * FROM " + MEMBER_TABLE;
        mDatabase = mHelper.getWritableDatabase();
        MemberList memberList = MemberList.getInstance();

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Member member = new Member();

            } // TODO: 10/27/2015 id problem - should initial id during inserting item
        }
    }

    private class LibrarySQLiteOpenHelper extends SQLiteOpenHelper {

        public LibrarySQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_MEMBER);
            db.execSQL(CREATE_TABLE_BOOK);
            db.execSQL(CREATE_TABLE_HOLD);
            db.execSQL(CREATE_TABLE_TRANSACTION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String DROP_TABLE = "DROP TABLE IF EXISTS ";
            db.execSQL(DROP_TABLE + MEMBER_TABLE);
            db.execSQL(DROP_TABLE + BOOK_TABLE);
            db.execSQL(DROP_TABLE + HOLD_TABLE);
            db.execSQL(DROP_TABLE + TRANSACTION_TABLE);

            onCreate(db);
        }
    }
}

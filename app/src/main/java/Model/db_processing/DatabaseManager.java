package Model.db_processing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Model.Member;
import Model.MemberList;

/**
 * Created by n.thanh on 10/27/2015.
 */
public class DatabaseManager {
    public static final String DB_NAME = "library";
    public static final int DB_VERSION = 1;
    public static final String MEMBER_TABLE = "member_table";
    public static final String MEMBER_ID_ROW = "_id";
    public static final String MEMBER_NAME_ROW = "member_name";
    public static final String MEMBER_PHONE_ROW = "member_phone";
    public static final String MEMBER_ADDRESS_ROW = "member_address";
    public static final String MEMBER_STATUS_ROW = "member_status";
    public static final String MEMBER_IN_JAIL_ROW = "member_in_jail";
    public static final String CREATE_TABLE_MEMBER =
            "CREATE TABLE " + MEMBER_TABLE + "("
                    + MEMBER_ID_ROW + " INTEGER PRIMARY KEY,"
                    + MEMBER_NAME_ROW + " TEXT,"
                    + MEMBER_PHONE_ROW + " TEXT,"
                    + MEMBER_ADDRESS_ROW + " TEXT,"
                    + MEMBER_STATUS_ROW + " INTEGER,"
                    + MEMBER_IN_JAIL_ROW + " INTEGER" + ");";
    public static final String BOOK_TABLE = "BOOK_TABLE";
    public static final String BOOK_ID_ROW = "_ID";
    public static final String BOOK_TITLE_ROW = "BOOK_TITLE";
    public static final String BOOK_AUTHOR_ROW = "BOOK_AUTHOR";
    public static final String BOOK_BORROWED_BY = "BOOK_BORROWED_BY";
    public static final String BOOK_DUE_DATE = "BOOK_DUE_DATE";
    public static final String CREATE_TABLE_BOOK =
            "CREATE TABLE " + BOOK_TABLE + "("
                    + BOOK_ID_ROW + " INTEGER PRIMARY KEY,"
                    + BOOK_TITLE_ROW + " TEXT,"
                    + BOOK_AUTHOR_ROW + " TEXT,"
                    + BOOK_BORROWED_BY + " INTEGER,"
                    + BOOK_DUE_DATE + " INTEGER" + ");";
    public static final String HOLD_TABLE = "HOLD_TABLE";
    public static final String HOLD_MEMBER_ID = "MEMBER_ID";
    public static final String HOLD_BOOK_ID = "BOOK_ID";
    public static final String HOLD_END_DATE = "HOLD_END_DATE";
    public static final String CREATE_TABLE_HOLD =
            "CREATE TABLE " + HOLD_TABLE + "("
                    + HOLD_MEMBER_ID + " INTEGER,"
                    + HOLD_BOOK_ID + " TEXT,"
                    + HOLD_END_DATE + " INTEGER" + ");";
    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String TRANSACTION_MEMBER_ID = "TRANSACTION_MEMBER_ID";
    public static final String TRANSACTION_BOOK_ID = "TRANSACTION_BOOK_ID";
    public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public static final String TRANSACTION_DATE = "TRANSACTION_DATE";
    public static final String CREATE_TABLE_TRANSACTION =
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
        mDatabase = mHelper.getWritableDatabase();

        //Confirm whether member already in registered
        String selectQuery = "SELECT * FROM " + MEMBER_TABLE + " WHERE "
                + "(" + MEMBER_NAME_ROW + " = " + "'" + member.getName() + "'"
                + " AND " + MEMBER_ADDRESS_ROW + " = " + "'" + member.getAddress() + "'" + ")";

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return -1;
        }
        cursor.close();

        //member has not been registered
        ContentValues values = new ContentValues();
        //values.put(MEMBER_ID_ROW, member.getId());
        values.put(MEMBER_NAME_ROW, member.getName());
        values.put(MEMBER_PHONE_ROW, member.getPhone());
        values.put(MEMBER_ADDRESS_ROW, member.getAddress());
        values.put(MEMBER_STATUS_ROW, member.getStatus());
        values.put(MEMBER_IN_JAIL_ROW, (member.isInJail() ? 1 : 0));

        return mDatabase.insert(MEMBER_TABLE, null, values);
    }

    public void getAllMembers() throws Exception {
        String selectQuery = "SELECT * FROM " + MEMBER_TABLE;
        mDatabase = mHelper.getWritableDatabase();
        MemberList memberList = MemberList.getInstance();

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String memberName, memberAddress, memberPhone;
                memberName = cursor.getString(cursor.getColumnIndex(MEMBER_NAME_ROW));
                memberAddress = cursor.getString(cursor.getColumnIndex(MEMBER_ADDRESS_ROW));
                memberPhone = cursor.getString(cursor.getColumnIndex(MEMBER_PHONE_ROW));

                Member member = new Member(memberName, memberAddress, memberPhone);
                member.setId(cursor.getLong(cursor.getColumnIndex(MEMBER_ID_ROW)));
                boolean b = (cursor.getInt(cursor.getColumnIndex(MEMBER_IN_JAIL_ROW)) == 1);
                member.setIsInJail(b);
                member.setStatus(cursor.getInt(cursor.getColumnIndex(MEMBER_STATUS_ROW)));
                // TODO: 10/30/2015 reading Hold data
                // TODO: 10/30/2015 reading ISSUED BOOKS DATA
                // TODO: 10/30/2015 reading TRANSACTION DATA
                try {
                    memberList.insert(member);
                } catch (Exception e) {
                    cursor.close();
                    throw e;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
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

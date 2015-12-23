package Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.novak.librarysystem.R;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import Model.db_processing.DatabaseManager;

/**
 * Created by Novak on 9/6/2015.
 */
public class Library {

    public static final int MAX_HOLD_DAYS = 30;
    public static final int MAX_ISSUABLE = 2;
    public static final int MAX_NUMBER_OF_HOLDS = 5;
    public static final int BOOK_REMOVE_OK = 0;
    public static final int BOOK_REMOVE_FAIL_BORROWED = 1;
    public static final int BOOK_REMOVE_FAIL_HOLD = 2;
    public static final int BOOK_REMOVE_FAIL_NOT_EXIST = 3;
    public static final int BOOK_RETURN_OK_NO_HOLD = 0;
    public static final int BOOK_RETURN_FAIL_NOT_ISSUED_YET = 1;
    public static final int BOOK_RETURN_OK_HOLD = 2;
    public static final int BOOK_RETURN_FAIL_NOT_EXIST = 3;
    public static final int BOOK_PLACE_HOLD_FAIL_NOT_EXIST = 3;
    public static final int BOOK_PLACE_HOLD_FAIL_NOT_ISSUED = 4;
    public static final int REMOVE_HOLD_FAIL_MEMBER_NOT_EXIST = 1;
    public static final int REMOVE_HOLD_FAIL_BOOK_NOT_EXIST = 2;
    public static final int BOOK_PLACE_HOLD_OK = 0;
    public static final int BOOK_REMOVE_HOLD_OK = 0;
    public static final int BOOK_REMOVE_HOLD_FAIL = 3;
    public static final int ISSUE_BOOK_FAIL_BOOK_NOT_AVAILABLE = 796;
    public static final int ISSUE_BOOK_FAIL_BOOK_MEMBER_ISSUE_LIMIT = 797;
    public static final int ISSUE_BOOK_FAIL_BOOK_NOT_EXIST = 798;
    public static final int ISSUE_BOOK_OK = 799;
    private volatile static Library sUniqueInstance;
    private static Context sAppContext;
    private MemberList mMemberList;
    private Catalog mCatalog;

    private Library(Context appContext) {
        mMemberList = MemberList.getInstance(sAppContext);
        mCatalog = Catalog.getInstance(appContext);
        sAppContext = appContext;
    }

    public static Library getInstance(Context appContext) {
        if (sUniqueInstance == null) {
            synchronized (Library.class) {
                if (sUniqueInstance == null) {
                    sUniqueInstance = new Library(appContext);
                    new LoadingData().execute();
                }
            }
        }
        return sUniqueInstance;
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public int removeHold(long memberId, long bookId) {
        Member member = mMemberList.search(memberId);
        if (member == null) return REMOVE_HOLD_FAIL_MEMBER_NOT_EXIST;
        Book book = mCatalog.search(bookId);
        if (book == null) return REMOVE_HOLD_FAIL_BOOK_NOT_EXIST;
        return (member.removeHold(bookId) && book.removeHold(memberId)) ?
                BOOK_REMOVE_HOLD_OK : BOOK_REMOVE_HOLD_FAIL;
    }

    public Member addMember(String name, String address, String phone) throws Exception {
        Member member = new Member(name, address, phone);
        //writing member to DB
        DatabaseManager databaseManager = new DatabaseManager(sAppContext);
        long memberId = databaseManager.addMember(member);
        if (memberId == -1)
            throw new Exception("Member already existed");
        member.setId(memberId);
        return (mMemberList.insert(member)) ? member : null;
    }

    public Book addBook(String title, String author) {
        Book book = new Book(sAppContext, title, author);
        //writing to DB
        long bookId = -1;
        DatabaseManager databaseManager = new DatabaseManager(sAppContext);
        bookId = databaseManager.addBook(book);
        if (bookId == -1) {
            return null;
        }
        book.setId(bookId);
        return mCatalog.insert(book) ? book : null;
    }

    public int renew(Book book) {
        int renewResult = book.renewBook();
        if (renewResult == Book.RENEW_OK) {
            new UpdateData().execute(null, book);
        }
        return renewResult;
    }

    public List<Long> renewRequest(long memberId) {
        Member member = mMemberList.search(memberId);
        if (member == null) return null;
        return member.getIssuedBooks();
    }

    public Member processHold(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return null;
        Hold hold = book.getNextHold();
        if (hold == null) return null;
        Member member = hold.getMember();
        if (member == null) return null;
        book.removeHold(hold);
        member.removeHold(hold);
        return member;
    }

    public int issueBook(long bookId, Member member) {
        Book book = mCatalog.search(bookId);
        if (book == null) return ISSUE_BOOK_FAIL_BOOK_NOT_EXIST;
        return issueBook(book, member);
    }

    public int issueBook(Book book, Member member) {
        int issueResult = book.issue(member);
        switch (issueResult) {
            case Book.ISSUE_FAIL_NOT_AVAILABLE:
                return ISSUE_BOOK_FAIL_BOOK_NOT_AVAILABLE;
            case Book.ISSUE_FAIL_MEMBER_IN_JAIL:
                return ISSUE_BOOK_FAIL_BOOK_MEMBER_ISSUE_LIMIT;
            default:
                member.issueBook(book);
                //Update to DB
                new UpdateData().execute(member, book);
                return ISSUE_BOOK_OK;
        }
    }

    public Member searchMember(long memberId) {
        return mMemberList.search(memberId);
    }

    public int placeHold(long memberId, long bookId, int duration) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_PLACE_HOLD_FAIL_NOT_EXIST;
        Member member = book.getBorrowedBy();
        if (member != null) return BOOK_PLACE_HOLD_FAIL_NOT_ISSUED;
        member = mMemberList.search(memberId);
        Hold hold = new Hold(sAppContext, book, member, duration);
        book.placeHold(hold);
        member.placeHold(hold);
        return BOOK_PLACE_HOLD_OK;
    }

    public int returnBook(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_RETURN_FAIL_NOT_EXIST;
        Member member = book.returnBook();
        if (member == null) return BOOK_RETURN_FAIL_NOT_ISSUED_YET;
        member.returnBook(book);
        //Update to DB
        new UpdateData().execute(member, book);
        if (book.hasHold()) return BOOK_RETURN_OK_HOLD;
        return BOOK_RETURN_OK_NO_HOLD;
    }

    public int removeBook(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_REMOVE_FAIL_NOT_EXIST;
        if (book.hasHold()) return BOOK_REMOVE_FAIL_HOLD;
        if (book.getBorrowedBy() != null) return BOOK_REMOVE_FAIL_BORROWED;

        //Delete book on DB
        new DeleteData().execute(null, book);

        //Remove book from Catalog
        mCatalog.remove(book);
        return BOOK_REMOVE_OK;
    }

    public Iterator getTransactions(long memberId, Calendar date) {
        Member member = mMemberList.search(memberId);
        if (member != null) return member.getTransactions(date);
        return null;
    }

    public Catalog getCatalog() {
        return mCatalog;
    }

    public Book searchBook(long id) {
        return mCatalog.search(id);
    }

    private static class LoadingData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LoadingData", "Start restoring data");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("LoadingData", "Loading...");
            DatabaseManager databaseManager = new DatabaseManager(sAppContext);
            try {
                databaseManager.loadAllData();
            } catch (Exception e) {
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.d("LoadingData", "Data overlap");
            Toast.makeText(sAppContext, R.string.member_data_read_overlap_error, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("LoadingData", "Finish loading data");
        }
    }

    private class DeleteData extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            // TODO: 12/12/2015 implement removing member, book
            Log.i("DeleteData", "Deleting book");
            Member member = (Member) params[0];
            Book book = (Book) params[1];
            DatabaseManager databaseManager = new DatabaseManager(sAppContext);
            databaseManager.deleteData(member, book);
            return null;
        }
    }

    private class UpdateData extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            Log.i("UpdateData", "Updating data");
            Member member = (Member) params[0];
            Book book = (Book) params[1];
            DatabaseManager databaseManager = new DatabaseManager(sAppContext);
            databaseManager.update(member, book);
            return null;
        }
    }
}

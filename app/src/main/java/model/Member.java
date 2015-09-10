package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Novak on 9/6/2015.
 */
public class Member {

    private long mId;
    private String mName;
    private String mAddress;
    private String mPhone;
    private List<Transaction> mTransactions;
    private List<Hold> mHolds;
    private List<Book> mIssuedBooks;

    private Member() {
    }

    public Member(String mName, String mAddress, String mPhone) {
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
        mIssuedBooks = new ArrayList<Book>();
        mTransactions = new ArrayList<Transaction>();
        mHolds = new ArrayList<Hold>();
    }

    public boolean issueBook(Book book) {
        return mIssuedBooks.add(book);
    }

    public boolean returnBook(Book book) {
        return mIssuedBooks.remove(book);
    }

    public void placeHold(Hold hold) {
        mHolds.add(hold);
    }

    public boolean removeHold(long bookId) {
        Iterator iterator = mHolds.iterator();
        if (iterator.hasNext()) {
            Object object = iterator.next();
            if (object instanceof Hold) {
                Hold hold = (Hold) object;
                if (hold.getBook().getId() == bookId) {
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeHold(Hold hold) {
        return mHolds.remove(hold);
    }

    public Iterator getTransactions() {
        return mTransactions.iterator();
    }

    public Iterator getIssuedBooks() {
        return mIssuedBooks.iterator();
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getPhone() {
        return mPhone;
    }
}

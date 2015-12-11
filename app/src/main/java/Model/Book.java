package Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Novak on 9/6/2015.
 */
public class Book {
    public static final int RENEW_OK = 0;
    public static final int ON_HOLD_RENEW_FAILED = 1;
    public static final int ISSUE_FAIL_NOT_AVAILABLE = 2;
    public static final int ISSUE_FAIL_MEMBER_IN_JAIL = 3;
    public static final int ISSUE_OK = 4;
    private static Context sAppContext;
    private long mId;
    private String mTitle;
    private String mAuthor;
    private List<Hold> mHolds;
    private long mBorrowedBy;       //id of borrower
    private Calendar mDueDate;

    private Book() {
    }

    public Book(Context context, String title, String author) {
        //mId = Catalog.getNumberOfBooks();
        mTitle = title;
        mAuthor = author;
        mBorrowedBy = -1;
        mDueDate = null;
        mHolds = new ArrayList<Hold>();
        sAppContext = context;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public Member returnBook() {
        mDueDate = null;
        Member member = MemberList.getInstance(sAppContext).search(mBorrowedBy);
        mBorrowedBy = -1;
        return member;
    }

    public int renewBook() {
        if (mHolds.isEmpty()) {
            mDueDate.add(Calendar.MONTH, 1);
            MemberList.getInstance(sAppContext).search(mBorrowedBy).addTransaction(
                    new Transaction(mTitle, Transaction.RENEW_TRANSACTION));
            return Book.RENEW_OK;
        } else
            return ON_HOLD_RENEW_FAILED;
    }

    public boolean removeHold(long memberId) {
        Iterator iterator = mHolds.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Hold) {
                Hold hold = (Hold) o;
                if (hold.getMember().getId() == memberId) {
                    mHolds.remove(hold);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeHold(Hold hold) {
        return mHolds.remove(hold);
    }

    public boolean hasHold() {
        boolean b = mHolds.iterator().hasNext();
        return b;
    }

    public Hold getNextHold() {
        try {
            return mHolds.iterator().next();
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    public void placeHold(Hold hold) {
        mHolds.add(hold);
    }

    public int issue(Member member) {

        if (mBorrowedBy != -1) return ISSUE_FAIL_NOT_AVAILABLE;
        if (member.isInJail()) return ISSUE_FAIL_MEMBER_IN_JAIL;
        mBorrowedBy = member.getId();
        mDueDate = Calendar.getInstance();
        mDueDate.add(Calendar.DAY_OF_MONTH, 30);
        return ISSUE_OK;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public Iterator getHolds() {
        return mHolds.iterator();
    }

    public Member getBorrowedBy() {
        return MemberList.getInstance(sAppContext).search(mBorrowedBy);
    }

    public Long getBorrowerId() {
        return mBorrowedBy;
    }

    public void setBorrower(Long memberId) {
        mBorrowedBy = memberId;
    }

    public Calendar getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Long dueDate) {
        mDueDate = Calendar.getInstance();
        mDueDate.setTimeInMillis(dueDate);
    }
}

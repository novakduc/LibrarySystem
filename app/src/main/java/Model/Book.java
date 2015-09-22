package Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by Novak on 9/6/2015.
 */
public class Book {
    public static final int RENEW_OK = 0;
    public static final int ON_HOLD_RENEW_FAILED = 1;
    private UUID mId;
    private String mTitle;
    private String mAuthor;
    private List<Hold> mHolds;
    private Member mBorrowedBy;
    private Calendar mDueDate;

    private Book() {
    }

    public Book(UUID id, String title, String author) {
        mId = id;
        mTitle = title;
        mAuthor = author;
        mHolds = new ArrayList<Hold>();
    }

    public Member returnBook() {
        mDueDate = null;
        Member member = mBorrowedBy;
        mBorrowedBy = null;
        return member;
    }

    public int renewBook() {
        if (mHolds.isEmpty()) {
            mDueDate.add(Calendar.DAY_OF_MONTH, 30);
            mBorrowedBy.addTransaction(new Transaction(mTitle, Transaction.RENEW_TRANSACTION));
            return Book.RENEW_OK;
        } else
            return ON_HOLD_RENEW_FAILED;
    }

    public boolean removeHold(UUID memberId) {
        Iterator iterator = mHolds.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Hold) {
                Hold hold = (Hold) o;
                if (hold.getMember().getId().equals(memberId)) {
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
        return mHolds.iterator().hasNext();
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

    public boolean issue(Member member) {

        if (member.isInJail()) return false;
        mBorrowedBy = member;
        mDueDate = Calendar.getInstance();
        mDueDate.add(Calendar.DAY_OF_MONTH, 30);
        return true;
    }

    public UUID getId() {
        return mId;
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
        return mBorrowedBy;
    }

    public Calendar getDueDate() {
        return mDueDate;
    }
}

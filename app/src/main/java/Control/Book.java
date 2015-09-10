package Control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Novak on 9/6/2015.
 */
public class Book {
    private long mId;
    private String mTitle;
    private String mAuthor;
    private List<Hold> mHolds;
    private Member mBorrowedBy;
    private Calendar mDueDate;

    private Book() {
    }

    public Book(long id, String title, String author) {
        mId = id;
        mTitle = title;
        mAuthor = author;
        mHolds = new ArrayList<Hold>();
    }

    public boolean removeHold(String memberId) {
        // TODO: 9/10/2015
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
        mBorrowedBy = member;
        // TODO: 9/10/2015 check member's condition before lending book
        return true;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public List getHold() {
        return mHolds;
    }

    public Member getBorrowedBy() {
        return mBorrowedBy;
    }

    public Calendar getDueDate() {
        return mDueDate;
    }
}

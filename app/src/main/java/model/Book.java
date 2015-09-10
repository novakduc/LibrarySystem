package model;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Novak on 9/6/2015.
 */
public class Book {
    private long mId;
    private String mTitle;
    private String mAuthor;
    private List mHold;
    private Member mBorrowedBy;
    private Calendar mDueDate;

    public Book(long id, String title, String author) {
        mId = id;
        mTitle = title;
        mAuthor = author;
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
        return mHold;
    }

    public Member getBorrowedBy() {
        return mBorrowedBy;
    }

    public Calendar getDueDate() {
        return mDueDate;
    }
}

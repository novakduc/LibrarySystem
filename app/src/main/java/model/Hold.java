package model;

import java.util.Calendar;

/**
 * Created by Novak on 9/6/2015.
 */
public class Hold {
    private Calendar mEndDate;
    private Book mBook;
    private Member mMember;

    private Hold() {
    }

    public Hold(Book book, Member member, int numberOfDays) {
        this.mBook = book;
        this.mMember = member;
        int tempNumberOfDays = numberOfDays;
        if (tempNumberOfDays > Library.MAX_HOLD_DAYS)
            tempNumberOfDays = Library.MAX_HOLD_DAYS;
        else {
            if (tempNumberOfDays < 0)
                tempNumberOfDays = 0;
        }
        mEndDate = Calendar.getInstance();
        mEndDate.add(Calendar.DAY_OF_MONTH, tempNumberOfDays);
    }

    public Book getBook() {
        return mBook;
    }

    public Member getMember() {
        return mMember;
    }

    public boolean isValided() {
        return mEndDate.before(Calendar.getInstance());
    }

    public Calendar getEndDate() {
        return mEndDate;
    }
}

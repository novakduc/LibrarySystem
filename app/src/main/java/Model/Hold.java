package Model;

import java.util.Calendar;

/**
 * Created by Novak on 9/6/2015.
 */
public class Hold {
    private Calendar mEndDate;
    private long mBookId;
    private long mMemberId;

    private Hold() {
    }

    public Hold(long bookId, long memberId, long endDate) {
        mBookId = bookId;
        mMemberId = memberId;
        mEndDate.setTimeInMillis(endDate);
    }

    public Hold(Book book, Member member, int numberOfDays) {
        this.mBookId = book.getId();
        this.mMemberId = member.getId();
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
        return Catalog.getInstance().search(mBookId);
    }

    public Member getMember() {
        return MemberList.getInstance().search(mMemberId);
    }

    public boolean isValided() {
        return mEndDate.before(Calendar.getInstance());
    }

    public Calendar getEndDate() {
        return mEndDate;
    }
}

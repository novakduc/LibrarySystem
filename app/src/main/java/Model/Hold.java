package Model;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by Novak on 9/6/2015.
 */
public class Hold {
    private static Context sAppContext;
    private Calendar mEndDate;
    private long mBookId;
    private long mMemberId;

    private Hold() {
    }

    public Hold(Context context, long bookId, long memberId, long endDate) {
        mBookId = bookId;
        mMemberId = memberId;
        mEndDate.setTimeInMillis(endDate);
        sAppContext = context;
    }

    public Hold(Context context, Book book, Member member, int numberOfDays) {
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
        sAppContext = context;
    }

    public Book getBook() {
        return Catalog.getInstance(sAppContext).search(mBookId);
    }

    public Member getMember() {
        return MemberList.getInstance(sAppContext).search(mMemberId);
    }

    public boolean isValided() {
        return mEndDate.before(Calendar.getInstance());
    }

    public Calendar getEndDate() {
        return mEndDate;
    }
}

package Control;

import java.util.Calendar;

/**
 * Created by Novak on 9/6/2015.
 */
public class Transaction {
    public static final String ISSUE_TRANSACTION = "Issue";
    public static final String RETURN_TRANSACTION = "Return";
    public static final String PLACE_HOLD_TRANSACTION = "Place a hold";
    public static final String REMOVE_HOLD = "Remove a hold";
    public static final String RENEW_TRANSACTION = "Renew a book";
    private Calendar mDate;
    private String mBookTitle;
    private String mType;

    private Transaction() {
    }

    public Transaction(String bookTitle, String type) {
        mBookTitle = bookTitle;
        mType = type;
    }

    public boolean OnDate(Calendar date) {
        return mDate.equals(date);
    }

    public Calendar getDate() {
        return mDate;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public String getType() {
        return mType;
    }
}

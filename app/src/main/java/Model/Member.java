package Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Novak on 9/6/2015.
 */
public class Member {

    public static int NORMAL_STATUS = 0;
    private UUID mId;
    private String mName;
    private String mAddress;
    private String mPhone;
    private List<Transaction> mTransactions;
    private List<Hold> mHolds;
    private List<Book> mIssuedBooks;
    private int mStatus;
    private boolean mIsInJail;

    private Member() {
    }

    public Member(String mName, String mAddress, String mPhone) {
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
        mId = UUID.randomUUID();
        mIssuedBooks = new ArrayList<Book>(Library.MAX_ISSUABLE);
        mTransactions = new ArrayList<Transaction>();
        mHolds = new ArrayList<Hold>(Library.MAX_NUMBER_OF_HOLDS);
        mIsInJail = false;
        mStatus = NORMAL_STATUS;
    }

    public boolean isInJail() {
        return mIsInJail;
    }

    public void setIsInJail(boolean isInJail) {
        mIsInJail = isInJail;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public boolean issueBook(Book book) {
        mTransactions.add(new Transaction(book.getTitle(), Transaction.ISSUE_TRANSACTION));
        if (!mIsInJail) return mIssuedBooks.add(book);
        else return false;
    }

    private void updateJailStatus() {
        if (mStatus != 0) mIsInJail = true;
    }

    public boolean returnBook(Book book) {
        mTransactions.add(new Transaction(book.getTitle(), Transaction.RETURN_TRANSACTION));
        return mIssuedBooks.remove(book);
    }

    public void placeHold(Hold hold) {
        mTransactions.add(new Transaction(hold.getBook().getTitle(), Transaction.PLACE_HOLD_TRANSACTION));
        mHolds.add(hold);
    }

    public boolean removeHold(UUID bookId) {
        Iterator iterator = mHolds.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object instanceof Hold) {
                Hold hold = (Hold) object;
                if (hold.getBook().getId().equals(bookId)) {
                    mTransactions.add(new Transaction(hold.getBook().getTitle(), Transaction.REMOVE_HOLD));
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeHold(Hold hold) {
        mTransactions.add(new Transaction(hold.getBook().getTitle(), Transaction.REMOVE_HOLD));
        return mHolds.remove(hold);
    }

    public Iterator getTransactions(Calendar date) {
        List list = new ArrayList();
        Transaction transaction;
        for (int i = 0; i < mTransactions.size(); i++) {
            transaction = mTransactions.get(i);
            if (transaction.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR))
                if (transaction.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR))
                    list.add(transaction);
        }
        return list.iterator();
    }

    public Iterator getIssuedBooks() {
        return mIssuedBooks.iterator();
    }

    public UUID getId() {
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

    public void addTransaction(Transaction transaction) {
        mTransactions.add(transaction);
    }
}

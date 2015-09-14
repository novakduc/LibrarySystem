package Model;

import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by Novak on 9/6/2015.
 */
public class Library {

    public static final int MAX_HOLD_DAYS = 30;
    public static final int MAX_ISSUABLE = 2;
    public static final int MAX_NUMBER_OF_HOLDS = 5;
    public static final int BOOK_REMOVE_OK = 0;
    public static final int BOOK_REMOVE_FAIL_BORROWED = 1;
    public static final int BOOK_REMOVE_FAIL_HOLD = 2;
    public static final int BOOK_REMOVE_FAIL_NOT_EXIST = 3;
    public static final int BOOK_RETURN_OK_NO_HOLD = 0;
    public static final int BOOK_RETURN_FAIL_NOT_ISSUED_YET = 1;
    public static final int BOOK_RETURN_OK_HOLD = 2;
    public static final int BOOK_RETURN_FAIL_NOT_EXIST = 3;
    public static final int BOOK_PLACE_HOLD_FAIL_NOT_EXIST = 3;
    private MemberList mMemberList;
    private Catalog mCatalog;

    public int placeHold(long memberId, long bookId, int duration) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_PLACE_HOLD_FAIL_NOT_EXIST;
        else {
            // TODO: 9/14/2015 ......
        }
    }

    public int returnBook(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_RETURN_FAIL_NOT_EXIST;
        else {
            Member member = book.returnBook();
            if (member == null) return BOOK_RETURN_FAIL_NOT_ISSUED_YET;
            else {
                member.returnBook(book);
                if (book.hasHold()) return BOOK_RETURN_OK_NO_HOLD;
                else return BOOK_RETURN_OK_HOLD;
            }
        }
    }

    public int removeBook(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_REMOVE_FAIL_NOT_EXIST;
        else {
            if (book.hasHold()) return BOOK_REMOVE_FAIL_HOLD;
            else {
                if (book.getBorrowedBy() == null) return BOOK_REMOVE_FAIL_BORROWED;
                else {
                    mCatalog.remove(book);
                    return BOOK_REMOVE_OK;
                }
            }
        }
    }

    public Iterator getTransactions(long memberId, Calendar date) {
        Member member = mMemberList.search(memberId);
        if (member != null)
            return member.getTransactions(date);
        else return null;
    }
}

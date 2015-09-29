package Model;

import android.content.Context;

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
    public static final int BOOK_PLACE_HOLD_FAIL_NOT_ISSUED = 4;
    public static final int REMOVE_HOLD_FAIL_MEMBER_NOT_EXIST = 1;
    public static final int REMOVE_HOLD_FAIL_BOOK_NOT_EXIST = 2;
    public static final int BOOK_PLACE_HOLD_OK = 0;
    public static final int BOOK_REMOVE_HOLD_OK = 0;
    public static final int BOOK_REMOVE_HOLD_FAIL = 3;
    private volatile static Library sUniqueInstance;
    private MemberList mMemberList;
    private Catalog mCatalog;
    private Context mAppContext;

    private Library(Context appContext) {
        mMemberList = MemberList.getInstance();
        mCatalog = Catalog.getInstance();
        mAppContext = appContext;
    }

    public static Library getInstance(Context appContext) {
        if (sUniqueInstance == null) {
            synchronized (Library.class) {
                if (sUniqueInstance == null) {
                    sUniqueInstance = new Library(appContext);
                }
            }
        }
        return sUniqueInstance;
    }

    public int removeHold(long memberId, long bookId) {
        Member member = mMemberList.search(memberId);
        if (member == null) return REMOVE_HOLD_FAIL_MEMBER_NOT_EXIST;
        Book book = mCatalog.search(bookId);
        if (book == null) return REMOVE_HOLD_FAIL_BOOK_NOT_EXIST;
        return (member.removeHold(bookId) && book.removeHold(memberId)) ? BOOK_REMOVE_HOLD_OK : BOOK_REMOVE_HOLD_FAIL;
    }

    public Member addMember(String name, String address, String phone) {
        Member member = new Member(name, address, phone);
        return mMemberList.insert(member) ? member : null;
    }

    public Book addBook(String title, String author) {
        Book book = new Book(title, author);
        return mCatalog.insert(book) ? book : null;
    }

    public int renew(Book book) {
        return book.renewBook();
    }

    public Iterator renewRequest(long memberId) {
        Member member = mMemberList.search(memberId);
        if (member == null) return null;
        return member.getIssuedBooks();
    }

    public Member processHold(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return null;
        Hold hold = book.getNextHold();
        if (hold == null) return null;
        Member member = hold.getMember();
        if (member == null) return null;
        book.removeHold(hold);
        member.removeHold(hold);
        return member;
    }

    public Book issueBook(long bookId, Member member) {
        Book book = mCatalog.search(bookId);
        if (book == null) return null;
        if (book.issue(member) && member.issueBook(book)) return book;
        return null;
    }

    public Member searchMember(long memberId) {
        return mMemberList.search(memberId);
    }

    public int placeHold(long memberId, long bookId, int duration) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_PLACE_HOLD_FAIL_NOT_EXIST;
        Member member = book.getBorrowedBy();
        if (member != null) return BOOK_PLACE_HOLD_FAIL_NOT_ISSUED;
        member = mMemberList.search(memberId);
        Hold hold = new Hold(book, member, duration);
        book.placeHold(hold);
        member.placeHold(hold);
        return BOOK_PLACE_HOLD_OK;
    }

    public int returnBook(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_RETURN_FAIL_NOT_EXIST;
        Member member = book.returnBook();
        if (member == null) return BOOK_RETURN_FAIL_NOT_ISSUED_YET;
        member.returnBook(book);
        if (book.hasHold()) return BOOK_RETURN_OK_NO_HOLD;
        return BOOK_RETURN_OK_HOLD;
    }

    public int removeBook(long bookId) {
        Book book = mCatalog.search(bookId);
        if (book == null) return BOOK_REMOVE_FAIL_NOT_EXIST;
        if (book.hasHold()) return BOOK_REMOVE_FAIL_HOLD;
        if (book.getBorrowedBy() == null) return BOOK_REMOVE_FAIL_BORROWED;
        mCatalog.remove(book);
        return BOOK_REMOVE_OK;
    }

    public Iterator getTransactions(long memberId, Calendar date) {
        Member member = mMemberList.search(memberId);
        if (member != null) return member.getTransactions(date);
        return null;
    }
}

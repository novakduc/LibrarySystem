package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Novak on 9/6/2015.
 */
public class Catalog {
    private volatile static Catalog sUniqueInstance;
    private List mBooks;

    private Catalog() {
        mBooks = new ArrayList();
    }

    public static Catalog getInstance() {
        if (sUniqueInstance == null) {
            synchronized (Catalog.class) {
                if (sUniqueInstance == null) {
                    sUniqueInstance = new Catalog();
                }
            }
        }
        return sUniqueInstance;
    }

    public boolean remove(Book book) {
        return mBooks.remove(book);
    }

    public Book search(long bookId) {
        Iterator iterator = mBooks.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Book) {
                Book book = (Book) o;
                if (book.getId() == bookId)
                    return book;
            }
        }
        return null;
    }

    public boolean insert(Book book) {
        return mBooks.add(book);
    }

    public Iterator getBooksIterator() {
        return mBooks.iterator();
    }

    public List getBooks() {
        return mBooks;
    }
}

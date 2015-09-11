package Control;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Novak on 9/6/2015.
 */
public class Catalog {
    private List mBooks;
    private Catalog mCatalog;

    private Catalog() {
    }

    public Catalog getInstance() {
        if (mCatalog == null)
            return new Catalog();
        else
            return mCatalog;
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

    public Iterator getBooks() {
        return mBooks.iterator();
    }
}

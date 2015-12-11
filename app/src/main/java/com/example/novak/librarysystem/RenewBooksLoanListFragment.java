package com.example.novak.librarysystem;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Book;
import Model.Library;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenewBooksLoanListFragment extends ListFragment {

    public static final String EXTRA_RENEW_BOOKS_LIST_TAG = "com.novak.librarysystem.renewBooksLoanListFragment";
    private List<Book> mIssuedBooks;

    public RenewBooksLoanListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///*
        Bundle args = new Bundle();
        args = getArguments();
        if (args != null) {
            Long memberId = args.getLong(EXTRA_RENEW_BOOKS_LIST_TAG);
            List<Long> issuedBooksId = Library.getInstance(getActivity()).renewRequest(memberId);
            mIssuedBooks = new ArrayList(Library.MAX_ISSUABLE);
            for (Long id : issuedBooksId) {
                mIssuedBooks.add(Library.getInstance(getActivity()).searchBook(id));
            }
            ArrayAdapter adapter = new BookListAdapter(mIssuedBooks);
            setListAdapter(adapter);
        }
        //*/
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("Focus", "reach here item onClick");
        Intent intent = new Intent(getActivity(), LoanBookDetailActivity.class);
        intent.putExtra(LoanBookDetailsFragment.EXTRA_BOOK_ID, mIssuedBooks.get(position).getId());
        startActivity(intent);
    }

    private class BookListAdapter extends ArrayAdapter {

        public BookListAdapter(List books) {
            super(getActivity(), 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.fragment_loanbook_item, null);
            }

            Book book = (Book) getItem(position);
            TextView positionTextView =
                    (TextView) convertView.findViewById(R.id.book_list_item_postion);
            positionTextView.setText((position + 1) + ".");
            TextView bookTitleTextView =
                    (TextView) convertView.findViewById(R.id.book_list_item_titleTextView);
            bookTitleTextView.setText(book.getTitle());
            TextView bookAuthorTextView =
                    (TextView) convertView.findViewById(R.id.book_list_item_authorTextView);
            bookAuthorTextView.setText(book.getAuthor());
            TextView bookDueDateTextView =
                    (TextView) convertView.findViewById(R.id.book_list_item_due_dateTextView);
            if (book.getDueDate() != null) {
                bookDueDateTextView.setText(Utility.dateToString(getActivity(), book.getDueDate()));
            } else {
                bookDueDateTextView.setText(getString(R.string.due_date));
            }
            CheckBox onHoldCheckBox =
                    (CheckBox) convertView.findViewById(R.id.book_list_item_onHoldCheckBox);
            onHoldCheckBox.setEnabled(book.hasHold());
            Log.d("Focus", "reach here getView " + position);
            return convertView;
        }
    }
}

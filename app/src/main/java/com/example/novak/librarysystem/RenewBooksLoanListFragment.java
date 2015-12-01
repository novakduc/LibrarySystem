package com.example.novak.librarysystem;


import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.Book;
import Model.Library;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenewBooksLoanListFragment extends ListFragment {


    public RenewBooksLoanListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///*
        ArrayAdapter adapter = new BookListAdapter(
                Library.getInstance(getActivity()).getCatalog().getBooks());

        setListAdapter(adapter);
        //*/
    }

    private class BookListAdapter extends ArrayAdapter {

        public BookListAdapter(List books) {
            super(getActivity(), 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.fragment_loanbook, null);
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
                Date date = new Date(book.getDueDate().getTimeInMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));

                bookDueDateTextView.setText(dateFormat.format(date));
            } else {
                bookDueDateTextView.setText(getString(R.string.due_date));
            }
            CheckBox onHoldCheckBox =
                    (CheckBox) convertView.findViewById(R.id.book_list_item_onHoldCheckBox);
            onHoldCheckBox.setEnabled(book.hasHold());
            return convertView;
        }
    }
}

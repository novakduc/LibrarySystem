package com.example.novak.librarysystem;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import Model.Book;
import Model.Library;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoanBookDetailsFragment extends Fragment {

    public static final String EXTRA_BOOK_ID =
            "com.example.novak.librarysystem.book.id";
    private Book mBook;

    public LoanBookDetailsFragment() {
        // Required empty public constructor
    }

    public static LoanBookDetailsFragment newInstance(Long bookId) {

        Bundle args = new Bundle();
        args.putLong(EXTRA_BOOK_ID, bookId);

        LoanBookDetailsFragment fragment = new LoanBookDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Long bookId = (Long) getArguments().getSerializable(EXTRA_BOOK_ID);
        if (bookId != null) {
            mBook = Library.getInstance(getActivity()).searchBook(bookId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_book_details, container, false);
        if (mBook == null) {
            return view;
        }

        EditText bookIdEditText = (EditText) view.findViewById(R.id.renew_book_book_id_edit_text);
        bookIdEditText.setText(Long.toString(mBook.getId()));
        EditText bookTittleEditText = (EditText) view.findViewById(R.id.renew_book_book_title_text_view);
        bookTittleEditText.setText(mBook.getTitle());
        EditText bookAuthorEditText = (EditText) view.findViewById(R.id.renew_book_book_author_text_view);
        bookAuthorEditText.setText(mBook.getAuthor());
        final EditText bookDueDateEditText = (EditText) view.findViewById(R.id.renew_book_due_date_text_view);
        if (mBook.getDueDate() != null) {
            bookDueDateEditText.setText(Utility.dateToString(getActivity(), mBook.getDueDate()));
        } else {
            bookDueDateEditText.setText(R.string.book_not_issued);
        }

        Button renewButton = (Button) view.findViewById(R.id.renew_book_renew_button);
        renewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12/11/2015 perform renew
                Library.getInstance(getActivity()).renew(mBook);
                bookDueDateEditText.setText(Utility.dateToString(getActivity(), mBook.getDueDate()));
            }
        });
        return view;
    }

}

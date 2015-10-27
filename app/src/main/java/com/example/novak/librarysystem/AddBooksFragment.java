package com.example.novak.librarysystem;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novak.librarysystem.dialog.AddBookConfirmationDialog;

import model.Book;
import model.Library;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddBooksFragment extends Fragment {

    private static final String DIALOG_CONFIRMATION = "confirm";
    private static final int REQUEST_CONFIRMATION = 0;
    private EditText mAddBookTitleEditText;
    private EditText mAddBookAuthorEditText;
    private Library mLibrary;
    private String mTitle, mAuthor;
    private long mId;

    public AddBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), R.string.add_book_for_other, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLibrary = Library.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_book, container, false);

        mAddBookTitleEditText = (EditText) v.findViewById(R.id.add_book_title_input);
        mAddBookAuthorEditText = (EditText) v.findViewById(R.id.add_book_author_input);
        Button addBookButton = (Button) v.findViewById(R.id.add_book_button);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitle = mAddBookTitleEditText.getText().toString();
                if (mTitle.length() == 0) {
                    Toast.makeText(getActivity(), R.string.add_book_null_title_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuthor = mAddBookAuthorEditText.getText().toString();
                if (mAuthor.length() == 0) {
                    Toast.makeText(getActivity(), R.string.add_book_null_author_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                Book book = mLibrary.addBook(mTitle, mAuthor);
                if (book == null) {
                    Toast.makeText(getActivity(), R.string.add_book_error, Toast.LENGTH_SHORT).show();
                } else {
                    mId = book.getId();

                    FragmentManager fragmentManager = getFragmentManager();
                    AddBookConfirmationDialog dialog = AddBookConfirmationDialog.newInstance(mTitle, mAuthor, mId);
                    dialog.setTargetFragment(AddBooksFragment.this, REQUEST_CONFIRMATION);
                    dialog.show(fragmentManager, DIALOG_CONFIRMATION);
                }
            }
        });

        return v;
    }


}

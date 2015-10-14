package com.example.novak.librarysystem;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.Library;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReturnBooksFragment extends Fragment {

    private Library mLibrary;
    private long mBookId;
    private EditText mBookIdEditText;
    private Button mReturnBookButton;

    public ReturnBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLibrary = Library.getInstance(getActivity());
        View view = inflater.inflate(R.layout.fragment_return_books, container, false);
        mBookIdEditText = (EditText) view.findViewById(R.id.return_book_book_id_edit_text);
        mReturnBookButton = (Button) view.findViewById(R.id.return_book_return_button);

        mReturnBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mBookIdEditText.getText().toString();
                try {
                    mBookId = Long.parseLong(s);
                    int result = mLibrary.returnBook(mBookId);
                    switch (result) {
                        case Library.BOOK_RETURN_OK_NO_HOLD:
                            Toast.makeText(getActivity(), R.string.return_book_ok_no_hold, Toast.LENGTH_SHORT).show();
                            break;
                        case Library.BOOK_RETURN_OK_HOLD:
                            Toast.makeText(getActivity(), R.string.return_book_ok_has_hold, Toast.LENGTH_SHORT).show();
                            break;
                        case Library.BOOK_RETURN_FAIL_NOT_EXIST:
                            Toast.makeText(getActivity(), R.string.return_book_fail_not_exist, Toast.LENGTH_SHORT).show();
                            break;
                        case Library.BOOK_RETURN_FAIL_NOT_ISSUED_YET:
                            Toast.makeText(getActivity(), R.string.return_book_fail_not_issued_yet, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), R.string.return_book_invalid_book_id, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


}

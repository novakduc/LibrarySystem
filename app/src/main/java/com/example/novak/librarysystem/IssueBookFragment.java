package com.example.novak.librarysystem;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.Book;
import Model.Library;
import Model.Member;

/**
 * A simple {@link Fragment} subclass.
 */
public class IssueBookFragment extends Fragment {

    private EditText mMemberIdEditText, mMemberNameEditText, mMemberAddressEditText,
            mMemberPhoneEditText, mBookIdEditText, mBookTitleEditText, mBookAuthorEditText;
    private Button mMemberSearchButton, mBookSearchButton, mIssueButton;

    private Book mBook;
    private Member mMember;
    private Library mLibrary;

    public IssueBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLibrary = Library.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_book, container, false);

        mMemberIdEditText = (EditText) view.findViewById(R.id.issue_book_member_id_edit_text);
        mMemberNameEditText = (EditText) view.findViewById(R.id.issue_book_member_name_text_view);
        mMemberAddressEditText = (EditText) view.findViewById(R.id.issue_book_member_address_text_view);
        mMemberPhoneEditText = (EditText) view.findViewById(R.id.issue_book_member_phone_text_view);

        mBookTitleEditText = (EditText) view.findViewById(R.id.issue_book_book_title_text_view);
        mBookIdEditText = (EditText) view.findViewById(R.id.issue_book_book_id_edit_text);
        mBookAuthorEditText = (EditText) view.findViewById(R.id.issue_book_book_author_text_view);

        mMemberSearchButton = (Button) view.findViewById(R.id.issue_book_member_search_button);
        mBookSearchButton = (Button) view.findViewById(R.id.issue_book_book_search_button);
        mIssueButton = (Button) view.findViewById(R.id.issue_book_issue_button);

        mIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMember == null) {
                    Toast.makeText(getActivity(), R.string.member_not_exist, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mBook == null) {
                    Toast.makeText(getActivity(), R.string.book_not_exist, Toast.LENGTH_SHORT).show();
                    return;
                }

                int issueBookResult = mLibrary.issueBook(mBook, mMember);
                switch (issueBookResult) {
                    case Library.ISSUE_BOOK_OK:
                        Toast.makeText(getActivity(), R.string.issue_book_successfully,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Library.ISSUE_BOOK_FAIL_BOOK_NOT_AVAILABLE:
                        Toast.makeText(getActivity(), R.string.issue_book_unsuccessfully_book_not_available,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Library.ISSUE_BOOK_FAIL_BOOK_MEMBER_ISSUE_LIMIT:
                        Toast.makeText(getActivity(), R.string.issue_book_unsuccessfully_member_in_jail, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), R.string.issue_book_unsuccessfully, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBookSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mBookIdEditText.getText().toString();

                try {
                    long id = Long.parseLong(s);
                    mBook = mLibrary.searchBook(id);
                    if (mBook == null) {
                        Toast.makeText(getActivity(), R.string.book_not_exist, Toast.LENGTH_SHORT).show();
                        mBookTitleEditText.setText("");
                        mBookTitleEditText.setHint(R.string.book_not_exist);
                        mBookAuthorEditText.setText("");
                        mBookAuthorEditText.setHint(R.string.book_not_exist);
                        return;
                    }

                    mBookTitleEditText.setText(mBook.getTitle());
                    mBookAuthorEditText.setText(mBook.getAuthor());
                } catch (NumberFormatException ex) {
                    Toast.makeText(getActivity(), R.string.issue_book_invalid_book_id, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMemberSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mMemberIdEditText.getText().toString();

                try {
                    long id = Long.parseLong(s);
                    mMember = mLibrary.searchMember(id);
                    if (mMember == null) {
                        Toast.makeText(getActivity(), R.string.member_not_exist, Toast.LENGTH_SHORT).show();
                        mMemberNameEditText.setText("");
                        mMemberNameEditText.setHint(R.string.member_not_exist);
                        mMemberAddressEditText.setText("");
                        mMemberAddressEditText.setHint(R.string.member_not_exist);
                        mMemberPhoneEditText.setText("");
                        mMemberPhoneEditText.setHint(R.string.member_not_exist);
                        return;
                    }
                    mMemberNameEditText.setText(mMember.getName());
                    mMemberAddressEditText.setText(mMember.getAddress());
                    mMemberPhoneEditText.setText(mMember.getPhone());
                } catch (NumberFormatException ex) {
                    Toast.makeText(getActivity(), R.string.issue_book_invalid_member_id, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


}

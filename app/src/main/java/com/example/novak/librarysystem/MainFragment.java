package com.example.novak.librarysystem;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {


    private Button mAddMemberButton;
    private Button mAddBookButton;
    private Button mIssueBooksButton;
    private Button mReturnBooksButton;
    private Button mRenewBooksButton;
    private Button mRemoveBooksButton;
    private Button mPlaceHoldButton;
    private Button mRemoveHoldButton;
    private Button mProcessHoldButton;
    private Button mPrintTransactionsButton;
    private Button mSaveDataButton;
    private Button mRetrieveDataButton;
    private Button mHelpButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        //Attach with views
        mAddMemberButton = (Button) v.findViewById(R.id.add_member_button);
        mAddBookButton = (Button) v.findViewById(R.id.add_book_button);
        mIssueBooksButton = (Button) v.findViewById(R.id.issue_book_button);
        mReturnBooksButton = (Button) v.findViewById(R.id.return_book_button);
        mRenewBooksButton = (Button) v.findViewById(R.id.renew_books_button);
        mRemoveBooksButton = (Button) v.findViewById(R.id.remove_books_button);
        mPlaceHoldButton = (Button) v.findViewById(R.id.place_hold_button);
        mRemoveHoldButton = (Button) v.findViewById(R.id.remove_hold_button);
        mProcessHoldButton = (Button) v.findViewById(R.id.process_holds_button);
        mPrintTransactionsButton = (Button) v.findViewById(R.id.print_transactions_button);
        mSaveDataButton = (Button) v.findViewById(R.id.save_data_button);
        mRetrieveDataButton = (Button) v.findViewById(R.id.retrieve_data_button);
        mHelpButton = (Button) v.findViewById(R.id.help_button);

        //Set listener
        mAddMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMemberActivity.class);
                startActivity(intent);
            }
        });

        mAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddBooksActivity.class);
                startActivity(intent);
            }
        });

        mIssueBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IssueBookActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}

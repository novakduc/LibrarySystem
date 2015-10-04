package com.example.novak.librarysystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class IssueBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        IssueBookFragment issueBookFragment =
                (IssueBookFragment) fragmentManager.findFragmentById(R.id.issue_book_fragment_container);
        if (issueBookFragment == null) {
            issueBookFragment = new IssueBookFragment();
            fragmentTransaction.add(R.id.issue_book_fragment_container, issueBookFragment);
            fragmentTransaction.commit();
        }
    }
}

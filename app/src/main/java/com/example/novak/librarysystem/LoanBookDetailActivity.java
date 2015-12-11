package com.example.novak.librarysystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LoanBookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_book_detail);

        if (findViewById(R.id.loan_book_details_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.loan_book_details_fragment_container, new LoanBookDetailsFragment());
            transaction.commit();
        }
    }
}

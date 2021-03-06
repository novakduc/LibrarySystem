package com.example.novak.librarysystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class RenewBooksActivity extends AppCompatActivity implements
        RenewBooksMemberIdFragment.OnSearchButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_book);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentById(R.id.renew_books_member_id_fragment_container) == null) {
            transaction.add(R.id.renew_books_member_id_fragment_container, new RenewBooksMemberIdFragment());
        }
        /*
        if (fragmentManager.findFragmentById(R.id.renew_book_loan_list_fragment_container) == null) {
            transaction.add(R.id.renew_book_loan_list_fragment_container, new RenewBooksLoanListFragment());
        }
        */

        transaction.commit();
    }

    @Override
    public void changeFocus() {
        Log.d("Listener", "changeFocus launched");
    }
}

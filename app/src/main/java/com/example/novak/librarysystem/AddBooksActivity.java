package com.example.novak.librarysystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddBooksFragment addBooksFragment =
                (AddBooksFragment) fragmentManager.findFragmentById(R.id.add_book_fragment_container);
        if (addBooksFragment == null) addBooksFragment = new AddBooksFragment();
        fragmentTransaction.replace(R.id.add_book_fragment_container, addBooksFragment);
        fragmentTransaction.commit();
    }
}

package com.example.novak.librarysystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class RemoveBookActivity extends AppCompatActivity
        implements RemoveBookFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_book);

        if (findViewById(R.id.remove_books_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            RemoveBookFragment fragment = RemoveBookFragment.newInstance(null, null);

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.remove_books_fragment_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("Fragment", "Launched onFragmentInteraction in RemoveBookActivity");
        return;
    }
}

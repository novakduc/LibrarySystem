package com.example.novak.librarysystem;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Novak on 9/19/2015.
 */
public class AddMemberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        AddMemberFragment addMemberFragment = new AddMemberFragment();
        transaction.add(R.id.add_member_fragment_container, addMemberFragment);
        transaction.commit();
    }
}

package com.example.novak.librarysystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Attach with views
        mAddMemberButton = (Button) findViewById(R.id.add_member_button);
        mAddBookButton = (Button) findViewById(R.id.add_book_button);
        mIssueBooksButton = (Button) findViewById(R.id.issue_book_button);
        mReturnBooksButton = (Button) findViewById(R.id.return_book_button);
        mRenewBooksButton = (Button) findViewById(R.id.renew_books_button);
        mRemoveBooksButton = (Button) findViewById(R.id.remove_books_button);
        mPlaceHoldButton = (Button) findViewById(R.id.place_hold_button);
        mRemoveHoldButton = (Button) findViewById(R.id.remove_hold_button);
        mProcessHoldButton = (Button) findViewById(R.id.process_holds_button);
        mPrintTransactionsButton = (Button) findViewById(R.id.print_transactions_button);
        mSaveDataButton = (Button) findViewById(R.id.save_data_button);
        mRetrieveDataButton = (Button) findViewById(R.id.retrieve_data_button);
        mHelpButton = (Button) findViewById(R.id.help_button);

        //Set listener
        mAddMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 9/18/2015
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

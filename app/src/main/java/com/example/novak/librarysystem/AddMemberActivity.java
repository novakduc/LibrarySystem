package com.example.novak.librarysystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Model.Library;
import Model.Member;

/**
 * Created by Novak on 9/19/2015.
 */
public class AddMemberActivity extends Activity {

    private Button mAddMemberRegisterButton;
    private TextView mAddMemberNameInputTextView;
    private TextView mAddMemberAddressInputTextView;
    private TextView mAddMemberPhoneInputTextView;
    private Library mLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mLibrary = new Library();

        mAddMemberAddressInputTextView = (TextView) findViewById(R.id.add_member_name_input);
        mAddMemberAddressInputTextView = (TextView) findViewById(R.id.add_member_address_input);
        mAddMemberPhoneInputTextView = (TextView) findViewById(R.id.add_member_phone_input);
        mAddMemberRegisterButton = (Button) findViewById(R.id.add_member_register_button);

        mAddMemberRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mAddMemberNameInputTextView.getText().toString();
                if (name == null) {
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_null_name_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                String address = mAddMemberAddressInputTextView.getText().toString();
                if (address == null) {
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_null_address_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                String phone = mAddMemberPhoneInputTextView.getText().toString();
                if (phone == null) {
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_null_phone_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                Member member = mLibrary.addMember(mAddMemberNameInputTextView.getText().toString(),
                        mAddMemberAddressInputTextView.getText().toString(), mAddMemberPhoneInputTextView.getText().toString());

                if (member == null)
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_unsuccessful_notice, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

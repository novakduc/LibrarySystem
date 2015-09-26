package com.example.novak.librarysystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.Library;
import Model.Member;

/**
 * Created by Novak on 9/19/2015.
 */
public class AddMemberActivity extends Activity {

    private Button mAddMemberRegisterButton;
    private EditText mAddMemberNameEditText;
    private EditText mAddMemberAddressEditText;
    private EditText mAddMemberPhoneEditText;
    private Library mLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mLibrary = Library.getInstance();

        mAddMemberNameEditText = (EditText) findViewById(R.id.add_member_name_input);
        mAddMemberAddressEditText = (EditText) findViewById(R.id.add_member_address_input);
        mAddMemberPhoneEditText = (EditText) findViewById(R.id.add_member_phone_input);
        mAddMemberRegisterButton = (Button) findViewById(R.id.add_member_register_button);

        mAddMemberRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mAddMemberNameEditText.getText().toString();

                if (name.length() == 0) {
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_null_name_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                String address = mAddMemberAddressEditText.getText().toString();
                if (address.length() == 0) {
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_null_address_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                String phone = mAddMemberPhoneEditText.getText().toString();
                if (phone.length() == 0) {
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_null_phone_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                ///Create confirm dialog


                Member member = mLibrary.addMember(name, address, phone);

                if (member == null)
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_unsuccessful_notice, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddMemberActivity.this, R.string.add_member_success, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

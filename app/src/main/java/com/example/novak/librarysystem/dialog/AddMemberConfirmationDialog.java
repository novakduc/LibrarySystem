package com.example.novak.librarysystem.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.novak.librarysystem.R;

/**
 * Created by Novak on 9/26/2015.
 */
public class AddMemberConfirmationDialog extends DialogFragment {

    private String mName, mAddress, mPhone;

    public AddMemberConfirmationDialog(String name, String address, String phone) {
        // TODO: 9/27/2015 Modify to setArguments. Not to use other Constructor than default in fragment
        mName = name;
        mAddress = address;
        mPhone = phone;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_add_member_confirmation, null);

        TextView nameTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_name),
                addressTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_address),
                phoneTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_phone);

        String nameShow = nameTextView.getText().toString() + mName;
        nameTextView.setText(nameShow);

        String addressShow = addressTextView.getText().toString() + mAddress;
        addressTextView.setText(addressShow);

        String phoneShow = phoneTextView.getText().toString() + mPhone;
        phoneTextView.setText(phoneShow);

        builder.setView(v);
        builder.setPositiveButton(R.string.add_member_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///add the member
            }
        });

        builder.setNegativeButton(R.string.add_member_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///not add the member
            }
        });

        return builder.create();
    }
}

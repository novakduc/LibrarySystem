package com.example.novak.librarysystem.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.novak.librarysystem.R;

/**
 * Created by Novak on 9/26/2015.
 */
public class AddMemberConfirmationDialog extends DialogFragment {

    public static final String EXTRA_MEMBER_NAME = "com.example.novak.librarysystem.dialog.member.name";
    public static final String EXTRA_MEMBER_ADDRESS = "com.example.novak.librarysystem.dialog.member.address";
    public static final String EXTRA_MEMBER_PHONE = "com.example.novak.librarysystem.dialog.member.phone";
    public static final String EXTRA_MEMBER_ID = "com.example.novak.librarysystem.dialog.member.id";
    private String mName, mAddress, mPhone;
    private long mId;

    public static AddMemberConfirmationDialog newInstance(String name, String address, String phone, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MEMBER_NAME, name);
        bundle.putSerializable(EXTRA_MEMBER_ADDRESS, address);
        bundle.putSerializable(EXTRA_MEMBER_PHONE, phone);
        bundle.putSerializable(EXTRA_MEMBER_ID, id);

        AddMemberConfirmationDialog addMemberConfirmationDialog = new AddMemberConfirmationDialog();
        addMemberConfirmationDialog.setArguments(bundle);
        return addMemberConfirmationDialog;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) return;

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mName = (String) getArguments().getSerializable(EXTRA_MEMBER_NAME);
        mAddress = (String) getArguments().getSerializable(EXTRA_MEMBER_ADDRESS);
        mPhone = (String) getArguments().getSerializable(EXTRA_MEMBER_PHONE);
        mId = (long) getArguments().getSerializable(EXTRA_MEMBER_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_add_member_confirmation, null);

        TextView nameTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_name),
                addressTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_address),
                phoneTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_phone),
                idTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_id);

        String nameShow = nameTextView.getText().toString() + " " + mName;
        nameTextView.setText(nameShow);

        String addressShow = addressTextView.getText().toString() + " " + mAddress;
        addressTextView.setText(addressShow);

        String phoneShow = phoneTextView.getText().toString() + " " + mPhone;
        phoneTextView.setText(phoneShow);

        String idShow = idTextView.getText().toString() + " " + mId;
        idTextView.setText(idShow);

        builder.setView(v);
        builder.setPositiveButton(R.string.add_member_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
            }
        });

        builder.setNegativeButton(R.string.add_member_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///not add the member
                sendResult(Activity.RESULT_CANCELED);
            }
        });

        return builder.create();
    }
}

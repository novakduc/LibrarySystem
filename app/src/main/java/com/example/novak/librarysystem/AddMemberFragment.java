package com.example.novak.librarysystem;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novak.librarysystem.dialog.AddMemberConfirmationDialog;

import Model.Library;
import Model.Member;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMemberFragment extends Fragment {

    private static final String DIALOG_CONFIRMATION = "confirm";
    private static final String KEY_ADD_NAME_INPUT = "ADD_NAME_INPUT";
    private static final String KEY_ADD_ADDRESS_INPUT = "ADD_ADDRESS_INPUT";
    private static final String KEY_ADD_PHONE_INPUT = "ADD_PHONE_INPUT";
    private static final int REQUEST_CONFIRMATION = 0;
    private EditText mAddMemberNameEditText;
    private EditText mAddMemberAddressEditText;
    private EditText mAddMemberPhoneEditText;
    private Library mLibrary;
    private String mName, mAddress, mPhone;
    private long mId;

    public AddMemberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity().finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLibrary = Library.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_member, container, false);

        mAddMemberNameEditText = (EditText) v.findViewById(R.id.add_member_name_input);
        mAddMemberAddressEditText = (EditText) v.findViewById(R.id.add_member_address_input);
        mAddMemberPhoneEditText = (EditText) v.findViewById(R.id.add_member_phone_input);
        Button addMemberRegisterButton = (Button) v.findViewById(R.id.add_member_register_button);

        addMemberRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mAddMemberNameEditText.getText().toString();
                if (mName.length() == 0) {
                    Toast.makeText(getActivity(), R.string.add_member_null_name_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                mAddress = mAddMemberAddressEditText.getText().toString();
                if (mAddress.length() == 0) {
                    Toast.makeText(getActivity(), R.string.add_member_null_address_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                mPhone = mAddMemberPhoneEditText.getText().toString();
                if (mPhone.length() == 0) {
                    Toast.makeText(getActivity(), R.string.add_member_null_phone_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Member member = mLibrary.addMember(mName, mAddress, mPhone);
                    if (member == null)
                        Toast.makeText(getActivity(), R.string.add_member_unsuccessful_notice, Toast.LENGTH_SHORT).show();
                    else {
                        ///Create confirm dialog
                        mId = member.getId();
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        AddMemberConfirmationDialog addMemberConfirmationDialog
                                = AddMemberConfirmationDialog.newInstance(mName, mAddress, mPhone, mId);
                        addMemberConfirmationDialog.setTargetFragment(AddMemberFragment.this, REQUEST_CONFIRMATION);
                        addMemberConfirmationDialog.show(fragmentManager, DIALOG_CONFIRMATION);
                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), R.string.add_member_unsuccessful_notice_member_existed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }


}

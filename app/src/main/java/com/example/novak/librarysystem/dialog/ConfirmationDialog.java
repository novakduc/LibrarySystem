package com.example.novak.librarysystem.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.novak.librarysystem.R;

/**
 * Created by Novak on 9/26/2015.
 */
public class ConfirmationDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_member_confirmation);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_add_member_confirmation, null);

        TextView nameTextView = (TextView) v.findViewById(R.id.dialog_add_member_confirmation_name);

        return super.onCreateDialog(savedInstanceState);
    }
}

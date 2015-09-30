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
public class AddBookConfirmationDialog extends DialogFragment {

    public static final String EXTRA_BOOK_TITLE = "com.example.novak.librarysystem.dialog.book.title";
    public static final String EXTRA_BOOK_AUTHOR = "com.example.novak.librarysystem.dialog.book.author";
    public static final String EXTRA_BOOK_ID = "com.example.novak.librarysystem.dialog.book.id";
    private String mTitle, mAuthor;
    private long mId;

    public static AddBookConfirmationDialog newInstance(String title, String author, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_BOOK_TITLE, title);
        bundle.putSerializable(EXTRA_BOOK_AUTHOR, author);
        bundle.putSerializable(EXTRA_BOOK_ID, id);

        AddBookConfirmationDialog addMemberConfirmationDialog = new AddBookConfirmationDialog();
        addMemberConfirmationDialog.setArguments(bundle);
        return addMemberConfirmationDialog;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) return;

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mTitle = (String) getArguments().getSerializable(EXTRA_BOOK_TITLE);
        mAuthor = (String) getArguments().getSerializable(EXTRA_BOOK_AUTHOR);
        mId = (long) getArguments().getSerializable(EXTRA_BOOK_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_add_book_confirmation, null);

        TextView titleTextView = (TextView) v.findViewById(R.id.dialog_add_book_confirmation_title),
                authorTextView = (TextView) v.findViewById(R.id.dialog_add_book_confirmation_author),
                idTextView = (TextView) v.findViewById(R.id.dialog_add_book_confirmation_id);

        String nameShow = titleTextView.getText().toString() + " " + mTitle;
        titleTextView.setText(nameShow);

        String authorShow = authorTextView.getText().toString() + " " + mAuthor;
        authorTextView.setText(authorShow);

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

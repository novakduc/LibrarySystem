package com.example.novak.librarysystem;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoanBookDetailsFragment extends Fragment {


    public LoanBookDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_book_details, container, false);
        EditText bookIdEditText = (EditText) view.findViewById(R.id.renew_book_book_id_edit_text);
        EditText bookTittleEditText = (EditText) view.findViewById(R.id.renew_book_book_title_text_view);
        EditText bookAuthorEditText = (EditText) view.findViewById(R.id.renew_book_book_author_text_view);
        EditText bookDueDateEditText = (EditText) view.findViewById(R.id.renew_book_due_date_text_view);

        Button renewButton = (Button) view.findViewById(R.id.renew_book_renew_button);
        renewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12/11/2015 perform renew
            }
        });
        return view;
    }

}

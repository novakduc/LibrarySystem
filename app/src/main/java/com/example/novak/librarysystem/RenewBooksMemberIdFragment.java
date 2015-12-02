package com.example.novak.librarysystem;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.Library;
import Model.Member;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenewBooksMemberIdFragment extends Fragment {


    public RenewBooksMemberIdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_renew_books_member_id, container, false);

        Button searchButton = (Button) view.findViewById(R.id.renew_book_member_search_button);
        final EditText memberNameEditText =
                (EditText) view.findViewById(R.id.renew_book_member_name_text_view);
        final EditText memberAddressEditText =
                (EditText) view.findViewById(R.id.renew_book_member_address_text_view);
        final EditText memberPhoneEditText =
                (EditText) view.findViewById(R.id.renew_book_member_phone_text_view);
        final EditText memberIdEditText =
                (EditText) view.findViewById(R.id.renew_book_member_id_edit_text);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Library library = Library.getInstance(getActivity());
                String s = memberIdEditText.getText().toString();
                try {
                    Long id = Long.parseLong(s);
                    Member member = library.searchMember(id);
                    if (member != null) {
                        memberNameEditText.setText(member.getName());
                        memberAddressEditText.setText(member.getAddress());
                        memberPhoneEditText.setText(member.getPhone());
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), R.string.renew_book_member_id_invalid, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}

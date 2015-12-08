package com.example.novak.librarysystem;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Library.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_renew_books_member_id, container, false);

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
                    if (member == null) {
                        Toast.makeText(getActivity(), R.string.member_not_exist, Toast.LENGTH_SHORT).show();
                        memberNameEditText.setText("");
                        memberNameEditText.setHint(R.string.member_not_exist);
                        memberAddressEditText.setText("");
                        memberAddressEditText.setHint(R.string.member_not_exist);
                        memberPhoneEditText.setText("");
                        memberPhoneEditText.setHint(R.string.member_not_exist);
                        return;
                    }
                    memberNameEditText.setText(member.getName());
                    memberAddressEditText.setText(member.getAddress());
                    memberPhoneEditText.setText(member.getPhone());

                    FragmentManager manager = getActivity().getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    RenewBooksLoanListFragment bookLoanListFragment =
                            new RenewBooksLoanListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putLong(RenewBooksLoanListFragment.EXTRA_RENEW_BOOKS_LIST_TAG, id);
                    bookLoanListFragment.setArguments(bundle);
                    transaction.replace(R.id.renew_book_loan_list_fragment_container,
                            bookLoanListFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    memberIdEditText.clearFocus();

                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), R.string.renew_book_member_id_invalid, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}

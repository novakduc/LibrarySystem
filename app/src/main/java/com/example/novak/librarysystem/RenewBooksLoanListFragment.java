package com.example.novak.librarysystem;


import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import Model.Catalog;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenewBooksLoanListFragment extends ListFragment {


    public RenewBooksLoanListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///*
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                Catalog.getInstance(getActivity().getApplicationContext()).getBooks());

        setListAdapter(adapter);
        //*/
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_loanbook, container, false);
    }
*/
}

package com.example.novak.librarysystem;


import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import Model.Book;
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

    private class BookListAdapter extends ArrayAdapter {

        public BookListAdapter(List books) {
            super(getActivity(), 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.fragment_loanbook, null);
            }

            Book book = (Book) getItem(position);
            return super.getView(position, convertView, parent);
        }
    }
}

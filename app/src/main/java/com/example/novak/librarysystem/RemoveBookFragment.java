package com.example.novak.librarysystem;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import Model.Book;
import Model.Library;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RemoveBookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RemoveBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemoveBookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Book mBook;

    private OnFragmentInteractionListener mListener;

    public RemoveBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RemoveBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RemoveBookFragment newInstance(String param1, String param2) {
        RemoveBookFragment fragment = new RemoveBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.d("Fragment", "Created fragment instance");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Library.getInstance(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d("Fragment", "Fragment onCreate launched");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Fragment", "Fragment onCreateView launched");
        View view = inflater.inflate(R.layout.fragment_remove_book, container, false);

        final EditText bookIdEditText = (EditText) view.findViewById(R.id.remove_book_book_id_edit_text);
        final EditText bookTitleEditText = (EditText) view.findViewById(R.id.remove_book_book_title_text_view);
        final EditText bookAuthorEditText = (EditText) view.findViewById(R.id.remove_book_book_author_text_view);
        final EditText bookDueDateEditText = (EditText) view.findViewById(R.id.remove_book_due_date_text_view);
        final CheckBox onHoldCheckBox = (CheckBox) view.findViewById(R.id.on_hold_check_box);
        final Button removeButton = (Button) view.findViewById(R.id.remove_book_remove_button);
        removeButton.setFocusable(false);
        Button searchButton = (Button) view.findViewById(R.id.remove_book_search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long bookId = Long.parseLong(bookIdEditText.getText().toString());
                    mBook = Library.getInstance(getActivity()).searchBook(bookId);
                    if (mBook != null) {
                        bookTitleEditText.setText(mBook.getTitle());
                        bookAuthorEditText.setText(mBook.getAuthor());
                        if (mBook.getDueDate() == null) {
                            bookDueDateEditText.setText(R.string.book_not_issued);
                        } else {
                            bookDueDateEditText.setText(
                                    Utility.dateToString(getActivity(), mBook.getDueDate()));
                        }
                    }
                    if (mBook.hasHold()) {
                        onHoldCheckBox.setSelected(true);
                    } else {
                        onHoldCheckBox.setSelected(false);
                    }

                    removeButton.setFocusable(true);
                } catch (NumberFormatException ex) {
                    Toast.makeText(getActivity(), R.string.issue_book_invalid_book_id, Toast.LENGTH_SHORT).show();
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int removeResult = Library.getInstance(getActivity()).removeBook(mBook.getId());
                switch (removeResult) {
                    case Library.BOOK_REMOVE_FAIL_NOT_EXIST:
                        Toast.makeText(getActivity(), R.string.remove_book_failed_not_exist,
                                Toast.LENGTH_SHORT).show();
                        return;
                    case Library.BOOK_REMOVE_FAIL_BORROWED:
                        Toast.makeText(getActivity(), R.string.remove_book_failed_borrowed,
                                Toast.LENGTH_SHORT).show();
                        return;
                    case Library.BOOK_REMOVE_FAIL_HOLD:
                        Toast.makeText(getActivity(), R.string.book_remove_failed_on_hold,
                                Toast.LENGTH_SHORT).show();
                        return;
                    case Library.BOOK_REMOVE_OK:
                        Toast.makeText(getActivity(), R.string.book_remove_ok,
                                Toast.LENGTH_SHORT).show();
                        bookIdEditText.setText("");
                        bookTitleEditText.setText("");
                        bookAuthorEditText.setText("");
                        bookDueDateEditText.setText("");
                        onHoldCheckBox.setSelected(false);
                        return;
                    default:
                        return;
                    // TODO: 12/12/2015
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        Log.d("Fragment", "Fragment onAttach launched");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d("Fragment", "Fragment onDetach lauched");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

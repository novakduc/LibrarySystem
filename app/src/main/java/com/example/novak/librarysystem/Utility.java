package com.example.novak.librarysystem;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by n.thanh on 12/11/2015.
 */
public class Utility {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}

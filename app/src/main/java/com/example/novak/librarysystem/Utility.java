package com.example.novak.librarysystem;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by n.thanh on 12/11/2015.
 */
public class Utility {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String dateToString(Activity activity, Calendar calendar) {
        Date date = new Date(calendar.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(activity.getString(R.string.date_format));

        return dateFormat.format(date);
    }
}

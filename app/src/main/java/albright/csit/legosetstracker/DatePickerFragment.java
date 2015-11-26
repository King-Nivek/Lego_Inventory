/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  10.31.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.02.2015

   Assignment:  PizzaOrderAppFragments
    File Name:  DatePickerFragment.java
      Purpose:  A fragment to display a date picker dialog.  That allows for
                  max date to be set to limit how far out the picker can go.
                  And setting the min date so past dates can not be selected.
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    ////  Fields
    /////////////////////////////
    private OnDatePickedListener _callback;
    private DatePickerDialog dpDialog;
    private GregorianCalendar date;

    ////  Interface
    /////////////////////////////
    public interface OnDatePickedListener {
        public void onDatePicked(int selectedYear, int selectedMonth, int selectedDay);
    }

    //  onCreateDialog Function
    //
    //  Use:  Creates the dialog.
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Dialog
    //
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        dpDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        return dpDialog;
    }

    //  onAttach Function
    //
    //  Use:  Attaches the dialog to the activity.  While making sure that the
    //          _callback function has been implemented in the activity class.
    //  Parameter(s):  Activity:activity
    //  Returns:  none
    //
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Fragment fragContext = getFragmentManager().findFragmentById(R.id.legoset_detail_container);
            _callback = (OnDatePickedListener) fragContext;
        } catch (ClassCastException e) {
            Log.d("DatePickerFragment---->", context.toString() + "must Implement");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _callback = (OnDatePickedListener) activity.getFragmentManager().findFragmentByTag("detailFragment");
        } catch (ClassCastException e) {
            Log.d("DatePickerFragment---->", activity.toString() + "must Implement");
        }
    }

    //  onDateSet Function
    //
    //  Use:  Where the implemented callback function will be placed.
    //  Parameter(s):  DatePicker:view, int:year, int:monthOfYear, int:dayOfMonth
    //  Returns:  none
    //
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _callback.onDatePicked(year, monthOfYear, dayOfMonth);
    }
}

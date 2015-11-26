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
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class NumberPickerFragment extends DialogFragment{

    ////  Fields
    /////////////////////////////
    private OnNumberPickedListener _callback;
    private DatePickerDialog dpDialog;

    ////  Interface
    /////////////////////////////
    public interface OnNumberPickedListener {
        public void onNumberPicked(int number);
    }

    //  onCreateDialog Function
    //
    //  Use:  Creates the dialog.
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Dialog
    //
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final NumberPicker np = new NumberPicker(getActivity());
        np.setMaxValue(200);
        np.setMinValue(0);
        np.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        np.setWrapSelectorWheel(true);
        builder.setTitle(R.string.update_hint_quantityOwned)
            .setView(np)
            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onNumberSet(np.getValue());
                }
            });

        return builder.create();
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
            _callback = (OnNumberPickedListener) fragContext;
        } catch (ClassCastException e) {
            Log.d("DatePickerFragment---->", context.toString() + "must Implement");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _callback = (OnNumberPickedListener) activity.getFragmentManager().findFragmentByTag("detailFragment");
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
    public void onNumberSet(int number) {
        _callback.onNumberPicked(number);
    }
}

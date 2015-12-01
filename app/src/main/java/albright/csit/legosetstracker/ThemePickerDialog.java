/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.29.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  ThemePickerDialog.java
      Purpose:  Creates a dialog window that will allow the user to pick a
                  theme.  Or the user can click a button to add a new theme.
                  Clicks are implemented through other classes implementing
                  the interface in this class.
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class ThemePickerDialog extends DialogFragment{
    ////  Fields
    ////////////////////////////////////////
    private SpinnerThemeAdapter adapter;
    private Callbacks _callbacks;

    ////  Setters
    ////////////////////////////////////////
    public void setAdapter(SpinnerThemeAdapter adapter){
        this.adapter = adapter;
    }

    ////  Interface
    ////////////////////////////////////////
    interface Callbacks{
        public void onAdapterClick(int which);
        public void onBttnAddTheme();
    }

    //  onCreateDialog Function
    //
    //  Use:  Creates the Dialog and returns this dialog.
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Dialog
    //
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder_themeDialog = new AlertDialog.Builder(getActivity());
        builder_themeDialog.setTitle(R.string.update_dialogTheme_title)
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    _callbacks.onAdapterClick(which);
                }
            })
            .setNeutralButton(R.string.update_dialogTheme_neutralText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    _callbacks.onBttnAddTheme();
                }
            })
            .setNegativeButton(R.string.update_dialogAddTheme_negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        return builder_themeDialog.create();
    }

    //  onAttach Functions
    //
    //  Use:  Attaches the dialog to the activity.  While making sure that the
    //          _callbacks function has been implemented in the activity class.
    //  Parameter(s):  Activity:activity OR if on API 23 Context:context
    //  Returns:  Void
    //
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Fragment fragContext = getFragmentManager().findFragmentById(R.id.lego_detail_container);
            _callbacks = (Callbacks) fragContext;
        } catch (ClassCastException e) {
            Log.d("ThemePickerDialog---->", context.toString() + "must Implement");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _callbacks = (Callbacks) activity.getFragmentManager().findFragmentByTag("detailFragment");
        } catch (ClassCastException e) {
            Log.d("ThemePickerDialog---->", activity.toString() + "must Implement");
        }
    }
}

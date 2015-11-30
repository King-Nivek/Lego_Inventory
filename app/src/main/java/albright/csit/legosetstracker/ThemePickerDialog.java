/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.29.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.29.2015

   Assignment:  Lego Sets Tracker
    File Name:  ThemePickerDialog
      Purpose:  
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
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ThemePickerDialog extends DialogFragment{
    private SpinnerThemeAdapter adapter;
    private Callbacks _callbacks;

    public void setAdapter(SpinnerThemeAdapter adapter){
        this.adapter = adapter;
    }

    interface Callbacks{
        public void onAdapterClick(int which);
        public void onBttnAddTheme();
    }

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

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
import android.widget.Button;
import android.widget.EditText;

public class AddThemeDialog extends DialogFragment{
    private View v;
    private TextInputLayout wrapperThemeName;
    private int maxLength;
    private Callbacks _callbacks;

    public void setMaxLength(int maxLength){
        this.maxLength = maxLength;
    }
    interface Callbacks{
        public boolean onBttnSaveTheme(TextInputLayout wrapper);
        public void onBttnCancelTheme();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //  Inner builder_addTheme
        AlertDialog.Builder builder_addTheme = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.add_theme_layout, null);
        wrapperThemeName = (TextInputLayout) v.findViewById(R.id.wrapper_dialog_themeName);
        wrapperThemeName.setCounterMaxLength(maxLength);

        builder_addTheme.setView(v)
            .setPositiveButton(R.string.update_dialogAddTheme_positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setNegativeButton(R.string.update_dialogAddTheme_negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    _callbacks.onBttnCancelTheme();
                }
            });
        return builder_addTheme.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null) {
            Button positiveButton = (Button)d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = _callbacks.onBttnSaveTheme(wrapperThemeName);
                    if(wantToCloseDialog)
                        d.dismiss();
                }
            });
        }
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
            Log.d("AddThemeDialog---->", context.toString() + "must Implement");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _callbacks = (Callbacks) activity.getFragmentManager().findFragmentByTag("detailFragment");
        } catch (ClassCastException e) {
            Log.d("AddThemeDialog---->", activity.toString() + "must Implement");
        }
    }
}

/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.30.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoThemeListFragment
      Purpose:  Used to add a new LegoTheme and update a LegoTheme
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.sql.SQLException;

public class LegoThemeDetailFragment extends Fragment {

    ////  Fields
    ////////////////////////////////////
    public static final String ARG_ITEM_ID = "legoThemeDetailFragment_itemId";
    public static final long INSERT_NEW_THEME_ID = -2;
    private static final int MAX_LENGTH_THEME_NAME = 30;

    private long id;
    private OnSaveLegoThemeListener _callback;
    private LegoTheme currentLegoTheme;
    private DbConnection db;

    private TextInputLayout wrapperThemeName;
    private EditText editText_legoThemeName;
    private Button bttn_save,
                   bttn_cancel;

    ////  Interface
    ////////////////////////////////////
    public interface OnSaveLegoThemeListener{
        public void onSaveLegoTheme(LegoTheme legoTheme);
    }

    ////  Constructors
    ////////////////////////////////////
    public LegoThemeDetailFragment() {
    }

    //  onCreate Function
    //
    //  Use:  Opens up the database and gets any values needed.  If this is
    //          created to add a new LegoTheme this will take that into account.
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Void
    //
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ARG_ITEM_ID)) {
            id = getArguments().getLong(ARG_ITEM_ID);
            db = new DbConnection(getActivity());
            try {
                db.open();
                if(id != INSERT_NEW_THEME_ID) {
                    currentLegoTheme = db.getLegoTheme(id);
                }
            } catch (SQLException e) {
                Log.d("Tag--->", "Caught");
            }
        }
    }

    //  onCreateView Function
    //
    //  Use:  Creates the view to be displayed to the user.
    //  Parameter(s):  LayoutInflater:inflater, ViewGroup:container, Bundle:savedInstanceState
    //  Returns:  View
    //
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.theme_create_update_layout, container, false);

        ////  Get and setup Views
        ////////////////////////////////////
        //  Theme Name
        wrapperThemeName= (TextInputLayout)view.findViewById(R.id.wrapper_update_themeName);
        wrapperThemeName.setCounterMaxLength(MAX_LENGTH_THEME_NAME);
        editText_legoThemeName = wrapperThemeName.getEditText();
        editText_legoThemeName.setFilters(new InputFilter[]{
            new RegexInputFilter("[\\p{Alnum},.&'\" -]+")
        });
        editText_legoThemeName.addTextChangedListener(generalTextWatcher(wrapperThemeName));

        //  Cancel Button
        bttn_cancel = (Button)view.findViewById(R.id.button_update_cancel);
        bttn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Check who the parent is; whether this is a phone or a tablet.
                if(_callback instanceof LegoThemeListActivity) {
                    Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("detailFragment");
                    getActivity().getFragmentManager().beginTransaction().detach(fragment).commit();
                }else {
                    getActivity().finish();
                }
            }
        });

        //  Save Button
        bttn_save = (Button)view.findViewById(R.id.button_update_save);
        bttn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidThemeName()){
                    currentLegoTheme = LegoTheme.copy(getInput());
                    currentLegoTheme.setAutoId(id);
                    insertData();
                    _callback.onSaveLegoTheme(currentLegoTheme);
                    //  Check who the parent is; whether this is a phone or a tablet.
                    if (_callback instanceof LegoThemeListActivity) {
                        Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("detailFragment");
                        getActivity().getFragmentManager().beginTransaction().detach(fragment).commit();
                    }
                }
            }
        });

        //  If Viewing/Updating a Theme
        if(currentLegoTheme != null){
            editText_legoThemeName.setText(currentLegoTheme.getName());
        }
        return view;
    }

    //  getInput Function
    //
    //  Use:  Gets the user input from the input fields and returns said input
    //          in a LegoTheme.
    //  Parameter(s):  Void
    //  Returns:  LegoTheme
    //
    public LegoTheme getInput(){
        LegoTheme tmpTheme = new LegoTheme();
        tmpTheme.setName(editText_legoThemeName.getText().toString());
        return tmpTheme;
    }

    //  insertData Function
    //
    //  Use:  Inserts or updates the database with values.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void insertData(){
        if (currentLegoTheme.getAutoId() > 0) {
            //  Update values
            db.updateLegoTheme(currentLegoTheme);
            db.close();
        } else {
            //  Insert values
            db.insertLegoTheme(currentLegoTheme);
            db.close();
        }
    }

    //  isValidThemeName Function
    //
    //  Use:  Checks if field is zero length, greater then max length, if the
    //          setId has been used, or if the Theme name has already been used.
    //          Will then set appropriate error message.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidThemeName(){
        boolean isValid = false;
        if(editText_legoThemeName.getText().length() == 0){
            wrapperThemeName.setError("Please Enter something Into this field.");
        }else if(editText_legoThemeName.getText().length() > MAX_LENGTH_THEME_NAME){
            wrapperThemeName.setError("Theme Name is to long.");
        }else if(db.hasLegoThemeName(editText_legoThemeName.getText().toString())) {
            wrapperThemeName.setError("This Theme Name has already been used.");
        }else {
            wrapperThemeName.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  generalTextWatcher Function
    //
    //  Use:  Used to give individualized watchers to Buttons and
    //          TextInputLayouts; to watch for changes and clear errors when
    //          user changes something in them.  Mostly used so I did not have
    //          to implement a TextWatcher on each one, having lots of code.
    //  Parameter(s):  View:v
    //  Returns:  TextWatcher
    //
    private TextWatcher generalTextWatcher(View v){
        final View wrapper, other;
        if(v instanceof TextInputLayout){
            wrapper = v;
            other = ((TextInputLayout)v).getEditText();
        }else {
            wrapper = null;
            other = v;
        }
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(wrapper != null){
                    ((TextInputLayout)wrapper).setError(null);
                }else {
                    ((TextView)other).setError(null);
                }
            }
        };
        return tw;
    }

    //  onAttach Functions
    //
    //  Use:  Attaches the dialog to the activity.  While making sure that the
    //          _callbacks function has been implemented in the activity class.
    //  Parameter(s):  Activity:activity OR if on API 23 Context:context
    //  Returns:  none
    //
    @TargetApi(23)
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            _callback = (OnSaveLegoThemeListener)context;
        }catch (ClassCastException e){
            Log.d("LegoThemeDetailFragm:", context.toString() + " must Implement");
        }
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            _callback = (OnSaveLegoThemeListener)activity;
        }catch (ClassCastException e){
            Log.d("LegoThemeDetailFragm:", activity.toString() + " must Implement");
        }
    }
}

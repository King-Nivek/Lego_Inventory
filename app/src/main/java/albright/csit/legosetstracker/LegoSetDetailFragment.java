/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.16.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.30.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSetListFragment.java
      Purpose:  Used to add a new LegoSet, update a LegoSet, or just to see the
                  info in a LegoSet.
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
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
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Locale;

public class LegoSetDetailFragment extends Fragment
    implements DatePickerFragment.OnDatePickedListener,
                /*NumberPickerFragment.OnNumberPickedListener,*/
                ThemePickerDialog.Callbacks,
                AddThemeDialog.Callbacks{

    ////  Fields
    ////////////////////////////////////
    public static final String ARG_ITEM_ID = "legoSetDetailFragment_itemId";
    public static final long INSERT_NEW_SET_ID = -2;
    private static final int MAX_LENGTH_SET_ID = 15,
                             MAX_LENGTH_SET_NAME = 50,
                             MAX_LENGTH_THEME_NAME = 30,
                             MAX_LENGTH_SET_PIECES = 5,
                             MAX_LENGTH_SET_QUANTITY = 4;

    private DialogFragment themePickerDialog, addThemeDialog;
    private long id;
    private OnSaveLegoSetListener _callback;
    private LegoSet currentLegoSet;
    private ArrayList<LegoTheme> legoThemes;
    private DbConnection db;
    private SpinnerThemeAdapter themeAdapter;
    private LegoTheme selectedLegoTheme;

    private TextInputLayout wrapperSetId,
                            wrapperSetName,
                            wrapperSetPieces,
                            wrapperSetQuantity;
    private EditText editText_legoSetId,
                     editText_legoSetName,
                     editText_legoSetPieces,
                     editText_legoSetQuantity;
    private Button bttn_theme,
                   bttn_acquiredDate,
                   bttn_save,
                   bttn_cancel;

    ////  Interface
    ////////////////////////////////////
    public interface OnSaveLegoSetListener{
        public void onSaveLegoSet(LegoSet legoSet);
    }

    ////  Constructors
    ////////////////////////////////////
    public LegoSetDetailFragment() {
    }

    //  onCreate Function
    //
    //  Use:  Opens up the database and gets any values needed.  If this is
    //          created to add a new LegoSet this will take that into account.
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
                if(id != INSERT_NEW_SET_ID) {
                    currentLegoSet = db.getLegoSet(id);
                }
                legoThemes = db.getAllLegoThemes();
                sortThemes(legoThemes);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_create_update_layout, container, false);

        ////  Get and setup Views
        ////////////////////////////////////
        //  Set ID
        wrapperSetId= (TextInputLayout)view.findViewById(R.id.wrapper_update_setId);
        wrapperSetId.setCounterMaxLength(MAX_LENGTH_SET_ID);
        editText_legoSetId = wrapperSetId.getEditText();
        editText_legoSetId.setFilters(new InputFilter[]{
            new RegexInputFilter("[\\d-]+")
        });
        editText_legoSetId.addTextChangedListener(generalTextWatcher(wrapperSetId));

        //  Set Name
        wrapperSetName= (TextInputLayout)view.findViewById(R.id.wrapper_update_setName);
        wrapperSetName.setCounterMaxLength(MAX_LENGTH_SET_NAME);
        editText_legoSetName = wrapperSetName.getEditText();
        editText_legoSetName.setFilters(new InputFilter[]{
            new RegexInputFilter("[\\p{Alnum},.&'\" -]+")
        });
        editText_legoSetName.addTextChangedListener(generalTextWatcher(wrapperSetName));

        //  Set Theme
        bttn_theme = (Button)view.findViewById(R.id.button_update_theme);
        themeAdapter = new SpinnerThemeAdapter(getActivity(), R.layout.lego_theme_list_row,
                R.id.textView_themeRow, legoThemes);
        bttn_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createThemeDialog();
            }
        });
        bttn_theme.addTextChangedListener(generalTextWatcher(bttn_theme));

        //  Set Acquired Date
        bttn_acquiredDate = (Button)view.findViewById(R.id.button_update_dateField);
        bttn_acquiredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });
        bttn_acquiredDate.addTextChangedListener(generalTextWatcher(bttn_acquiredDate));

        //  Set Pieces
        wrapperSetPieces= (TextInputLayout)view.findViewById(R.id.wrapper_update_setNumberOfPieces);
        wrapperSetPieces.setCounterMaxLength(MAX_LENGTH_SET_PIECES);
        editText_legoSetPieces = wrapperSetPieces.getEditText();
        editText_legoSetPieces.setFilters(new InputFilter[]{
            new RegexInputFilter("[\\d]+")
        });
        editText_legoSetPieces.addTextChangedListener(generalTextWatcher(wrapperSetPieces));

        //  Set Quantity
        wrapperSetQuantity= (TextInputLayout)view.findViewById(R.id.wrapper_update_setQuantity);
        wrapperSetQuantity.setCounterMaxLength(MAX_LENGTH_SET_QUANTITY);
        editText_legoSetQuantity = wrapperSetQuantity.getEditText();
        editText_legoSetQuantity.setFilters(new InputFilter[]{
            new RegexInputFilter("[\\d]+")
        });
        editText_legoSetQuantity.addTextChangedListener(generalTextWatcher(wrapperSetQuantity));

        //  Cancel Button
        bttn_cancel = (Button)view.findViewById(R.id.button_update_cancel);
        bttn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Check who the parent is; whether this is a phone or a tablet.
                if(_callback instanceof LegoSetListActivity) {
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
                if(isValidInput()){
                    currentLegoSet = LegoSet.copy(getInput());
                    currentLegoSet.setAutoId(id);
                    insertData();
                    _callback.onSaveLegoSet(currentLegoSet);
                    //  Check who the parent is; whether this is a phone or a tablet.
                    if (_callback instanceof LegoSetListActivity) {
                        Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("detailFragment");
                        getActivity().getFragmentManager().beginTransaction().detach(fragment).commit();
                    }
                }
            }
        });

        //  If Viewing/Updating a set then fill values in from given LegoSet
        if(currentLegoSet != null){
            selectedLegoTheme = new LegoTheme(currentLegoSet.getThemeId(), currentLegoSet.getThemeName());
            editText_legoSetId.setText(currentLegoSet.getId());
            editText_legoSetName.setText(currentLegoSet.getName());
            bttn_theme.setText(currentLegoSet.getThemeName());
            bttn_acquiredDate.setText(currentLegoSet.getAcquiredDate());
            editText_legoSetPieces.setText(String.valueOf(currentLegoSet.getPieces()));
            editText_legoSetQuantity.setText(String.valueOf(currentLegoSet.getQuantity()));
        }
        return view;
    }

    //  onDatePicked Function Implementation
    //
    //  Use:  Gets the date that the user selected and stores this as the date
    //          buttons text.
    //  Parameter(s):  int:year, int:month, int:day
    //  Returns:  Void
    //
    public void onDatePicked(int year, int month, int day){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        GregorianCalendar date = new GregorianCalendar(year, month, day);
        bttn_acquiredDate.setText(dateFormat.format(date.getTime()));
    }

    //  I did not end up using this.  It does work, but I chose not to refine it
    //    to look better.  Is the Implementation function for getting values
    //    from the number picker dialog.
    /*
    public void onNumberPicked(int number){
        editText_legoSetQuantity.setText(String.valueOf(number));
    }
    */

    //  getInput Function
    //
    //  Use:  Gets the user input from the input fields and returns said input
    //          in a LegoSet.
    //  Parameter(s):  Void
    //  Returns:  LegoSet
    //
    public LegoSet getInput(){
        LegoSet tmpSet = new LegoSet();
        tmpSet.setId(editText_legoSetId.getText().toString());
        tmpSet.setName(editText_legoSetName.getText().toString());
        tmpSet.setThemeId(selectedLegoTheme.getAutoId());
        tmpSet.setThemeName(selectedLegoTheme.getName());
        tmpSet.setAcquiredDate(bttn_acquiredDate.getText().toString());
        tmpSet.setPieces(Integer.valueOf(editText_legoSetPieces.getText().toString()));
        tmpSet.setQuantity(Integer.valueOf(editText_legoSetQuantity.getText().toString()));
        return tmpSet;
    }

    //  insertData Function
    //
    //  Use:  Inserts or updates the database with values.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void insertData(){
        if (currentLegoSet.getAutoId() > 0) {
            //  Update values
            db.updateLegoSet(currentLegoSet);
            db.close();
        } else {
            //  Insert values
            db.insertLegoSet(currentLegoSet);
            db.close();
        }
    }

    //  createThemeDialog Function
    //
    //  Use:  Creates the themePickerDialog and the addThemeDialog.
    //          Sets the themeAdapter and MAX_LENGTH_THEME_NAME for these
    //          dialogs.  It will then start the themePickerDialog fragment.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    private void createThemeDialog(){
        themePickerDialog = new ThemePickerDialog();
        addThemeDialog = new AddThemeDialog();
        ((ThemePickerDialog)themePickerDialog).setAdapter(themeAdapter);
        ((AddThemeDialog)addThemeDialog).setMaxLength(MAX_LENGTH_THEME_NAME);
        themePickerDialog.show(getFragmentManager(), "themePicker");
    }

    //  onAdapterClick Function Implementation
    //
    //  Use:  Gets user's Theme selection and sets it to the text of the theme
    //          button.
    //  Parameter(s):  int:which
    //  Returns:  Void
    //
    public void onAdapterClick(int which){
        selectedLegoTheme = themeAdapter.getItem(which);
        bttn_theme.setText(selectedLegoTheme.getName());
    }

    //  onBttnAddTheme Function Implementation
    //
    //  Use:  Starts the addThemeDialog when user clicks this button.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void onBttnAddTheme(){
        addThemeDialog.show(getFragmentManager(), "addTheme");
    }

    //  onBttnSaveTheme Function Implementation
    //
    //  Use:  Sets the functionality of the save button for addThemeDialog.
    //          Checks for valid input and when valid will save new theme to
    //          the TableLegoTheme.  At which point it will return true so the
    //          dialog can be dismissed.
    //  Parameter(s):  TextInputLayout:wrapper
    //  Returns:  boolean
    //
    public boolean onBttnSaveTheme(TextInputLayout wrapper){
        boolean isValid = false;
        EditText editText_themeName = wrapper.getEditText();

        if (isValidThemeInput(wrapper, editText_themeName)) {
            String text = editText_themeName.getText().toString();
            LegoTheme theme = new LegoTheme(-1, text);
            db.insertLegoTheme(theme);
            legoThemes.add(theme);
            sortThemes(legoThemes);
            selectedLegoTheme = theme;
            bttn_theme.setText(selectedLegoTheme.getName());
            themeAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Theme Saved", Toast.LENGTH_LONG).show();
            isValid = true;
        }
        return isValid;
    }

    //  onBttnCancelTheme Function Implementation
    //
    //  Use:  Will cancel the addThemeDialog and return the themePickerDialog.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void onBttnCancelTheme(){
        themePickerDialog.show(getFragmentManager(), "themePicker");
        addThemeDialog.dismiss();
    }

    ////  Validity functions.
    ////////////////////////////////////////

    //  isValidInput Function
    //
    //  Use:  Checks validity for each item separately then checks if any of
    //          those returned values are false. If any are false returns FALSE.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidInput(){
        boolean validId, validName, validTheme, validDate, validPieces, validQuantity;
        validId = isValidId();
        validName = isValidName();
        validTheme = isValidTheme();
        validDate = isValidDate();
        validPieces = isValidPieces();
        validQuantity = isValidQuantity();

        return (validId && validName
            && validTheme && validDate
            && validPieces && validQuantity);
    }

    //  isValidId Function
    //
    //  Use:  Checks if field is zero length, greater then max length, if the
    //          setId has been used, or if the setId and autoId have been used
    //          together.  Will then set appropriate error message.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidId(){
        boolean isValid = false;
        //  Check Set ID
        if(editText_legoSetId.getText().length() == 0){
            wrapperSetId.setError("Please Enter something Into this field.");
        }else if(editText_legoSetId.getText().length() > MAX_LENGTH_SET_ID){
            wrapperSetId.setError("Set ID is to long.");
        }else if(db.hasLegoSetId(editText_legoSetId.getText().toString())
                && !db.hasLegoSet(id, editText_legoSetId.getText().toString())){
            wrapperSetId.setError("This Set ID has already been used.");
        }else {
            wrapperSetId.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  isValidName Function
    //
    //  Use:  Checks if field is zero length, or greater then max length.
    //          Will then set appropriate error message.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidName(){
        boolean isValid = false;
        //  Check Set Name
        if(editText_legoSetName.getText().length() == 0){
            wrapperSetName.setError("Please Enter something Into this field.");
        }else if(editText_legoSetName.getText().length() > MAX_LENGTH_SET_NAME) {
            wrapperSetName.setError("Set Name is to long.");
        }else {
            wrapperSetName.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  isValidTheme Function
    //
    //  Use:  Checks if user has picked a theme or not.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidTheme(){
        boolean isValid = false;
        //  Check Set Theme
        if(bttn_theme.getText().toString().contentEquals(
            getString(R.string.update_button_theme_lbl_initialText))){
            bttn_theme.setError("Please choose a Theme.");
        }else {
            bttn_theme.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  isValidDate Function
    //
    //  Use:  Checks if user has picked a date or not.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidDate(){
        boolean isValid = false;
        //  Check Set Acquired Date
        if(bttn_acquiredDate.getText().toString().contentEquals(
            getString(R.string.update_button_date_lbl_initialText))){
            bttn_acquiredDate.setError("Please choose a Date.");
        }else {
            bttn_acquiredDate.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  isValidPieces Function
    //
    //  Use:  Checks if field is zero length, or greater then max length.
    //          Will then set appropriate error message.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidPieces(){
        boolean isValid = false;
        //  Check Set Pieces
        if(editText_legoSetPieces.getText().length() == 0){
            wrapperSetPieces.setError("Please Enter something Into this field.");
        }else if(editText_legoSetPieces.getText().length() > MAX_LENGTH_SET_PIECES) {
            wrapperSetPieces.setError("Set Pieces is to long.");
        }else {
            wrapperSetPieces.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  isValidQuantity Function
    //
    //  Use:  Checks if field is zero length, or greater then max length.
    //          Will then set appropriate error message.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidQuantity(){
        boolean isValid = false;
        //  Check Set Quantity
        if(editText_legoSetQuantity.getText().length() == 0){
            wrapperSetQuantity.setError("Please Enter something Into this field.");
        }else if(editText_legoSetQuantity.getText().length() > MAX_LENGTH_SET_QUANTITY) {
            wrapperSetQuantity.setError("Set Quantity");
        }else {
            wrapperSetQuantity.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  isValidThemeInput Function
    //
    //  Use:  Checks if field is zero length, greater then max length, if the
    //          setId has been used, or if the Theme name has already been used.
    //          Will then set appropriate error message.
    //  Parameter(s):  Void
    //  Returns:  boolean
    //
    private boolean isValidThemeInput(TextInputLayout wrapper, EditText editText){
        boolean isValid = false;
        if(editText.getText().length() == 0){
            wrapper.setError("Please Enter something Into this field.");
        }else if(editText.getText().length() > MAX_LENGTH_THEME_NAME){
            wrapper.setError("Theme Name is to long.");
        }else if(db.hasLegoThemeName(editText.getText().toString())) {
            wrapper.setError("This Theme Name has already been used.");
        }else {
            wrapper.setError(null);
            isValid = true;
        }
        return isValid;
    }

    //  sortThemes Function
    //
    //  Use:  Sorts the Themes in the themePickerDialog by Name.
    //  Parameter(s):  ArrayList<LegoTheme>:legoThemes
    //  Returns:  Void
    //
    private void sortThemes(ArrayList<LegoTheme> legoThemes){
        Collections.sort(legoThemes, new Comparator<LegoTheme>() {
            @Override
            public int compare(LegoTheme lhs, LegoTheme rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
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
            _callback = (OnSaveLegoSetListener)context;
        }catch (ClassCastException e){
            Log.d("LegoSetDetailFragment:", context.toString() + " must Implement");
        }
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            _callback = (OnSaveLegoSetListener)activity;
        }catch (ClassCastException e){
            Log.d("LegoSetDetailFragment:", activity.toString() + " must Implement");
        }
    }
}

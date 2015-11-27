/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.16.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.16.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSetListFragment
      Purpose:  
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class LegoSetDetailFragment extends Fragment
    implements DatePickerFragment.OnDatePickedListener,
                NumberPickerFragment.OnNumberPickedListener {

    ////  Fields
    ////////////////////////////////////
    public static final String ARG_ITEM_ID = "legoSetDetailFragment_itemId";

    private OnSaveLegoSetListener _callback;
    private LegoSet legoSet;
    private ArrayList<LegoTheme> legoThemes;
    private DbConnection db;
    private SpinnerThemeAdapter spinnerThemeAdapter;

    private EditText editText_legoSetId,
                     editText_legoSetName,
                     editText_legoSetPieces,
                     editText_legoSetQuantity;
    private Spinner spinner_themes;
    private Button bttn_acquiredDate,
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ARG_ITEM_ID)) {
            long id = getArguments().getLong(ARG_ITEM_ID);
            db = new DbConnection(getActivity());
            try {
                db.open();
                if(id > -1) {
                    legoSet = db.getLegoSet(id);
                }
                legoThemes = db.getAllLegoThemes();
            } catch (SQLException e) {
                Log.d("Tag--->", "Caught");
            }
        }
    }

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_create_update_layout, container, false);

        editText_legoSetId = (EditText)view.findViewById(R.id.editText_update_setId);
        editText_legoSetName = (EditText)view.findViewById(R.id.editText_update_setName);
        spinner_themes = (Spinner)view.findViewById(R.id.spinner_update_theme);
        editText_legoSetPieces = (EditText)view.findViewById(R.id.editText_update_setNumberOfPieces);
        editText_legoSetQuantity = (EditText)view.findViewById(R.id.editText_update_setQuantity);

        //  Date Button
        bttn_acquiredDate = (Button)view.findViewById(R.id.button_update_dateField);
        bttn_acquiredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        //  Quantity Entry
        editText_legoSetQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment numberPickerFragment = new NumberPickerFragment();
                numberPickerFragment.show(getFragmentManager(), "numberPicker");
            }
        });

        //  Save Button
        bttn_save = (Button)view.findViewById(R.id.button_update_save);
        bttn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("detailFragment");
                getActivity().getFragmentManager().beginTransaction().detach(fragment).commit();
            }
        });

        bttn_cancel = (Button)view.findViewById(R.id.button_update_cancel);
        bttn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("detailFragment");
                getActivity().getFragmentManager().beginTransaction().detach(fragment).commit();
            }
        });

        if(legoThemes != null){
            spinnerThemeAdapter = new SpinnerThemeAdapter(getActivity(), R.layout.spinner_layout,
                                        R.id.textView_spinnerRow, legoThemes);
            spinner_themes.setAdapter(spinnerThemeAdapter);
        }
        if(legoSet != null){
            editText_legoSetId.setText(legoSet.getId());
            editText_legoSetName.setText(legoSet.getName());
//            spinner_themes.f TODO Add to Adapter to find view by Item ID
            int position = (int)legoSet.getThemeId();
            if((long)position == legoSet.getThemeId()) {
                spinner_themes.setSelection(position -1);
            }//Else conversion was incorrect.

            bttn_acquiredDate.setText(legoSet.getAcquiredDate());
            editText_legoSetPieces.setText(String.valueOf(legoSet.getPieces()));
            editText_legoSetQuantity.setText(String.valueOf(legoSet.getQuantity()));
        }

        return view;
    }

    public void onDatePicked(int year, int month, int day){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        GregorianCalendar date = new GregorianCalendar(year, month, day);
        bttn_acquiredDate.setText(dateFormat.format(date.getTime()));
    }

    public void onNumberPicked(int number){
        editText_legoSetQuantity.setText(String.valueOf(number));
    }

    public void insertData(){
        LegoSet inputLegoSet = getInput();
        if(legoSet != null) {
            if (legoSet.getAutoId() > 0) {
                //  Update values
                updateLegoSet(inputLegoSet);
                db.updateLegoSet(legoSet);
                _callback.onSaveLegoSet(legoSet);
            }
        }else {
            //  Insert new LegoSet
            db.insertLegoSet(inputLegoSet);
            _callback.onSaveLegoSet(inputLegoSet);

        }
    }

    public LegoSet getInput(){
        LegoSet tmpSet = new LegoSet();
        tmpSet.setId(editText_legoSetId.getText().toString());
        tmpSet.setName(editText_legoSetName.getText().toString());
        tmpSet.setThemeId(spinner_themes.getSelectedItemId());
        tmpSet.setThemeName(legoThemes.get(spinner_themes.getSelectedItemPosition()).getName());
        tmpSet.setAcquiredDate(bttn_acquiredDate.getText().toString());
        tmpSet.setPieces(Integer.valueOf(editText_legoSetPieces.getText().toString()));
        tmpSet.setQuantity(Integer.valueOf(editText_legoSetQuantity.getText().toString()));
        return tmpSet;
    }

    public void updateLegoSet(LegoSet inputLegoSet){
        if(!legoSet.equalsId(inputLegoSet.getId())){
            legoSet.setId(inputLegoSet.getId());
        }
        if(!legoSet.equalsName(inputLegoSet.getName())){
            legoSet.setName(inputLegoSet.getName());
        }
        if(!legoSet.equalsThemeId(inputLegoSet.getThemeId())){
            legoSet.setThemeId(inputLegoSet.getThemeId());
            legoSet.setThemeName(inputLegoSet.getThemeName());
        }
        if(!legoSet.equalsAcquiredDate(inputLegoSet.getAcquiredDate())){
            legoSet.setAcquiredDate(inputLegoSet.getAcquiredDate());
        }
        if(!legoSet.equalsPieces(inputLegoSet.getPieces())){
            legoSet.setPieces(inputLegoSet.getPieces());
        }
        if(!legoSet.equalsQuantity(inputLegoSet.getQuantity())){
            legoSet.setQuantity(inputLegoSet.getQuantity());
        }
    }
}

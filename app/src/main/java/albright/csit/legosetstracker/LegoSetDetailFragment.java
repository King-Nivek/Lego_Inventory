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

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

public class LegoSetDetailFragment extends Fragment {

    ////  Fields
    ///////////////////////////////////
    public static final String ARG_ITEM_ID = "legoSetDetailFragment_itemId";

    private LegoSet legoSet;
    private ArrayList<LegoTheme> legoThemes;
    private DbConnection db;



    ////  Constructors
    ///////////////////////////////////
    public LegoSetDetailFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ARG_ITEM_ID)) {
            long id = getArguments().getLong(ARG_ITEM_ID);
            db = new DbConnection(getActivity());
            try {
                db.open();
                legoSet = db.getLegoSet(id);
                legoThemes = db.getAllLegoThemes();
            } catch (SQLException e) {
                Log.d("Tag--->", "Caught");
            }
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_create_update_layout, container, false);
        if(legoThemes != null){
//            LegoThemeSpinnerAdapter legoThemeSpinnerAdapter;
//            ((Spinner) view.findViewById(R.id.spinner_update_theme)).setAdapter(spinnerAdapter);
        }
        if(legoSet != null){
            ((EditText)view.findViewById(R.id.editText_update_setNumber)).setText(legoSet.getId());
            ((EditText)view.findViewById(R.id.editText_update_setName)).setText(legoSet.getName());
            ((Button)view.findViewById(R.id.button_update_dateField)).setText(legoSet.getAcquiredDate());
            ((EditText)view.findViewById(R.id.editText_update_setNumberOfPieces)).setText(String.valueOf(legoSet.getPieces()));
        }

        return view;
    }

}

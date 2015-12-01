/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.25.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  SpinnerThemeAdapter.java
      Purpose:  Used to by the ThemePickerDialog to fill in a list of themes.
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class SpinnerThemeAdapter extends ArrayAdapter<LegoTheme>{
    ////  Fields
    ////////////////////////////////////////
    private ArrayList<LegoTheme> legoThemes;

    //  getItemId Function
    //
    //  Use:  Returns the LegoTheme autoId value for the LegoTheme at given position.
    //  Parameter(s):  int:position
    //  Returns:  long
    //
    public long getItemId(int position){
        return legoThemes.get(position).getAutoId();
    }

    //  getItem Function
    //
    //  Use:  Get the item at the given position.
    //  Parameter(s):  int:position
    //  Returns:  LegoTheme
    //
    public LegoTheme getItem(int position){
        return legoThemes.get(position);
    }

    //  getCount Function
    //
    //  Use:  Get the size of the list.
    //  Parameter(s):  Void
    //  Returns:  int
    //
    public int getCount(){
        return legoThemes.size();
    }

    ////  Constructors
    ////////////////////////////////////////
    public SpinnerThemeAdapter(Context context, int resource, int textViewResourceId, ArrayList<LegoTheme> values){
        super(context, resource, textViewResourceId, values);
        legoThemes = values;
    }
}

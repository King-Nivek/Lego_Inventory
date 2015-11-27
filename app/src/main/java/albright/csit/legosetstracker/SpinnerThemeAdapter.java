/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.25.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.25.2015

   Assignment:  Lego Sets Tracker
    File Name:  SpinnerThemeAdapter
      Purpose:  
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class SpinnerThemeAdapter extends ArrayAdapter<LegoTheme>{
    private ArrayList<LegoTheme> legoThemes;

    public long getItemId(int position){
        return legoThemes.get(position).getAutoId();
    }

    public LegoTheme getItem(int position){
        return legoThemes.get(position);
    }

    public int getCount(){
        return legoThemes.size();
    }


    public SpinnerThemeAdapter(Context context, int resource, int textViewResourceId, ArrayList<LegoTheme> values){
        super(context, resource, textViewResourceId, values);

        legoThemes = values;
    }
}

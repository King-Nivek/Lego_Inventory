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

import android.app.ListFragment;
import android.util.Log;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class LegoSetListFragment extends ListFragment{
    private DbConnection db;
    private ArrayList<LegoSet> legoSetsList;
    public void testing(){
        try{
            db.open();
            legoSetsList = db.getAllLegoSets();
        }catch (SQLException e){
            Log.e("DbConnection:-->", "Error could not open database connection", e);
        }
    }

    public void sortAutoId(){
        Collections.sort(legoSetsList, new Comparator<LegoSet>() {
            @Override
            public int compare(LegoSet s1, LegoSet s2) {
                return ((Long) s1.getAutoId()).compareTo(s2.getAutoId());
            }
        });
    }

    public void sortSetId(){
        Collections.sort(legoSetsList, new Comparator<LegoSet>() {
            @Override
            public int compare(LegoSet s1, LegoSet s2) {
                return s1.getId().compareToIgnoreCase(s2.getId());
            }
        });
    }

    public void sortSetName(){
        Collections.sort(legoSetsList, new Comparator<LegoSet>() {
            @Override
            public int compare(LegoSet s1, LegoSet s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
    }

    public void sortThemeId(){
        Collections.sort(legoSetsList, new Comparator<LegoSet>() {
            @Override
            public int compare(LegoSet s1, LegoSet s2) {
                return ((Long) s1.getThemeId()).compareTo(s2.getThemeId());
            }
        });
    }

    public void sortPieces(){
        Collections.sort(legoSetsList, new Comparator<LegoSet>() {
            @Override
            public int compare(LegoSet s1, LegoSet s2) {
                return ((Integer) s1.getPieces()).compareTo(s2.getPieces());
            }
        });
    }

    public void sortAcquiredDate(){
        //TODO
    }

    public void sortQuantity(){
        Collections.sort(legoSetsList, new Comparator<LegoSet>() {
            @Override
            public int compare(LegoSet s1, LegoSet s2) {
                return ((Integer) s1.getQuantity()).compareTo(s2.getQuantity());
            }
        });
    }

    public void sortingType(int type, boolean isDesc){
        switch (type){
            case R.integer.SORT_AUTO_ID:
                sortAutoId();
                break;
            case R.integer.SORT_ID:
                sortSetId();
                break;
            case R.integer.SORT_NAME:
                sortSetName();
                break;
            case R.integer.SORT_THEME_ID:
                sortThemeId();
                break;
            case R.integer.SORT_PIECES:
                sortPieces();
                break;
            case R.integer.SORT_ACQUIRED_DATE:
                sortAcquiredDate();
                break;
            case R.integer.SORT_QUANTITY:
                sortQuantity();
                break;
        }
        if(isDesc){
            Collections.reverse(legoSetsList);
        }
    }
}

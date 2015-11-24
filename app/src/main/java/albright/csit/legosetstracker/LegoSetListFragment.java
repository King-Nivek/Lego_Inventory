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
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.sql.SQLException;
import java.util.ArrayList;

public class LegoSetListFragment extends Fragment implements LegoSetListAdapter.ViewHolder.ClickListener{

    ////  Fields
    ///////////////////////////////////
    private static final String STATE_ACTIVATED_POSITION = "legoSetListFragment_activatedPosition";
    private Callbacks _callbacks = activityCall;
    private int _activatedPosition = -1;


    private DbConnection db;
    private ArrayList<LegoSet> legoSetsList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DefaultItemAnimator itemAnimator;



    ////  interfaces
    ///////////////////////////////////
    public interface Callbacks{
        public void onItemSelected(long item);
    }

    //  Dummy Callback used when not attached to an activity.
    private static Callbacks activityCall = new Callbacks() {
        @Override
        public void onItemSelected(long item) {}
    };


    ////  Constructors
    ///////////////////////////////////
    public LegoSetListFragment(){
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        db = new DbConnection(getActivity());
        try{
            db.open();
            legoSetsList = db.getAllLegoSets();
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
            layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            itemAnimator = new DefaultItemAnimator();
            recyclerView.setItemAnimator(itemAnimator);
            adapter = new LegoSetListAdapter(legoSetsList, this);
            recyclerView.setAdapter(adapter);
            db.close();
        }catch (SQLException e){
            Log.e("DbConnection:-->", "Error could not open database connection", e);
        }
        return view;
    }

    public void onItemClicked(long id){
        _callbacks.onItemSelected(id);
    }

    public boolean onItemLongClicked(long id){
        return true;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        try{
            _callbacks = (Callbacks)context;
        }catch(ClassCastException e){
            Log.d("LegoSetListFragment:-->", context.toString());
        }
    }

    public void onDetach(){
        super.onDetach();
        _callbacks = activityCall;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
/*
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
    */
}

/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.16.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.16.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoThemeListFragment
      Purpose:  
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LegoThemeListFragment extends Fragment implements LegoThemeListAdapter.ViewHolder.ClickListener{

    ////  Fields
    ///////////////////////////////////
    private static final String STATE_ACTIVATED_POSITION = "legoThemeListFragment_activatedPosition";
    private Callbacks _callbacks = activityCall;
    private int _activatedPosition = -1;


    private DbConnection db;
    private ArrayList<LegoTheme> legoThemesList;
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
    public LegoThemeListFragment(){
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        db = new DbConnection(getActivity());
        try{
            db.open();
            legoThemesList = db.getAllLegoThemes();
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
            layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            itemAnimator = new DefaultItemAnimator();
            recyclerView.setItemAnimator(itemAnimator);
            Collections.sort(legoThemesList, new Comparator<LegoTheme>() {
                @Override
                public int compare(LegoTheme s1, LegoTheme s2) {
                    return s1.getName().compareToIgnoreCase(s2.getName());
                }
            });
            adapter = new LegoThemeListAdapter(legoThemesList, this);
            recyclerView.setAdapter(adapter);
        }catch (SQLException e){
            Log.e("DbConnection:-->", "Error could not open database connection", e);
        }
        return view;
    }

    public void onItemClicked(long id){
        _callbacks.onItemSelected(id);
    }

    public boolean onItemLongClicked(long id){
        boolean isTrue = false;
        if(db.usesTheme(id)){
            final int position = recyclerView.findViewHolderForItemId(id).getAdapterPosition();
            AlertDialog.Builder builder_cannot = new AlertDialog.Builder(getActivity());
            builder_cannot.setMessage("Lego Theme: \"" + legoThemesList.get(position).getName() + "\" is being used by a set and cannot be deleted.");
            builder_cannot.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }else {
            isTrue = true;
            final int position = recyclerView.findViewHolderForItemId(id).getAdapterPosition();
            AlertDialog.Builder builder_can = new AlertDialog.Builder(getActivity());
            builder_can.setMessage("Delete Lego Theme \"" + legoThemesList.get(position).getName() + "\"?");
            builder_can.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteLegoTheme(legoThemesList.get(position));
                    legoThemesList.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }
        return isTrue;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            _callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            Log.d("DatePickerFragment---->", context.toString() + "must Implement");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _callbacks = (Callbacks) activity;
        } catch (ClassCastException e) {
            Log.d("DatePickerFragment---->", activity.toString() + "must Implement");
        }
    }

    public void onDetach(){
        super.onDetach();
        _callbacks = activityCall;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void savedLegoTheme(LegoTheme legoTheme){
        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForItemId(legoTheme.getAutoId());
        int position;
        if(vh == null) {
            int tmpSize = legoThemesList.size();
            legoThemesList.add(legoTheme);
            adapter.notifyItemInserted(tmpSize);
            ((LegoThemeListAdapter)adapter).sortIds();
            Toast.makeText(getActivity(), "Theme Saved", Toast.LENGTH_LONG).show();
        }else {
            position = vh.getAdapterPosition();
            legoThemesList.set(position, legoTheme);
            adapter.notifyItemChanged(position);
            ((LegoThemeListAdapter)adapter).sortIds();
            Toast.makeText(getActivity(), "Theme Updated", Toast.LENGTH_LONG).show();
        }
    }
}

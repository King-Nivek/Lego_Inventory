/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.16.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSetListFragment.java
      Purpose:  Shows the user a list of LegoSets.  User may click on a LegoSet
                  to view its details and update its information.  The user may
                  also long click on a LegoSet to initiate a delete function.
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

public class LegoSetListFragment extends Fragment implements LegoSetListAdapter.ViewHolder.ClickListener{

    ////  Fields
    ///////////////////////////////////
    private Callbacks _callbacks = activityCall;
    private DbConnection db;
    private ArrayList<LegoSet> legoSetsList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DefaultItemAnimator itemAnimator;

    ////  Interfaces
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

    //  onCreate Function
    //
    //  Use:  Initializes the fragment.
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Void
    //
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    //  onCreateView Function
    //
    //  Use:  Creates the view to be displayed to the user.  Opens the database
    //          and retrieves a list of LegoSets to set up the recyclerView
    //  Parameter(s):  LayoutInflater:inflater, ViewGroup:container, Bundle:savedInstanceState
    //  Returns:  View
    //
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
            Collections.sort(legoSetsList, new Comparator<LegoSet>() {
                @Override
                public int compare(LegoSet s1, LegoSet s2) {
                    return s1.getId().compareToIgnoreCase(s2.getId());
                }
            });
            adapter = new LegoSetListAdapter(legoSetsList, this);
            recyclerView.setAdapter(adapter);
        }catch (SQLException e){
            Log.e("DbConnection:-->", "Error could not open database connection", e);
        }
        return view;
    }

    //  onItemClicked Function Implementation
    //
    //  Use:  Implements an implementation of what to do when an Item is clicked.
    //          Passes item id.
    //  Parameter(s):  long:id
    //  Returns:  Void
    //
    public void onItemClicked(long id){
        _callbacks.onItemSelected(id);
    }

    //  onItemLongClicked Function Implementation
    //
    //  Use:  Implements an implementation of what to do when an Item is long
    //          clicked.  Creates a dialog asking the user if they want to
    //          delete the selected LegoSet.  Will delete if user selects the
    //          delete button.
    //  Parameter(s):  long:id
    //  Returns:  boolean
    //
    public boolean onItemLongClicked(long id){
        final int position = recyclerView.findViewHolderForItemId(id).getAdapterPosition();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete Lego set \"" + legoSetsList.get(position).getName() + "\"?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteLegoSet(legoSetsList.get(position));
                legoSetsList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getActivity(), "Set Deleted", Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
        return true;
    }

    //  savedLegoSet Function
    //
    //  Use:  Updates the recyclerView for legoSet updates and insertions for
    //          given legoSet object.
    //  Parameter(s):  LegoSet:legoSet
    //  Returns:  Void
    //
    public void savedLegoSet(LegoSet legoSet){
        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForItemId(legoSet.getAutoId());
        int position;
        if(vh == null) {
            int tmpSize = legoSetsList.size();
            legoSetsList.add(legoSet);
            adapter.notifyItemInserted(tmpSize);
            ((LegoSetListAdapter)adapter).sortIds();
            Toast.makeText(getActivity(), "Set Saved", Toast.LENGTH_LONG).show();
        }else {
            position = vh.getAdapterPosition();
            legoSetsList.set(position, legoSet);
            adapter.notifyItemChanged(position);
            ((LegoSetListAdapter)adapter).sortIds();
            Toast.makeText(getActivity(), "Set Updated", Toast.LENGTH_LONG).show();
        }
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

    //  onDetach Function
    //
    //  Use:  reset what _callbacks holds on fragment detaching from activity.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void onDetach(){
        super.onDetach();
        _callbacks = activityCall;
    }

    //  onActivityCreated Function
    //
    //  Use:  Call super for onActivityCreated
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Void
    //
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

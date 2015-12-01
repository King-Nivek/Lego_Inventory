/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.30.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoThemeListFragment.java
      Purpose:  Shows the user a list of LegoThemes.  User may click on a
                  LegoTheme to view its details and update its information.  The
                  user may also long click on a LegoTheme to initiate a delete
                  function.
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
    private Callbacks _callbacks = activityCall;
    private DbConnection db;
    private ArrayList<LegoTheme> legoThemesList;
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
    public LegoThemeListFragment(){
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
    //          and retrieves a list of LegoThemes to set up the recyclerView
    //  Parameter(s):  LayoutInflater:inflater, ViewGroup:container, Bundle:savedInstanceState
    //  Returns:  View
    //
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
    //          clicked.  Checks to see if the selected theme is in active use.
    //          If true then will create a dialog telling the user that selected
    //          theme may not be deleted for it is in use. Else it will Create a
    //          dialog asking the user if they want to delete the selected
    //          LegoTheme.  Will delete if user selects the delete button.
    //  Parameter(s):  long:id
    //  Returns:  boolean
    //
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
                    Toast.makeText(getActivity(), "Theme Deleted", Toast.LENGTH_LONG).show();
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

    //  savedLegoTheme Function
    //
    //  Use:  Updates the recyclerView for legoTheme updates and insertions for
    //          given legoTheme object.
    //  Parameter(s):  LegoTheme:legoTheme
    //  Returns:  Void
    //
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

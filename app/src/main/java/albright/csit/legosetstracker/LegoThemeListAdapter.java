/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.30.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoThemeListAdapter
      Purpose:  This class is used to handle the LegoTheme List views.  Uses
                  interfaces to get implementations for clicks.
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LegoThemeListAdapter extends RecyclerView.Adapter<LegoThemeListAdapter.ViewHolder> {
    ////  Fields
    ////////////////////////////////////////
    private ArrayList<LegoTheme> values;
    private ViewHolder.ClickListener clickListener;

    ////  Inner Class ViewHolder
    ////////////////////////////////////////
    //  Used to create ViewHolders that will be recycled to better handle large
    //    lists of data.
    public static class ViewHolder extends RecyclerView.ViewHolder implements
                                View.OnClickListener, View.OnLongClickListener{

        ////  Inner Fields
        ////////////////////////////////////////
        public TextView themeName;
        private  ClickListener listener;

        ////  Inner Interface
        ////////////////////////////////////////
        public interface ClickListener{
            public void onItemClicked(long id);
            public boolean onItemLongClicked(long id);
        }

        ////  Inner Constructors
        ////////////////////////////////////////
        public ViewHolder(View v, ClickListener listener){
            super(v);
            themeName = (TextView)v.findViewById(R.id.textView_themeRow);

            this.listener = listener;
            v.setClickable(true);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        //  onClick Function Implementation
        //
        //  Use:  Implements an implementation to have a click do something.
        //  Parameter(s):  View:v
        //  Returns:  Void
        //
        public void onClick(View v){
            Log.d("ThemeAdapter_-->", "Item: " + getAdapterPosition() + " clicked.");
            if(listener != null){
                listener.onItemClicked(getItemId());
            }
        }

        //  onLongClick Function Implementation
        //
        //  Use:  Implements an implementation to have a long click do something.
        //  Parameter(s):  View:v
        //  Returns:  Void
        //
        public boolean onLongClick(View v){
            boolean value;
            Log.d("ThemeAdapter--->", "Item: " + getAdapterPosition() + " long clicked.");
            if(listener != null){
                value = listener.onItemLongClicked(getItemId());
            }else {
                value = false;
            }
            return value;
        }
    }

    ////  Constructor(s)
    ////////////////////////////////////////
    public LegoThemeListAdapter(ArrayList<LegoTheme> values, ViewHolder.ClickListener clickListener){
        this.values = values;
        this.clickListener = clickListener;
        setHasStableIds(true);
    }

    //  add Function
    //
    //  Use:  Not fully in use yet.  Used to notify an item change.
    //  Parameter(s):  int:position, LegoSet:item
    //  Returns:  Void
    //
    public void add(int position, LegoTheme item){
        notifyItemInserted(position);
    }

    //  remove Function
    //
    //  Use:  Not fully in use yet.  Will be used to notify an item change.
    //  Parameter(s):  int:position, LegoSet:item
    //  Returns:  Void
    //
    public void remove(LegoTheme item){
        int position = values.indexOf(item);
        notifyItemRemoved(position);
    }


    //  onCreateViewHolder Function Implementation
    //
    //  Use:  Creates a ViewHolder
    //  Parameter(s):  ViewGroup:parent, int:viewType
    //  Returns:  LegoThemeListAdapter.ViewHolder
    //
    public LegoThemeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lego_theme_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v, clickListener);
        return  vh;
    }

    //  onBindViewHolder Function Implementation
    //
    //  Use:  Binds values to a ViewHolder.
    //  Parameter(s):  ViewHolder:holder, int:position
    //  Returns:  Void
    //
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.themeName.setText(values.get(position).getName());
    }

    //  getItemCount Function Implementation
    //
    //  Use:  Gets the size of the list.
    //  Parameter(s):  Void
    //  Returns:  int
    //
    public int getItemCount(){
        return values.size();
    }

    //  getItemId Function
    //
    //  Use:  Returns the LegoTheme autoId value for the LegoTheme at given position.
    //  Parameter(s):  int:position
    //  Returns:  long
    //
    @Override
    public long getItemId(int position){
        return values.get(position).getAutoId();
    }

    //  sortIds Function
    //
    //  Use:  Sort the list by LegoTheme name.  Notifies data changes.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void sortIds(){
        Collections.sort(values, new Comparator<LegoTheme>() {
            @Override
            public int compare(LegoTheme lhs, LegoTheme rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        notifyDataSetChanged();
    }
}

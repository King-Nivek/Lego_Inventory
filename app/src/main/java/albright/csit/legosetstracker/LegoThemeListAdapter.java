/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.30.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.30.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoThemeListAdapter
      Purpose:  
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
    ///////////////////////////////////
    private ArrayList<LegoTheme> values;
    private ViewHolder.ClickListener clickListener;

    ////  Inner Class
    ///////////////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder implements
                                View.OnClickListener, View.OnLongClickListener{

        public TextView themeName;
        private  ClickListener listener;

        public ViewHolder(View v, ClickListener listener){
            super(v);
            themeName = (TextView)v.findViewById(R.id.textView_themeRow);

            this.listener = listener;
            v.setClickable(true);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public void onClick(View v){
            Log.d("ThemeAdapter_-->", "Item: " + getAdapterPosition() + " clicked.");
            if(listener != null){
                listener.onItemClicked(getItemId());
            }
        }

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

        public interface ClickListener{
            public void onItemClicked(long id);
            public boolean onItemLongClicked(long id);
        }
    }

    ////  Constructor(s)
    ///////////////////////////////////
    public LegoThemeListAdapter(ArrayList<LegoTheme> values, ViewHolder.ClickListener clickListener){
        this.values = values;
        this.clickListener = clickListener;
        setHasStableIds(true);
    }

    public void add(int position, LegoTheme item){
        notifyItemInserted(position);
    }

    public void remove(LegoTheme item){
        int position = values.indexOf(item);
        notifyItemRemoved(position);
    }


    public LegoThemeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lego_theme_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v, clickListener);
        return  vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.themeName.setText(values.get(position).getName());
    }

    public int getItemCount(){
        int size = values.size();
        return size;
    }

    public long getItemId(int position){
        return values.get(position).getAutoId();
    }

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

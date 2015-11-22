/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.17.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.17.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoListAdapter
      Purpose:  
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LegoSetListAdapter extends RecyclerView.Adapter<LegoSetListAdapter.ViewHolder> {
    ////  Fields
    ///////////////////////////////////
    private ArrayList<LegoSet> values;
    private ViewHolder.ClickListener clickListener;

    ////  Inner Class
    ///////////////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder implements
                                View.OnClickListener, View.OnLongClickListener{

        public TextView setId;
        public TextView setName;
        private  ClickListener listener;

        public ViewHolder(View v, ClickListener listener){
            super(v);
            long id;
            setId = (TextView)v.findViewById(R.id.textView_setNumber);
            setName = (TextView)v.findViewById(R.id.textView_setName);

            this.listener = listener;

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public void onClick(View v){
            Log.d("SetsAdapter_-->", "Item: " + getAdapterPosition() + " clicked.");
            if(listener != null){
                listener.onItemClicked(getItemId());
            }
        }

        public boolean onLongClick(View v){
            boolean value;
            Log.d("SetsAdapter--->", "Item: " + getAdapterPosition() + " long clicked.");
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
    public LegoSetListAdapter(ArrayList<LegoSet> values, ViewHolder.ClickListener clickListener){
        this.values = values;
        this.clickListener = clickListener;
        setHasStableIds(true);
    }

    public void add(int position, LegoSet item){
        notifyItemInserted(position);
    }

    public void remove(LegoSet item){
        int position = values.indexOf(item);
        notifyItemRemoved(position);
    }


    public LegoSetListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lego_set_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v, clickListener);
        return  vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        final LegoSet item = values.get(position);
        holder.setId.setText(values.get(position).getId());
        holder.setName.setText(values.get(position).getName());
    }

    public int getItemCount(){
        int size = values.size();
        return size;
    }

    public long getItemId(int position){
        return values.get(position).getAutoId();
    }
}

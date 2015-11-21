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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LegoSetListAdapter extends RecyclerView.Adapter<LegoSetListAdapter.ViewHolder> {

    private ArrayList<LegoSet> values;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView setId;
        public TextView setName;

        public ViewHolder(View v){
            super(v);
            setId = (TextView)v.findViewById(R.id.textView_setNumber);
            setName = (TextView)v.findViewById(R.id.textView_setName);
        }
    }

    public void add(int position, LegoSet item){
        notifyItemInserted(position);
    }

    public void remove(LegoSet item){
        int position = values.indexOf(item);
        notifyItemRemoved(position);
    }

    public LegoSetListAdapter(ArrayList<LegoSet> values){
        this.values = values;
    }

    public LegoSetListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lego_set_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return  vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        final String name = values.get(position).getId();
        holder.setId.setText(values.get(position).getId());
        holder.setName.setText(values.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), name, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public int getItemCount(){
        int size = values.size();
        return size;
    }
}

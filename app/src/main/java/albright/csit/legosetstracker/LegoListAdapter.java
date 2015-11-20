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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LegoListAdapter extends ArrayAdapter<LegoSet> {

    private final Context context;
    private final ArrayList<LegoSet> values;

    static class ViewHolder{
        public TextView setId;
        public TextView setName;
    }

    public LegoListAdapter(Context context, ArrayList<LegoSet> values){
        super(context, R.layout.lego_set_list_row, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        //TODO
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.lego_set_list_row, null);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.setId = (TextView) rowView.findViewById(R.id.textView_setNumber);
            viewHolder.setName = (TextView) rowView.findViewById(R.id.textView_setName);
            rowView.setTag(viewHolder);;
        }
        ViewHolder holder = (ViewHolder)rowView.getTag();
        holder.setId.setText((values.get(position).getId()));
        holder.setName.setText((values.get(position).getName()));
        return rowView;
    }
}

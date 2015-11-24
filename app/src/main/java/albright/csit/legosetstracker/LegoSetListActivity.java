package albright.csit.legosetstracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LegoSetListActivity extends ActivityMenu implements LegoSetListFragment.Callbacks{
    private boolean _twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);

        //  Set my toolbar to be the toolbar.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar_baseLayout);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.main_menu);
        setSupportActionBar(toolbar);


        if(findViewById(R.id.legoset_detail_container) != null){
            _twoPane = true;


        }
    }

    public void onItemSelected(long id){
        if(_twoPane){
            Bundle arguments = new Bundle();
            arguments.putLong(LegoSetDetailFragment.ARG_ITEM_ID, id);
            LegoSetDetailFragment detailFragment = new LegoSetDetailFragment();
            detailFragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                .replace(R.id.legoset_detail_container, detailFragment)
                .commit();
        }else{
            Intent detailIntent = new Intent(this, LegoSetDetailActivity.class);
            detailIntent.putExtra(LegoSetDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, R.string.menu_item_add_set, menu.size(), R.string.menu_item_add_set);
        MenuItem menuItem = menu.getItem(menu.size()-1);
        menuItem.setIcon(R.drawable.ic_add_white_24dp);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menuItem.setTitleCondensed("");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        boolean isSelected = false;
        switch(item.getItemId()){
            case R.string.menu_item_add_set:
                Toast toast = Toast.makeText(this, "Add new Set", Toast.LENGTH_LONG);
                toast.show();

                break;
            default:
                isSelected = super.onOptionsItemSelected(item);
        }
        return isSelected;
    }

}

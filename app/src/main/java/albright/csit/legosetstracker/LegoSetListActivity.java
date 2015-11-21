package albright.csit.legosetstracker;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LegoSetListActivity extends ActivityMenu {
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

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.fragment_legoset_list);

        if(findViewById(R.id.frameLayout_legosetList_detailContainer) != null){
            _twoPane = true;


        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, R.string.menu_item_add_set, menu.size(), R.string.menu_item_add_set);
        MenuItem menuItem = menu.getItem(menu.size()-1);
        menuItem.setIcon(android.R.drawable.ic_menu_add);
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

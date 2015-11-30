package albright.csit.legosetstracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class LegoSetListActivity extends ActivityMenu
        implements LegoSetListFragment.Callbacks,
                    LegoSetDetailFragment.OnSaveLegoSetListener{

    private static final int LEGO_SET_DETAIL_RESULTS = 1;
    private LegoSetListFragment legoSetListFragment;
    private boolean _twoPane;
    private LegoSet legoSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);

        //  Set my toolbar to be the toolbar.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar_baseLayout);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.main_menu);
        setSupportActionBar(toolbar);

        legoSetListFragment = (LegoSetListFragment)getFragmentManager().findFragmentById(R.id.fragment_legoset_list);

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
                .replace(R.id.legoset_detail_container, detailFragment, "detailFragment")
                .addToBackStack(null)
                .commit();
        }else{
            Intent detailIntent = new Intent(this, LegoSetDetailActivity.class);
            detailIntent.putExtra(LegoSetDetailFragment.ARG_ITEM_ID, id);
            startActivityForResult(detailIntent, LEGO_SET_DETAIL_RESULTS);
        }
    }

    public void onSaveLegoSet(LegoSet legoSet){
        this.legoSet = legoSet;
        legoSetListFragment.savedLegoSet(legoSet);
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
                onItemSelected(LegoSetDetailFragment.INSERT_NEW_SET_ID);

                break;
            default:
                isSelected = super.onOptionsItemSelected(item);
        }
        return isSelected;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ActivityMenu.RESULT_OK){
            switch (requestCode){
                case LEGO_SET_DETAIL_RESULTS:
                    Bundle legoSetBundle = data.getBundleExtra(LegoSetDetailActivity.RESULTS);
                    this.legoSet = LegoSet.readBundle(legoSetBundle);
                    legoSetListFragment.savedLegoSet(legoSet);
            }
        }
    }

}

package albright.csit.legosetstracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class LegoThemeListActivity extends ActivityMenu
        implements LegoThemeListFragment.Callbacks,
                    LegoThemeDetailFragment.OnSaveLegoThemeListener{

    private static final int LEGO_THEME_DETAIL_RESULTS = 1;
    private LegoThemeListFragment legoThemeListFragment;
    private boolean _twoPane;
    private LegoTheme legoTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legotheme_list_base);

        //  Set my toolbar to be the toolbar.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar_baseLayout);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.main_menu);
        setSupportActionBar(toolbar);

        legoThemeListFragment = (LegoThemeListFragment)getFragmentManager().findFragmentById(R.id.fragment_legotheme_list);

        if(findViewById(R.id.lego_detail_container) != null){
            _twoPane = true;


        }
    }

    public void onItemSelected(long id){
        if(_twoPane){
            Bundle arguments = new Bundle();
            arguments.putLong(LegoThemeDetailFragment.ARG_ITEM_ID, id);
            LegoThemeDetailFragment detailFragment = new LegoThemeDetailFragment();
            detailFragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                .replace(R.id.lego_detail_container, detailFragment, "detailFragment")
                .addToBackStack(null)
                .commit();
        }else{
            Intent detailIntent = new Intent(this, LegoThemeDetailActivity.class);
            detailIntent.putExtra(LegoThemeDetailFragment.ARG_ITEM_ID, id);
            startActivityForResult(detailIntent, LEGO_THEME_DETAIL_RESULTS);
        }
    }

    public void onSaveLegoTheme(LegoTheme legoTheme){
        this.legoTheme = legoTheme;
        legoThemeListFragment.savedLegoTheme(legoTheme);
    }



    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, R.string.menu_item_add_theme, menu.size(), R.string.menu_item_add_theme);
        MenuItem menuItem = menu.getItem(menu.size()-1);
        menuItem.setIcon(R.drawable.ic_add_white_24dp);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menuItem.setTitleCondensed("");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        boolean isSelected = false;
        switch(item.getItemId()){
            case R.string.menu_item_add_theme:
                onItemSelected(LegoThemeDetailFragment.INSERT_NEW_THEME_ID);

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
                case LEGO_THEME_DETAIL_RESULTS:
                    Bundle legoThemeBundle = data.getBundleExtra(LegoThemeDetailActivity.RESULTS);
                    this.legoTheme = LegoTheme.readBundle(legoThemeBundle);
                    legoThemeListFragment.savedLegoTheme(legoTheme);
            }
        }
    }

}

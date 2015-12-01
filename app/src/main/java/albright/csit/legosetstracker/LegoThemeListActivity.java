/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.30.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoThemeListActivity.java
      Purpose:  This activity is the launch activity.  It will act as the
                  control activity for all LegoTheme operations.  Handling who is
                  started and what is started. Whether it is fragments for large
                  screen sizes and activities for small screens.
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class LegoThemeListActivity extends ActivityMenu
        implements LegoThemeListFragment.Callbacks,
                    LegoThemeDetailFragment.OnSaveLegoThemeListener{

    ////  Fields
    ////////////////////////////////////////
    private static final int LEGO_THEME_DETAIL_RESULTS = 1;
    private LegoThemeListFragment legoThemeListFragment;
    private boolean _twoPane;
    private LegoTheme legoTheme;

    //  onCreate Function
    //
    //  Use:  Create the activity.  Sets up the toolbar.  Finds out if this a
    //          big or small screen; indicates whether we will use activities
    //          or fragments side-by-side.
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Void
    //
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

    //  onItemSelected Function Implementation
    //
    //  Use:  This is what will be done when the user selects an item from the
    //          list.  If this screen size can handle two fragments then we will
    //          begin a fragment transaction.  Otherwise we will start an
    //          activity for results.  Both will pass the items id.
    //  Parameter(s):  long:id
    //  Returns:  Void
    //
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

    //  onSaveLegoTheme Function Implementation
    //
    //  Use:  When a LegoTheme is saved or updated this function will get the data
    //          from the LegoThemeDetailFragment and get this data to the
    //          LegoSetListFragment.
    //  Parameter(s):  LegoTheme:legoTheme
    //  Returns:  Void
    //
    public void onSaveLegoTheme(LegoTheme legoTheme){
        this.legoTheme = legoTheme;
        legoThemeListFragment.savedLegoTheme(legoTheme);
    }

    //  onCreateOptionsMenu Function
    //
    //  Use:  Adding a menu item to the ActivityMenu's base menu items.  Adding
    //          the plus sign symbol to the menu so users can add a new
    //          LegoTheme.
    //  Parameter(s):  Menu:menu
    //  Returns:  boolean
    //
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, R.string.menu_item_add_theme, menu.size(), R.string.menu_item_add_theme);
        MenuItem menuItem = menu.getItem(menu.size()-1);
        menuItem.setIcon(R.drawable.ic_add_white_24dp);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menuItem.setTitleCondensed("");
        return true;
    }

    //  onOptionsItemSelected Function
    //
    //  Use:  This is giving the added menu item functionality; allowing it to
    //          activate the onItemSelected function and passing in the
    //          INSERT_NEW_THEME_ID value.
    //  Parameter(s):  MenuItem:item
    //  Returns:  boolean
    //
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

    //  onActivityResult Function
    //
    //  Use:  When on a small screen this will be used to get the new or updated
    //          LegoTheme results from the LegoThemeDetailActivity and then getting
    //          this info to the LegoThemeListFragment.
    //  Parameter(s):  int:requestCode, int:resultCode, Intent:data
    //  Returns:  Void
    //
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

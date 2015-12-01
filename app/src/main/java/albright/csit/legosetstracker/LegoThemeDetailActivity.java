/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.30.2015

  Modified By:  Kevin M. Albright
Last Modified:  12.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoThemeDetailActivity.java
      Purpose:  This activity is a wrapper for the LegoThemeDetailActivity.
                  When on Phone size screens this activity will be started.
                  Handles passing values back to the calling activity when a
                  LegoSet is inserted or updated.
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class LegoThemeDetailActivity extends ActivityMenu
    implements LegoThemeDetailFragment.OnSaveLegoThemeListener{

    ////  Fields
    ////////////////////////////////////////
    public static final String RESULTS = "LegoThemeDetailActivity_Results";

    //  onCreate Function
    //
    //  Use:  Create this activity. Set toolbar and check/get bundle and start
    //          LegoThemeDetailFragment.
    //  Parameter(s):  Bundle:savedInstanceState
    //  Returns:  Void
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lego_detail);

        //  Set my toolbar to be the toolbar.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.main_menu);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            Bundle arguments = new Bundle();
            arguments.putLong(LegoThemeDetailFragment.ARG_ITEM_ID,
                getIntent().getLongExtra(LegoThemeDetailFragment.ARG_ITEM_ID,
                                    LegoThemeDetailFragment.INSERT_NEW_THEME_ID));
            LegoThemeDetailFragment detailFragment = new LegoThemeDetailFragment();
            detailFragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                .add(R.id.lego_detail_container, detailFragment, "detailFragment")
                .commit();
        }
    }

    //  onSaveLegoTheme Function Implementation
    //
    //  Use:  Will put a LegoTheme into a bundle and will put the bundle in the
    //          resultIntent, which will be passed back to the calling activity.
    //  Parameter(s):  LegoTheme:legoTheme
    //  Returns:  Void
    //
    public void onSaveLegoTheme(LegoTheme legoTheme){
        Bundle legoThemeBundle = legoTheme.writeBundle();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULTS, legoThemeBundle);
        setResult(ActivityMenu.RESULT_OK, resultIntent);
        finish();
    }
}

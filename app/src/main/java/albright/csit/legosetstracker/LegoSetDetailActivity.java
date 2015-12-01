/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.11.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.30.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSetDetailActivity.java
      Purpose:  This activity is a wrapper for the LegoSetDetailFragment.
                  When on Phone size screens this activity will be started.
                  Handles passing values back to the calling activity when a
                  LegoSet is inserted or updated.
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class LegoSetDetailActivity extends ActivityMenu
    implements LegoSetDetailFragment.OnSaveLegoSetListener{

    ////  Fields
    ////////////////////////////////////////
    public static final String RESULTS = "LegoSetDetailActivity_Results";

    //  onCreate Function
    //
    //  Use:  Create this activity. Set toolbar and check/get bundle and start
    //          LegoSetDetailFragment.
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
            arguments.putLong(LegoSetDetailFragment.ARG_ITEM_ID,
                getIntent().getLongExtra(LegoSetDetailFragment.ARG_ITEM_ID,
                                    LegoSetDetailFragment.INSERT_NEW_SET_ID));
            LegoSetDetailFragment detailFragment = new LegoSetDetailFragment();
            detailFragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                .add(R.id.lego_detail_container, detailFragment, "detailFragment")
                .commit();
        }
    }

    //  onSaveLegoSet Function Implementation
    //
    //  Use:  Will put a LegoSet into a bundle and will put the bundle in the
    //          resultIntent, which will be passed back to the calling activity.
    //  Parameter(s):  LegoSet:legoSet
    //  Returns:  Void
    //
    public void onSaveLegoSet(LegoSet legoSet){
        Bundle legoSetBundle = legoSet.writeBundle();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULTS, legoSetBundle);
        setResult(ActivityMenu.RESULT_OK, resultIntent);
        finish();
    }
}

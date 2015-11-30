package albright.csit.legosetstracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class LegoSetDetailActivity extends ActivityMenu
    implements LegoSetDetailFragment.OnSaveLegoSetListener{

    public static final String RESULTS = "LegoSetDetailActivity_Results";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legoset_detail);

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
                .add(R.id.legoset_detail_container, detailFragment, "detailFragment")
                .commit();
        }
    }

    public void onSaveLegoSet(LegoSet legoSet){
        Bundle legoSetBundle = legoSet.writeBundle();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULTS, legoSetBundle);
        setResult(ActivityMenu.RESULT_OK, resultIntent);
        finish();
    }
}

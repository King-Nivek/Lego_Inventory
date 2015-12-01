package albright.csit.legosetstracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class LegoThemeDetailActivity extends ActivityMenu
    implements LegoThemeDetailFragment.OnSaveLegoThemeListener{

    public static final String RESULTS = "LegoThemeDetailActivity_Results";


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

    public void onSaveLegoTheme(LegoTheme legoTheme){
        Bundle legoThemeBundle = legoTheme.writeBundle();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULTS, legoThemeBundle);
        setResult(ActivityMenu.RESULT_OK, resultIntent);
        finish();
    }
}

/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.12.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.12.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSetSqlOpenHelper
      Purpose:  
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    ////  Fields
    /////////////////////////////////////
    private static final String LOG = "DbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "legoSetsTracker.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LegoTrackerDbContract.CREATE_TABLE_LEGO_THEME);
        db.execSQL(LegoTrackerDbContract.CREATE_TABLE_LEGO_SET);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  on upgrade drop older tables
        db.execSQL(LegoTrackerDbContract.DELETE_TABLE_LEGO_SET);
        db.execSQL(LegoTrackerDbContract.DELETE_TABLE_LEGO_THEME);

        //  Create new tables
        onCreate(db);
    }
}
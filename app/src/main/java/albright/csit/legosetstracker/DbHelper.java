/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.12.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.30.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSetSqlOpenHelper.java
      Purpose:  Connects to the named database.  If no database by that name
                  will create the database.  Can perform on Upgrade functions.
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    ////  Fields
    ////////////////////////////////////////
    private static final String LOG = "DbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "legoSetsTracker.db";

    ////  Constructors
    ////////////////////////////////////////
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //  onCreate Function
    //
    //  Use:  Creates the database tables.
    //  Parameter(s):  SQLiteDatabase:db
    //  Returns:  Void
    //
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LegoTrackerDbContract.CREATE_TABLE_LEGO_THEME);
        db.execSQL(LegoTrackerDbContract.CREATE_TABLE_LEGO_SET);
    }

    //  onUpgrade Function
    //
    //  Use:  Upgrades the database to a new version.
    //  Parameter(s):  SQLiteDatabase:db, int:oldVersion, int:newVersion
    //  Returns:  Void
    //
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  on upgrade drop older tables
        db.execSQL(LegoTrackerDbContract.DELETE_TABLE_LEGO_SET);
        db.execSQL(LegoTrackerDbContract.DELETE_TABLE_LEGO_THEME);

        //  Create new tables
        onCreate(db);
    }
}
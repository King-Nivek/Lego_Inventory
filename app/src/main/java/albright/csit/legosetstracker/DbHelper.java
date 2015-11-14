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
import android.provider.BaseColumns;

import java.sql.SQLException;

public class DbHelper {

    ////  Fields
    /////////////////////////////////////
    private static final String LOG = "DbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "legoSetsTracker.db";
    private DbOpenHelper _dbOpenHelper;
    private SQLiteDatabase _db;

    ////  Contract inner class
    public final class LegoTablesContract {

        public LegoTablesContract() {
        }

        public abstract class TableLegoSet implements BaseColumns {
            public static final String TABLE_NAME = "LEGO_SET";
            //      public static final String LEGOSET_AUTO_ID = "LEGOSET_AUTO_ID";
            public static final String ID = "LEGOSET_ID";
            public static final String NAME = "LEGOSET_NAME";
            public static final String THEME_ID = "LEGOTHEME_ID";
            public static final String PIECES = "LEGOSET_PIECES";
            public static final String ACQUIRED_DATE = "LEGOSET_ACQUIRED_DATE";
            public static final String QUANTITY = "LEGOSET_QUANTITY";
        }

        public abstract class TableLegoTheme implements BaseColumns {
            public static final String TABLE_NAME = "LEGO_THEME";
            //      public static final String ID = "LEGOTHEME_ID";
            public static final String NAME = "LEGOTHEME_NAME";
        }

        ////  Table create statements
        /////////////////////////////////////

        //  LEGO_SET table
        private static final String CREATE_TABLE_LEGO_SET =
            "CREATE TABLE " + TableLegoSet.TABLE_NAME + "("
                + TableLegoSet._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + TableLegoSet.ID + " TEXT UNIQUE,"
                + TableLegoSet.NAME + " TEXT,"
                + TableLegoSet.THEME_ID + " INTEGER,"
                + TableLegoSet.PIECES + " INTEGER,"
                + TableLegoSet.ACQUIRED_DATE + " TEXT,"
                + TableLegoSet.QUANTITY + " INTEGER,"
                + "FOREIGN KEY(" + TableLegoSet.THEME_ID + ") REFERENCES " + TableLegoTheme.TABLE_NAME + "(" + TableLegoTheme._ID + ")"
                + ")";

        //  LEGO_THEME table
        private static final String CREATE_TABLE_LEGO_THEME =
            "CREATE TABLE " + TableLegoTheme.TABLE_NAME + "("
                + TableLegoTheme._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + TableLegoTheme.NAME + " TEXT"
                + ")";

        private static final String DELETE_TABLE_LEGO_SET =
            "DROP TABLE IF EXISTS " + TableLegoSet.TABLE_NAME;

        private static final String DELETE_TABLE_LEGO_THEME =
            "DROP TABLE IF EXISTS " + TableLegoTheme.TABLE_NAME;

    }// End Contract inner class

    ////  Database OpenHelper inner class
    private static class DbOpenHelper extends SQLiteOpenHelper {
        public DbOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(LegoTablesContract.CREATE_TABLE_LEGO_THEME);
            db.execSQL(LegoTablesContract.CREATE_TABLE_LEGO_SET);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //  on upgrade drop older tables
            db.execSQL(LegoTablesContract.DELETE_TABLE_LEGO_SET);
            db.execSQL(LegoTablesContract.DELETE_TABLE_LEGO_THEME);

            //  Create new tables
            onCreate(db);
        }

        public void open() throws SQLException {

        }
    }// End Database OpenHelp inner class
}
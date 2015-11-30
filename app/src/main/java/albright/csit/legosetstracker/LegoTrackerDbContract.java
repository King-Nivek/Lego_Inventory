/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.14.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.14.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoTrackerDbContract
      Purpose:  
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.provider.BaseColumns;

public final class LegoTrackerDbContract{

    public LegoTrackerDbContract() {
    }

    public static abstract class TableLegoSet implements BaseColumns {
        public static final String TABLE_NAME = "LEGO_SET";
        //      public static final String LEGOSET_AUTO_ID = "LEGOSET_AUTO_ID";
        public static final String ID = "LEGOSET_ID";
        public static final String NAME = "LEGOSET_NAME";
        public static final String THEME_ID = "LEGOTHEME_ID";
        public static final String PIECES = "LEGOSET_PIECES";
        public static final String ACQUIRED_DATE = "LEGOSET_ACQUIRED_DATE";
        public static final String QUANTITY = "LEGOSET_QUANTITY";

        //  Not used in table but useful for other operations
        public static final String THEME_NAME = "LEGOTHEME_NAME";
    }

    public static abstract class TableLegoTheme implements BaseColumns {
        public static final String TABLE_NAME = "LEGO_THEME";
        //      public static final String ID = "LEGOTHEME_ID";
        public static final String NAME = "LEGOTHEME_NAME";
    }

    ////  Table create statements
    /////////////////////////////////////

    //  LEGO_SET table
    public static final String CREATE_TABLE_LEGO_SET =
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
    public static final String CREATE_TABLE_LEGO_THEME =
        "CREATE TABLE " + TableLegoTheme.TABLE_NAME + "("
            + TableLegoTheme._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
            + TableLegoTheme.NAME + " TEXT"
            + ")";


    ////  Table delete statements
    /////////////////////////////////////
    public static final String DELETE_TABLE_LEGO_SET =
        "DROP TABLE IF EXISTS " + TableLegoSet.TABLE_NAME;

    public static final String DELETE_TABLE_LEGO_THEME =
        "DROP TABLE IF EXISTS " + TableLegoTheme.TABLE_NAME;

}

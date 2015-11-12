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

  //  Logcat tag
  private static final String LOG = "DbHelper";

  //  Database Version
  private static final int DATABASE_VERSION = 1;

  //  Database Name
  private static final String DATABASE_NAME = "legoSetsTracker";

  //  Table Names
  private static final String TABLE_LEGO_SET = "LEGO_SET";
  private static final String TABLE_LEGO_THEME = "LEGO_THEME";

  //  Common Column Names
  private static final String LEGOTHEME_ID = "LEGOTHEME_ID";

  //  LEGO_SET table - column names
  private static final String LEGOSET_AUTO_ID = "LEGOSET_AUTO_ID";
  private static final String LEGOSET_ID = "LEGOSET_ID";
  private static final String LEGOSET_NAME = "LEGOSET_NAME";
  //  private static final String LEGOTHEME_ID = "LEGOTHEME_ID";
  private static final String LEGOSET_PIECES = "LEGOSET_PIECES";
  private static final String LEGOSET_ACQUIRED_DATE = "LEGOSET_ACQUIRED_DATE";
  private static final String LEGOSET_QUANTITY= "LEGOSET_QUANTITY";

  //  LEGO_THEME table - column names
  //  private static final String LEGOTHEME_ID = "LEGOTHEME_ID";
  private static final String LEGOTHEME_NAME = "LEGOTHEME_NAME";

  ////  Table create statements
  /////////////////////////////////////

  //  LEGO_SET table
  private static final String CREATE_TABLE_LEGO_SET =
      "CREATE TABLE " + TABLE_LEGO_SET + "("
          + LEGOSET_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
          + LEGOSET_ID + " TEXT UNIQUE,"
          + LEGOSET_NAME + " TEXT,"
          + LEGOTHEME_ID + " INTEGER,"
          + LEGOSET_PIECES + " INTEGER,"
          + LEGOSET_ACQUIRED_DATE + " TEXT,"
          + LEGOSET_QUANTITY + " INTEGER,"
          + "FOREIGN KEY(" + LEGOTHEME_ID + ") REFERENCES " + TABLE_LEGO_THEME + "(" + LEGOTHEME_ID + ")"
      + ")";

  //  LEGO_THEME table
  private static final String CREATE_TABLE_LEGO_THEME =
      "CREATE TABLE " + TABLE_LEGO_THEME + "("
          + LEGOTHEME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
          + LEGOTHEME_NAME + " TEXT"
      + ")";

  ////  Constructors
  /////////////////////////////////////

  public DbHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public void onCreate(SQLiteDatabase db){
    db.execSQL(CREATE_TABLE_LEGO_THEME);
    db.execSQL(CREATE_TABLE_LEGO_SET);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    //  on upgrade drop older tables
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEGO_THEME);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEGO_SET);

    //  Create new tables
    onCreate(db);
  }
}
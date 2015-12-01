/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.14.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.30.2015

   Assignment:  Lego Sets Tracker
    File Name:  DbConnection.java
      Purpose:  The connection to a database.  Performing Insert, Update,
                    Delete, and Get functions.  Also has a few querying
                    functions.

    Functions:  open():void
                close():void
                insertLegoSet(LegoSet):void
                insertLegoTheme(LegoTheme):void
                updateLegoSet(LegoSet):void
                updateLegoTheme(LegoTheme):void
                deleteLegoSet(LegoSet):void
                deleteLegoTheme(LegoTheme):void
                getLegoSet(long):LegoSet
                getLegoTheme(long):LegoTheme
                getAllLegoSets():ArrayList<LegoSet>
                getAllLegoThemes():ArrayList<LegoTheme>
                hasLegoSetId(String):boolean
                hasLegoSet(long, String):boolean
                hasLegoThemeName(String):boolean
                usesTheme(long):boolean
                numberOfLegoSets():int
                numberOfPieces():int
                quantityOfLegoSets():int
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import albright.csit.legosetstracker.LegoTrackerDbContract.TableLegoSet;
import albright.csit.legosetstracker.LegoTrackerDbContract.TableLegoTheme;

public class DbConnection {

    ////  Fields
    ////////////////////////////////////////
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    ////  Constructors
    ////////////////////////////////////////
    public DbConnection(Context context){
        dbHelper = new DbHelper(context);
    }

    //  open Function
    //
    //  Use:  Opens a writableDatabase.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    //  close Function
    //
    //  Use:  Closes the database object.
    //  Parameter(s):  Void
    //  Returns:  Void
    //
    public void close(){
        dbHelper.close();
    }

    //  insertLegoSet Function
    //
    //  Use:  Inserts a LegoSet as a new row in the database in TableLegoSet.
    //          The LegoSet object will be updated to have the autoId that is
    //          returned by the database when it inserts a new LegoSet.
    //          This will be useful for adding sets to Lists.
    //  Parameter(s):  LegoSet:legoSet
    //  Returns:  Void
    //
    public void insertLegoSet(LegoSet legoSet){
        ContentValues keyValues = new ContentValues();

        keyValues.put(TableLegoSet.ID, legoSet.getId());
        keyValues.put(TableLegoSet.NAME, legoSet.getName());
        keyValues.put(TableLegoSet.THEME_ID, legoSet.getThemeId());
        keyValues.put(TableLegoSet.PIECES, legoSet.getPieces());
        keyValues.put(TableLegoSet.ACQUIRED_DATE, legoSet.getAcquiredDate());
        keyValues.put(TableLegoSet.QUANTITY, legoSet.getQuantity());

        long newRowId;
        newRowId = database.insert(TableLegoSet.TABLE_NAME, null, keyValues);
        legoSet.setAutoId(newRowId);
    }

    //  insertLegoTheme Function
    //
    //  Use:  Inserts a LegoTheme as a new row in the database in TableLegoTheme.
    //          The LegoTheme object will be updated to have the autoId that is
    //          returned by the database when it inserts a new LegoTheme.
    //          This will be useful for adding themes to Lists.
    //  Parameter(s):  LegoTheme:legoTheme
    //  Returns:  Void
    //
    public void insertLegoTheme(LegoTheme legoTheme){
        ContentValues keyValues = new ContentValues();

        keyValues.put(TableLegoTheme.NAME, legoTheme.getName());

        long newRowId;
        newRowId = database.insert(TableLegoTheme.TABLE_NAME, null, keyValues);
        legoTheme.setAutoId(newRowId);
    }

    //  updateLegoSet Function
    //
    //  Use:  Updates a row in TableLegoSet with new values.  Will find the row
    //          that equals the given LegoSet's autoId and update that row.
    //  Parameter(s):  LegoSet:legoSet
    //  Returns:  Void
    //
    public void updateLegoSet(LegoSet legoSet){
        ContentValues keyValues = new ContentValues();
        keyValues.put(TableLegoSet.ID, legoSet.getId());
        keyValues.put(TableLegoSet.NAME, legoSet.getName());
        keyValues.put(TableLegoSet.THEME_ID, legoSet.getThemeId());
        keyValues.put(TableLegoSet.PIECES, legoSet.getPieces());
        keyValues.put(TableLegoSet.ACQUIRED_DATE, legoSet.getAcquiredDate());
        keyValues.put(TableLegoSet.QUANTITY, legoSet.getQuantity());
        String whereClause = TableLegoSet._ID + " = ?";
        String[] whereArgs = {String.valueOf(legoSet.getAutoId())};
        database.update(TableLegoSet.TABLE_NAME, keyValues, whereClause, whereArgs);
    }

    //  updateLegoTheme Function
    //
    //  Use:  Updates a row in TableLegoTheme with new values.  Will find the row
    //          that equals the given LegoTheme's autoId and update that row.
    //  Parameter(s):  LegoTheme:legoTheme
    //  Returns:  Void
    //
    public void updateLegoTheme(LegoTheme legoTheme){
        ContentValues keyValues = new ContentValues();

        keyValues.put(TableLegoTheme.NAME, legoTheme.getName());
        String whereClause = TableLegoTheme._ID + " = ?";
        String[] whereArgs = {String.valueOf(legoTheme.getAutoId())};
        database.update(TableLegoTheme.TABLE_NAME, keyValues, whereClause, whereArgs);
    }

    //  deleteLegoSet Function
    //
    //  Use:  Deletes a row in TableLegoSet.  Will find the row
    //          that equals the given LegoSet's autoId and delete that row.
    //  Parameter(s):  LegoSet:legoSet
    //  Returns:  Void
    //
    public void deleteLegoSet(LegoSet legoSet){
        String whereClause = TableLegoSet._ID + " = ?";
        String[] whereArgs = {String.valueOf(legoSet.getAutoId())};
        database.delete(TableLegoSet.TABLE_NAME, whereClause, whereArgs);
    }

    //  deleteLegoTheme Function
    //
    //  Use:  Deletes a row in TableLegoTheme.  Will find the row
    //          that equals the given LegoTheme's autoId and delete that row.
    //  Parameter(s):  LegoTheme:legoTheme
    //  Returns:  Void
    //
    public void deleteLegoTheme(LegoTheme legoTheme){
        String whereClause = TableLegoTheme._ID + "=?";
        String[] whereArgs = {String.valueOf(legoTheme.getAutoId())};
        database.delete(TableLegoTheme.TABLE_NAME, whereClause, whereArgs);
    }

    //  getAllLegoSets Function
    //
    //  Use:  Finds all entered LegoSets in TableLegoSet and returns an
    //          ArrayList of LegoSets.
    //  Parameter(s):  Void
    //  Returns:  ArrayList<LegoSet>
    //
    public ArrayList<LegoSet> getAllLegoSets(){
        String queryStatement =
            "SELECT " + "s." + TableLegoSet._ID + ", "
                      + "s." + TableLegoSet.ID + ", "
                      + "s." + TableLegoSet.THEME_ID + ", "
                      + "s." + TableLegoSet.NAME + " AS 'Set Name', "
                      + "t." + TableLegoTheme.NAME + " AS 'Theme', "
                      + "s." + TableLegoSet.PIECES + " AS '# of Pieces', "
                      + "s." + TableLegoSet.ACQUIRED_DATE + " AS 'Date Acquired', "
                      + "s." + TableLegoSet.QUANTITY + " AS 'Qty.' "
            + "FROM " + TableLegoSet.TABLE_NAME + " AS s "
              + "INNER JOIN " + TableLegoTheme.TABLE_NAME + " AS t "
                + "ON s." + TableLegoSet.THEME_ID + " = t." + TableLegoTheme._ID;


        Cursor c = database.rawQuery(queryStatement, null);
        ArrayList<LegoSet> legoSetList = new ArrayList<LegoSet>();

        if(c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                int i = 0;
                LegoSet legoSet = new LegoSet();

                legoSet.setAutoId(c.getLong(i++));
                legoSet.setId(c.getString(i++));
                legoSet.setThemeId(c.getLong(i++));
                legoSet.setName(c.getString(i++));
                legoSet.setThemeName(c.getString(i++));
                legoSet.setPieces(c.getInt(i++));
                legoSet.setAcquiredDate(c.getString(i++));
                legoSet.setQuantity(c.getInt(i));

                legoSetList.add(legoSet);
            }
            c.close();
        }
        return legoSetList;

    }

    //  getAllLegoThemes Function
    //
    //  Use:  Finds all entered LegoThemes in TableLegoSet and returns an
    //          ArrayList of LegoThemes.
    //  Parameter(s):  Void
    //  Returns:  ArrayList<LegoTheme>
    //
    public ArrayList<LegoTheme> getAllLegoThemes(){
        String queryStatement =
            "SELECT " + TableLegoTheme._ID + ", "
                      + TableLegoTheme.NAME + " "
              + "FROM " + TableLegoTheme.TABLE_NAME;

        Cursor c = database.rawQuery(queryStatement, null);
        ArrayList<LegoTheme> legoThemesList = new ArrayList<LegoTheme>();

        if(c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                int i = 0;
                LegoTheme legoTheme = new LegoTheme(c.getLong(i++), c.getString(i));
                legoThemesList.add(legoTheme);
            }
            c.close();
        }
        return legoThemesList;
    }

    //  getLegoSet Function
    //
    //  Use:  Finds the legoSet associated with given id and returns the found
    //          legoSet.
    //  Parameter(s):  long:id
    //  Returns:  LegoSet
    //
    public LegoSet getLegoSet(long id){
        LegoSet legoSet;
        String queryStatement =
            "SELECT " + "s." + TableLegoSet._ID + ", "
                      + "s." + TableLegoSet.ID + ", "
                      + "s." + TableLegoSet.THEME_ID + ", "
                      + "s." + TableLegoSet.NAME + " AS 'Set Name', "
                      + "t." + TableLegoTheme.NAME + " AS 'Theme', "
                      + "s." + TableLegoSet.PIECES + " AS '# of Pieces', "
                      + "s." + TableLegoSet.ACQUIRED_DATE + " AS 'Date Acquired', "
                      + "s." + TableLegoSet.QUANTITY + " AS 'Qty.' "
            + "FROM " + TableLegoSet.TABLE_NAME + " AS s "
              + "INNER JOIN " + TableLegoTheme.TABLE_NAME + " AS t "
                + "ON s." + TableLegoSet.THEME_ID + " = t." + TableLegoTheme._ID + " "
              + "WHERE s." + TableLegoSet._ID + " = " + id;

        Cursor c = database.rawQuery(queryStatement, null);
        if(c != null){
            c.moveToFirst();
            int i = 0;
            legoSet = new LegoSet();
            legoSet.setAutoId(c.getLong(i++));
            legoSet.setId(c.getString(i++));
            legoSet.setThemeId(c.getLong(i++));
            legoSet.setName(c.getString(i++));
            legoSet.setThemeName(c.getString(i++));
            legoSet.setPieces(c.getInt(i++));
            legoSet.setAcquiredDate(c.getString(i++));
            legoSet.setQuantity(c.getInt(i));

            c.close();
        }else {
            legoSet = null;
        }
        return legoSet;
    }

    //  getLegoTheme Function
    //
    //  Use:  Finds the legoTheme associated with given id and returns the found
    //          legoTheme.
    //  Parameter(s):  long:id
    //  Returns:  LegoTheme
    //
    public LegoTheme getLegoTheme(long id){
        LegoTheme legoTheme;
        String queryStatement =
            "SELECT " + TableLegoTheme._ID + ", "
                      + TableLegoTheme.NAME + " "
              + "FROM " + TableLegoTheme.TABLE_NAME + " "
              + "WHERE " + TableLegoTheme._ID + " = " + id;

        Cursor c = database.rawQuery(queryStatement, null);
        if(c.getCount() > 0) {
            c.moveToFirst();
            int i = 0;
            legoTheme = new LegoTheme(c.getLong(i++), c.getString(i));
            c.close();
        }else {
            legoTheme = null;
        }
        return legoTheme;
    }

    //  hasLegoSetId Function
    //
    //  Use:  Looks to see if the TableLegoSet has a the setId equal to the
    //          given id.  If a row is found with this id then TRUE is return.
    //  Parameter(s):  String:id
    //  Returns:  boolean
    //
    public boolean hasLegoSetId(String id){
        String query =
            "SELECT "
                + TableLegoSet._ID + ", "
                + TableLegoSet.ID + " "
            + "FROM " + TableLegoSet.TABLE_NAME + " "
            + "WHERE " + TableLegoSet.ID + " = '" + id + "'";
        Cursor c = database.rawQuery(query, null);
        boolean hasId = (c.getCount() > 0);
        if(hasId){c.close();}
        return hasId;
    }

    //  hasLegoSet Function
    //
    //  Use:  Looks to see if the TableLegoSet has a the setId and setAutoId
    //          equal to the given autoId and id   If a row is found that has
    //          both then TRUE is return.
    //  Parameter(s):  long:autoId, String:id
    //  Returns:  boolean
    //
    public boolean hasLegoSet(long autoId, String id){
        String query =
            "SELECT "
                + TableLegoSet._ID + ", "
                + TableLegoSet.ID + " "
            + "FROM " + TableLegoSet.TABLE_NAME + " "
            + "WHERE " + TableLegoSet.ID + " = '" + id + "' AND " + TableLegoSet._ID + " = " + autoId;
        Cursor c = database.rawQuery(query, null);
        boolean hasId = (c.getCount() == 1);
        if(hasId){c.close();}
        return hasId;
    }

    //  hasLegoThemeName Function
    //
    //  Use:  Looks to see if the TableLegoTheme has a the themeName equal to
    //          the given name.  If a row is found with this name then TRUE is
    //          return.
    //  Parameter(s):  String:name
    //  Returns:  boolean
    //
    public boolean hasLegoThemeName(String name){
        String query =
            "SELECT "
                + TableLegoTheme._ID + ", "
                + TableLegoTheme.NAME + " "
            + "FROM " + TableLegoTheme.TABLE_NAME + " "
            + "WHERE " + TableLegoTheme.NAME + " = '" + name + "'";
        Cursor c = database.rawQuery(query, null);
        boolean hasId = (c.getCount() > 0);
        if(hasId){c.close();}
        return hasId;
    }

    //  usesTheme Function
    //
    //  Use:  Looks to see if any LegoSets from TableLegoSet are using the given
    //          themeId.  If a row is found then TRUE is returned.
    //  Parameter(s):  long:themeId
    //  Returns:  boolean
    //
    public boolean usesTheme(long themeId){
        String query =
            "SELECT "
                + TableLegoSet._ID + ", "
                + TableLegoSet.THEME_ID + " "
            + "FROM " + TableLegoSet.TABLE_NAME + " "
            + "WHERE " + TableLegoSet.THEME_ID + " = " + themeId;
        Cursor c = database.rawQuery(query, null);
        boolean usesThemeId = (c.getCount() > 0);
        if(usesThemeId){c.close();}
        return usesThemeId;
    }

    //  numberOfLegoSets Function
    //
    //  Use:  Counts the rows in TableLegoSet and returns the count total.
    //  Parameter(s):  Void
    //  Returns:  int
    //
    public int numberOfLegoSets(){
        String query =
            "SELECT COUNT(" + TableLegoSet._ID + ") "
            + "FROM " + TableLegoSet.TABLE_NAME;
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        int count =  c.getInt(0);
        c.close();
        return count;
    }

    //  numberOfPieces Function
    //
    //  Use:  Sums up the number of pieces in TableLegoSet.Pieces and returns
    //          the sum total of pieces.
    //  Parameter(s):  Void
    //  Returns:  int
    //
    public int numberOfPieces(){
        String query =
            "SELECT SUM(" + TableLegoSet.PIECES + ") "
            + "FROM " + TableLegoSet.TABLE_NAME;
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        int count = c.getInt(0);
        c.close();
        return count;
    }

    //  quantityOfLegoSets Function
    //
    //  Use:  Sums up the number of sets in TableLegoSet.QUANTITY and returns
    //          the sum total of sets.
    //  Parameter(s):  Void
    //  Returns:  int
    //
    public int quantityOfLegoSets(){
        String query =
            "SELECT SUM(" + TableLegoSet.QUANTITY + ") "
                + "FROM " + TableLegoSet.TABLE_NAME;
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        int count = c.getInt(0);
        c.close();
        return count;
    }
}

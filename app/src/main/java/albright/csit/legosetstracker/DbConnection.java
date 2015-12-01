/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.14.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.14.2015

   Assignment:  Lego Sets Tracker
    File Name:  DbConnection
      Purpose:  
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import albright.csit.legosetstracker.LegoTrackerDbContract.TableLegoSet;
import albright.csit.legosetstracker.LegoTrackerDbContract.TableLegoTheme;

public class DbConnection {

    private SQLiteDatabase database;
    private DbHelper dbHelper;


    public DbConnection(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertLegoSet(LegoSet legoSet){
        //TODO: I think this is done.
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

    public void insertLegoTheme(LegoTheme legoTheme){
        //TODO: I think this is done.
        ContentValues keyValues = new ContentValues();

        keyValues.put(TableLegoTheme.NAME, legoTheme.getName());

        long newRowId;
        newRowId = database.insert(TableLegoTheme.TABLE_NAME, null, keyValues);
        legoTheme.setAutoId(newRowId);
    }

    public void updateLegoSet(LegoSet legoSet){
        //TODO
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

    public void updateLegoTheme(LegoTheme legoTheme){
        //TODO
        ContentValues keyValues = new ContentValues();

        keyValues.put(TableLegoTheme.NAME, legoTheme.getName());
        String whereClause = TableLegoTheme._ID + " = ?";
        String[] whereArgs = {String.valueOf(legoTheme.getAutoId())};
        database.update(TableLegoTheme.TABLE_NAME, keyValues, whereClause, whereArgs);
    }

    public void deleteLegoSet(LegoSet legoSet){
        //TODO: I think this is done.
        String whereClause = TableLegoSet._ID + " = ?";
        String[] whereArgs = {String.valueOf(legoSet.getAutoId())};
        database.delete(TableLegoSet.TABLE_NAME, whereClause, whereArgs);
    }

    public void deleteLegoTheme(LegoTheme legoTheme){
        //TODO: I think this is done.
        String whereClause = TableLegoTheme._ID + "=?";
        String[] whereArgs = {String.valueOf(legoTheme.getAutoId())};
        database.delete(TableLegoTheme.TABLE_NAME, whereClause, whereArgs);
    }

    public ArrayList<LegoSet> getAllLegoSets(){
        //TODO: I think this is done.

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

    public ArrayList<LegoTheme> getAllLegoThemes(){
        //TODO: I think this is done.
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

    public LegoSet getLegoSet(long id){
        //TODO: I think this is done.
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

    public LegoTheme getLegoTheme(long id){
        //TODO: I think this is done.
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

    public boolean hasLegoSet(long _id, String id){
        String query =
            "SELECT "
                + TableLegoSet._ID + ", "
                + TableLegoSet.ID + " "
            + "FROM " + TableLegoSet.TABLE_NAME + " "
            + "WHERE " + TableLegoSet.ID + " = '" + id + "' AND " + TableLegoSet._ID + " = " + _id;
        Cursor c = database.rawQuery(query, null);
        boolean hasId = (c.getCount() == 1);
        if(hasId){c.close();}
        return hasId;
    }


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
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    public void buildQuery(String table, ContentValues values, String where, String yo){

    }


    public String buildQueryString(int type, long id, String sortOn,
                                   boolean isFilter, boolean isDesc){
        String select = "SELECT ",
               columns = buildColumns(type),
               from = buildFrom(type),
               where = buildWhere(type, id, isFilter),
               orderBy = buildSort(sortOn, isDesc);

        String query = select + columns + from;
        if(where != null){
            query += where;
        }
        if(orderBy != null){
            query += orderBy;
        }

        return query;
    }

    public String buildQueryStringFilterTheme(long themeId){
        String select = "SELECT ";
        String columns  = "s." + TableLegoSet._ID + ", "
                        + "s." + TableLegoSet.ID + ", "
                        + "s." + TableLegoSet.THEME_ID + ", "
                        + "s." + TableLegoSet.NAME + " AS 'Set Name', "
                        + "t." + TableLegoTheme.NAME + " AS 'Theme' "
                        + "s." + TableLegoSet.PIECES + " AS '# of Pieces, "
                        + "s." + TableLegoSet.ACQUIRED_DATE + " AS 'Date Acquired', "
                        + "s." + TableLegoSet.QUANTITY + " AS 'Qty.' ";

        String from = "FROM " + TableLegoSet.TABLE_NAME + " AS s ";
        String innerJoin = "INNER JOIN " + TableLegoTheme.TABLE_NAME + " AS t ";
        String on = "ON s." + TableLegoSet.THEME_ID + " = " + TableLegoTheme._ID + " ";
        String where = "WHERE s." + TableLegoSet.THEME_ID + " = " + themeId;

        return select + columns + from + innerJoin + on + where;
    }

    public String buildColumns(int type){
        String columns;
        switch (type){
            case R.string.type_set:
                columns = "s." + TableLegoSet._ID + ", "
                        + "s." + TableLegoSet.ID + ", "
                        + "s." + TableLegoSet.THEME_ID + ", "
                        + "s." + TableLegoSet.NAME + " AS 'Set Name', "
                        + "t." + TableLegoTheme.NAME + " AS 'Theme' "
                        + "s." + TableLegoSet.PIECES + " AS '# of Pieces, "
                        + "s." + TableLegoSet.ACQUIRED_DATE + " AS 'Date Acquired', "
                        + "s." + TableLegoSet.QUANTITY + " AS 'Qty.' ";
                break;
            case R.string.type_theme:
                columns = TableLegoTheme._ID + ", "
                        + TableLegoTheme.NAME + " ";
                break;
            default:
                columns = null;
        }
        return columns;
    }

    public String buildFrom(int type){
        String from;
        switch (type){
            case R.string.type_set:
                from = "FROM " + TableLegoSet.TABLE_NAME + " AS s "
                        + "INNER JOIN " + TableLegoTheme.TABLE_NAME + " AS t "
                        + "ON s." + TableLegoSet.THEME_ID + " = " + TableLegoTheme._ID + " ";
                break;
            case R.string.type_theme:
                from = "FROM " + TableLegoTheme.TABLE_NAME + " ";
                break;
            default:
                from = null;
        }
        return from;
    }

    public String buildWhere(int type, long id, boolean isFilter){
        String where;
        switch (type){
            case R.string.type_set:
                if(isFilter) {
                    where = "WHERE s." + TableLegoSet.THEME_ID + " = " + id;
                }else {
                    where = "WHERE s." + TableLegoSet._ID + " = " + id;
                }
                break;
            case R.string.type_theme:
                where = "WHERE " + TableLegoTheme._ID + " = " + id;
                break;
            default:
                where = null;
        }
        return where;
    }

    public String buildSort(String sortOn, boolean isDesc){
        String orderBy = null;
        if(isDesc) {
            orderBy = "ORDER BY " + sortOn + " DESC ";
        }else {
            orderBy = "ORDER BY " + sortOn + " ASC ";
        }
        return orderBy;
    }
}

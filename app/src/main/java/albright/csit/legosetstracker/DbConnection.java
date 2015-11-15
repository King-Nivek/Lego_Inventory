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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public void insertLegoSet(){
        //TODO
    }

    public void insertLegoTheme(){
        //TODO
    }

    public void updateLegoSet(){
        //TODO
    }

    public void updateLegoTheme(){
        //TODO
    }

    public void deleteLegoSet(){
        //TODO
    }

    public void deleteLegoTheme(){
        //TODO
    }

    public ArrayList<LegoSet> getAllLegoSets(){
        //TODO
    }

    public ArrayList<LegoTheme> getAllLegoThemes(){
        //TODO
    }

    public LegoSet getLegoSet(){
        //TODO
    }

    public LegoTheme getLegoTheme(){
        //TODO
    }


}

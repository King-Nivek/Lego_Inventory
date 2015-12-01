/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.12.2015

  Modified By:  Kevin M. Albright
Last Modified:  01.01.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoTheme
      Purpose:  A LegoTheme object and its fields.  Will be used to store a
                  lego theme.
______________________________________________________________________________*/
package albright.csit.legosetstracker;

import android.os.Bundle;

public class LegoTheme {
    ////  Fields
    /////////////////////////////////////
    private long autoId;
    private String name;

    ////  Setters
    /////////////////////////////////////
    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    ////  Getters
    /////////////////////////////////////
    public long getAutoId() {
        return autoId;
    }

    public String getName() {
        return name;
    }

    ////  Constructors
    ////////////////////////////////////////
    public LegoTheme() {
    }

    public LegoTheme(long themeId) {
        this.autoId = themeId;
    }

    public LegoTheme(long themeId, String themeName) {
        this.autoId = themeId;
        this.name = themeName;
    }

    //  writeBundle Function
    //
    //  Use:  Creates a bundle that is filled with this LegoTheme's values.
    //  Parameter(s):  Void
    //  Returns:  Bundle
    //
    public Bundle writeBundle(){
        Bundle b = new Bundle();
        b.putLong(LegoTrackerDbContract.TableLegoTheme._ID, this.autoId);
        b.putString(LegoTrackerDbContract.TableLegoTheme.NAME, this.name);
        return b;
    }

    //  readBundle Function
    //
    //  Use:  Creates a LegoTheme that is filled with the given Bundle's values.
    //  Parameter(s):  Bundle:b
    //  Returns:  LegoTheme
    //
    public static LegoTheme readBundle(Bundle b){
        LegoTheme legoTheme = new LegoTheme();
        legoTheme.setAutoId(b.getLong(LegoTrackerDbContract.TableLegoTheme._ID));
        legoTheme.setName(b.getString(LegoTrackerDbContract.TableLegoTheme.NAME));
        return legoTheme;
    }

    //  copy Function
    //
    //  Use:  Creates a new LegoTheme with the values of the given LegoTheme.
    //  Parameter(s):  LegoTheme:legoTheme
    //  Returns:  LegoTheme
    //
    public static LegoTheme copy(LegoTheme legoTheme){
        return new LegoTheme(legoTheme.getAutoId(), legoTheme.getName());
    }

    //  toString Function
    //
    //  Use:  Returns the Theme Name.
    //  Parameter(s):  Void
    //  Returns:  String
    //
    @Override
    public String toString() {
        return this.getName();
    }
}

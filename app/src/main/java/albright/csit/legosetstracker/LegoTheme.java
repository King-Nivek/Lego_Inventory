/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.12.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.12.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoTheme
      Purpose:  
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
    /////////////////////////////////////
    public LegoTheme() {
    }

    public LegoTheme(long themeId) {
        this.autoId = themeId;
    }

    public LegoTheme(long themeId, String themeName) {
        this.autoId = themeId;
        this.name = themeName;
    }

    public Bundle writeBundle(){
        Bundle b = new Bundle();
        b.putLong(LegoTrackerDbContract.TableLegoTheme._ID, this.autoId);
        b.putString(LegoTrackerDbContract.TableLegoTheme.NAME, this.name);
        return b;
    }

    public static LegoTheme readBundle(Bundle b){
        LegoTheme legoTheme = new LegoTheme();
        legoTheme.setAutoId(b.getLong(LegoTrackerDbContract.TableLegoTheme._ID));
        legoTheme.setName(b.getString(LegoTrackerDbContract.TableLegoTheme.NAME));
        return legoTheme;
    }

    public boolean equals(LegoTheme that){
        return ((this.autoId == that.autoId)
            && (this.name.contentEquals(that.name)));
    }
    public static LegoTheme copy(LegoTheme legoTheme){
        return new LegoTheme(legoTheme.getAutoId(), legoTheme.getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

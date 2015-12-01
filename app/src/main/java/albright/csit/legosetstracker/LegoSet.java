/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.11.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.30.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSet.java
      Purpose:  A LegoSet object and its fields.  Will be used to store a
                  lego set.
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.os.Bundle;

public class LegoSet {
    ////  Fields
    ////////////////////////////////////////
    private long autoId;
    private String id;
    private String name;
    private long themeId;
    private String themeName;
    private int pieces;
    private String acquiredDate;
    private int quantity;

    ////  Setters
    ////////////////////////////////////////
    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public void setAcquiredDate(String acquiredDate) {
        this.acquiredDate = acquiredDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    ////  Getters
    ////////////////////////////////////////
    public long getAutoId() {
        return autoId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getThemeId() {
        return themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public int getPieces() {
        return pieces;
    }

    public String getAcquiredDate() {
        return acquiredDate;
    }

    public int getQuantity() {
        return quantity;
    }

    ////  Constructors
    ////////////////////////////////////////
    public LegoSet() {
    }

    public LegoSet(long autoId) {
        this.autoId = autoId;
    }

    public LegoSet(long autoId, String legoSetId, String legoSetName,
                   long legoThemeId, String legoThemeName, int legoSetPieces,
                   String legoSetAcquiredDate, int legoSetQuantity) {

        this.autoId = autoId;
        this.id = legoSetId;
        this.name = legoSetName;
        this.themeId = legoThemeId;
        this.themeName = legoThemeName;
        this.pieces = legoSetPieces;
        this.acquiredDate = legoSetAcquiredDate;
        this.quantity = legoSetQuantity;
    }

    //  writeBundle Function
    //
    //  Use:  Creates a bundle that is filled with this LegoSet's values.
    //  Parameter(s):  Void
    //  Returns:  Bundle
    //
    public Bundle writeBundle(){
        Bundle b = new Bundle();
        b.putLong(LegoTrackerDbContract.TableLegoSet._ID, this.autoId);
        b.putString(LegoTrackerDbContract.TableLegoSet.ID, this.id);
        b.putString(LegoTrackerDbContract.TableLegoSet.NAME, this.name);
        b.putLong(LegoTrackerDbContract.TableLegoSet.THEME_ID, this.themeId);
        b.putString(LegoTrackerDbContract.TableLegoSet.THEME_NAME, this.themeName);
        b.putString(LegoTrackerDbContract.TableLegoSet.ACQUIRED_DATE, this.acquiredDate);
        b.putInt(LegoTrackerDbContract.TableLegoSet.PIECES, this.pieces);
        b.putInt(LegoTrackerDbContract.TableLegoSet.QUANTITY, this.quantity);
        return b;
    }

    //  readBundle Function
    //
    //  Use:  Creates a LegoSet that is filled with the given Bundle's values.
    //  Parameter(s):  Bundle:b
    //  Returns:  LegoSet
    //
    public static LegoSet readBundle(Bundle b){
        LegoSet legoSet = new LegoSet();
        legoSet.setAutoId(b.getLong(LegoTrackerDbContract.TableLegoSet._ID));
        legoSet.setId(b.getString(LegoTrackerDbContract.TableLegoSet.ID));
        legoSet.setName(b.getString(LegoTrackerDbContract.TableLegoSet.NAME));
        legoSet.setThemeId(b.getLong(LegoTrackerDbContract.TableLegoSet.THEME_ID));
        legoSet.setThemeName(b.getString(LegoTrackerDbContract.TableLegoSet.THEME_NAME));
        legoSet.setAcquiredDate(b.getString(LegoTrackerDbContract.TableLegoSet.ACQUIRED_DATE));
        legoSet.setPieces(b.getInt(LegoTrackerDbContract.TableLegoSet.PIECES));
        legoSet.setQuantity(b.getInt(LegoTrackerDbContract.TableLegoSet.QUANTITY));
        return legoSet;
    }

    //  copy Function
    //
    //  Use:  Creates a new LegoSet with the values of the given LegoSet.
    //  Parameter(s):  LegoSet:legoSet
    //  Returns:  LegoSet
    //
    public static LegoSet copy(LegoSet legoSet){
        return new LegoSet(legoSet.getAutoId(), legoSet.getId(), legoSet.getName(),
            legoSet.getThemeId(), legoSet.getThemeName(), legoSet.getPieces(),
            legoSet.getAcquiredDate(), legoSet.getQuantity());
    }
}

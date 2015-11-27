/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.11.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.11.2015

   Assignment:  Lego Sets Tracker
    File Name:  LegoSet.java
      Purpose:  
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.os.Bundle;

public class LegoSet {
    ////  Fields
    /////////////////////////////////////
    private long autoId;
    private String id;
    private String name;
    private long themeId;
    private String themeName;
    private int pieces;
    private String acquiredDate;
    private int quantity;

    ////  Setters
    /////////////////////////////////////
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
    /////////////////////////////////////
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
    /////////////////////////////////////
    public LegoSet() {
    }

    public LegoSet(int autoId) {
        this.autoId = autoId;
    }

    public LegoSet(int autoId, String legoSetId, String legoSetName,
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

    public boolean equalsAll(LegoSet thatObj){
        boolean returnValue = false;
        if(this.autoId == thatObj.getAutoId() && equals(thatObj)){
            returnValue = true;
        }
        return returnValue;
    }

    public boolean equals(LegoSet thatObj){
        boolean isEqual = false;
        final int FIELD_NUMBER = 7;
        int areTrue = 0;
        if(this.id.compareTo(thatObj.getId()) == 0){
            areTrue++;
        }
        if(this.name.compareTo(thatObj.getName()) == 0){
            areTrue++;
        }
        if(this.themeId == thatObj.getThemeId()){
            areTrue++;
        }
        if(this.acquiredDate.compareTo(thatObj.getAcquiredDate()) == 0){
            areTrue++;
        }
        if(this.pieces == thatObj.getPieces()){
            areTrue++;
        }
        if(this.quantity == thatObj.getQuantity()){
            areTrue++;
        }
        if(areTrue == FIELD_NUMBER){
            isEqual = true;
        }
        return isEqual;
    }

    public boolean equalsAutoId(long thatSetAutoId){
        return this.autoId == thatSetAutoId;
    }
    public boolean equalsId(String thatSetId){
        return this.id.compareTo(thatSetId) == 0;
    }
    public boolean equalsName(String thatSetName){
        return this.name.compareTo(thatSetName) == 0;
    }
    public boolean equalsThemeId(long thatThemeId){
        return this.themeId == thatThemeId;
    }
    public boolean equalsAcquiredDate(String thatAcquiredDate){
        return this.acquiredDate.compareTo(thatAcquiredDate) == 0;
    }
    public boolean equalsPieces(int thatSetPieces){
        return this.pieces == thatSetPieces;
    }
    public boolean equalsQuantity(int thatSetQuantity){
        return this.quantity == thatSetQuantity;
    }
}

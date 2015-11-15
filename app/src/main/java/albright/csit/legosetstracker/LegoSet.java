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

public class LegoSet {
  ////  Fields
  /////////////////////////////////////
  private long autoId;
  private String legoSetId;
  private String legoSetName;
  private int legoThemeId;
  private String legoThemeName;
  private int legoSetPieces;
  private String legoSetAcquiredDate;
  private int legoSetQuantity;

  ////  Setters
  /////////////////////////////////////
  public void setAutoId(long autoId) {
    this.autoId = autoId;
  }

  public void setLegoSetId(String legoSetId) {
    this.legoSetId = legoSetId;
  }

  public void setLegoSetName(String legoSetName) {
    this.legoSetName = legoSetName;
  }

  public void setLegoThemeId(int legoThemeId) {
    this.legoThemeId = legoThemeId;
  }

  public void setLegoThemeName(String legoThemeName) {
    this.legoThemeName = legoThemeName;
  }

  public void setLegoSetPieces(int legoSetPieces) {
    this.legoSetPieces = legoSetPieces;
  }

  public void setLegoSetAcquiredDate(String legoSetAcquiredDate) {
    this.legoSetAcquiredDate = legoSetAcquiredDate;
  }

  public void setLegoSetQuantity(int legoSetQuantity) {
    this.legoSetQuantity = legoSetQuantity;
  }

  ////  Getters
  /////////////////////////////////////
  public long getAutoId() {
    return autoId;
  }

  public String getLegoSetId() {
    return legoSetId;
  }

  public String getLegoSetName() {
    return legoSetName;
  }

  public int getLegoThemeId() {
    return legoThemeId;
  }

  public String getLegoThemeName() {
    return legoThemeName;
  }

  public int getLegoSetPieces() {
    return legoSetPieces;
  }

  public String getLegoSetAcquiredDate() {
    return legoSetAcquiredDate;
  }

  public int getLegoSetQuantity() {
    return legoSetQuantity;
  }

  ////  Constructors
  /////////////////////////////////////
  public LegoSet() {
  }

  public LegoSet(int autoId) {
    this.autoId = autoId;
  }

  public LegoSet(int autoId, String legoSetId, String legoSetName,
                 int legoThemeId, String legoThemeName, int legoSetPieces,
                 String legoSetAcquiredDate, int legoSetQuantity) {

    this.autoId = autoId;
    this.legoSetId = legoSetId;
    this.legoSetName = legoSetName;
    this.legoThemeId = legoThemeId;
    this.legoThemeName = legoThemeName;
    this.legoSetPieces = legoSetPieces;
    this.legoSetAcquiredDate = legoSetAcquiredDate;
    this.legoSetQuantity = legoSetQuantity;
  }
}

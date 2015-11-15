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
  private String id;
  private String name;
  private int themeId;
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

  public void setThemeId(int themeId) {
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

  public int getThemeId() {
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
                 int legoThemeId, String legoThemeName, int legoSetPieces,
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
}

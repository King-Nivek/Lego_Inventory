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
}

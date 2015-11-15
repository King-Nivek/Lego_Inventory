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
  private long themeId;
  private String themeName;

  ////  Setters
  /////////////////////////////////////
  public void setThemeId(int themeId) {
    this.themeId = themeId;
  }

  public void setThemeName(String themeName) {
    this.themeName = themeName;
  }

  ////  Getters
  /////////////////////////////////////
  public long getThemeId() {
    return themeId;
  }

  public String getThemeName() {
    return themeName;
  }

  ////  Constructors
  /////////////////////////////////////
  public LegoTheme() {
  }

  public LegoTheme(int themeId) {
    this.themeId = themeId;
  }

  public LegoTheme(int themeId, String themeName) {
    this.themeId = themeId;
    this.themeName = themeName;
  }
}

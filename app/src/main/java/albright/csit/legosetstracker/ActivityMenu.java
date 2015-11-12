/*______________________________________________________________________________
   Created By:  Kevin M. Albright
Creation Date:  11.11.2015

  Modified By:  Kevin M. Albright
Last Modified:  11.11.2015

   Assignment:  Lego Sets Tracker
    File Name:  ActivityMenu
      Purpose:  
______________________________________________________________________________*/

package albright.csit.legosetstracker;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActivityMenu extends AppCompatActivity{

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    boolean isSelected = false;

    switch (item.getItemId()){

      case R.id.menuItem_sets:

        break;

      case R.id.menuItem_themes:

        break;

      case R.id.menuItem_reports:

        break;

      default:
        isSelected = super.onOptionsItemSelected(item);
    }

    return isSelected;
  }
}

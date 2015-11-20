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
import android.widget.Toast;

public class ActivityMenu extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isSelected = false;
        Toast toast;

        switch (item.getItemId()) {
            case R.id.menuItem_sets:
                toast = Toast.makeText(this, getText(R.string.menu_item_sets), Toast.LENGTH_LONG);
                toast.show();
                break;

            case R.id.menuItem_themes:
                toast = Toast.makeText(this, getText(R.string.menu_item_themes), Toast.LENGTH_LONG);
                toast.show();
                break;

            case R.id.menuItem_reports:
                toast = Toast.makeText(this, getText(R.string.menu_item_reports), Toast.LENGTH_LONG);
                toast.show();
                break;

            default:
                isSelected = super.onOptionsItemSelected(item);
        }

        return isSelected;
    }
}

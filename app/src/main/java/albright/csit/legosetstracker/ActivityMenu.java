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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
                Intent intentSet = new Intent(this, LegoSetListActivity.class);
                startActivity(intentSet);
                break;

            case R.id.menuItem_themes:
                Intent intentTheme = new Intent(this, LegoThemeListActivity.class);
                startActivity(intentTheme);
                break;

            case R.id.menuItem_reports:
                try{
                    DbConnection db = new DbConnection(this);
                    db.open();
                    DecimalFormat format = new DecimalFormat("###,###,##0");
                    int numberOfSets, numberOfPieces, quantityOfSets;
                    numberOfSets = db.numberOfLegoSets();
                    numberOfPieces = db.numberOfPieces();
                    quantityOfSets = db.quantityOfLegoSets();

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Sets by the Numbers:")
                        .setMessage("Total Unique Sets: " + format.format(numberOfSets) + "  \n"
                                  + "Total Set Quantity: " + format.format(quantityOfSets) + "  \n"
                                  + "Total Pieces: " + format.format(numberOfPieces) + "  \n")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                    db.close();

                }catch(SQLException e){
                    Log.d("Reports Menu", "Open Database failed.");
                }
                break;

            default:
                isSelected = super.onOptionsItemSelected(item);
        }

        return isSelected;
    }
}

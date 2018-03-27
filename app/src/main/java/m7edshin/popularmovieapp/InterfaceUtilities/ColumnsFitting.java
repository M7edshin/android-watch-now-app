package m7edshin.popularmovieapp.InterfaceUtilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Mohamed Shahin on 02/03/2018.
 * Implementation for Auto fit columns on screen size (RecyclerView with GridView)
 */

public class ColumnsFitting {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 120);
    }

}

package th.ac.bodin.ppnt.englock.stats;

import android.content.Context;
import android.content.SharedPreferences;

public class DashboardStats {

    public DashboardStats(Context context) {

        SharedPreferences shared = context.getSharedPreferences("Englock Dashboard",
                Context.MODE_PRIVATE);
        boolean isDBcreated = shared.getBoolean("isDBcreated", false);

        if(!isDBcreated) createDB(shared);
    }

    private void createDB(SharedPreferences shared) {
        SharedPreferences.Editor editor = shared.edit();

        editor.putString("Recent0", " ");
        editor.putString("Recent1", " ");
        editor.putString("Recent2", " ");

        editor.putLong("easyCount", 0);
        editor.putLong("normalCount", 0);
        editor.putLong("hardCount", 0);

        editor.putLong("easyNice", 0);
        editor.putLong("normalNice", 0);
        editor.putLong("hardNice", 0);

        editor.putBoolean("isDBcreated", true);

        editor.commit();
    }

}

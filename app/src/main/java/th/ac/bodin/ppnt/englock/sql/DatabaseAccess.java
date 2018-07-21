package th.ac.bodin.ppnt.englock.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    public SQLiteDatabase database;
    private static DatabaseAccess instance;
    public SQLiteQueryBuilder queryBuilder;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context, int i) {
        if (instance == null) {
            switch (i) {
                case 0:
                    instance = new DatabaseAccess(context);
                    break;
                case 1:
                    instance = new DatabaseAccess(context);
                    break;
                case 2:
                    instance = new DatabaseAccess(context);
                    break;
                case 3:
                    instance = new DatabaseAccess(context);
                    break;
                case 4:
                    instance = new DatabaseAccess(context);
                    break;
            }
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
        this.queryBuilder = new SQLiteQueryBuilder();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public int getNumberOfData (int i) {
        int n = 30;
        /*switch (i) {
            case 0:
                n = 30;
                break;
            case 1:
                n = 30;
                break;
        }*/
        return n;
    }

    public String getDataName (int i) {
        String name = "school";
        switch (i) {
            case 0:
                name = "school";
                break;
            case 1:
                name = "clothes";
                break;
            case 2:
                name = "wom";
                break;
            case 3:
                name = "etiquette";
                break;
            case 4:
                name = "landforms";
                break;
        }
        return name;
    }
}

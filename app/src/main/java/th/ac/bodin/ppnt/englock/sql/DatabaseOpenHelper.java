package th.ac.bodin.ppnt.englock.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "Vocabtest.db";
    private static final int DATABASE_VERSION = 5;

    private String sqlTables;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void setTable (int i) {
        this.sqlTables = "school";
        switch (i) {
            case 0:
                this.sqlTables = "school";
                break;
            case 1:
                this.sqlTables = "clothes";
                break;
            case 2:
                this.sqlTables = "wom";
                break;
            case 3:
                this.sqlTables = "etiquette";
                break;
            case 4:
                this.sqlTables = "landforms";
                break;
            case 5:
                this.sqlTables = "fruits";
                break;
            case 6:
                this.sqlTables = "kingdom";
                break;
            case 7:
                this.sqlTables = "outerspace";
                break;
            case 8:
                this.sqlTables = "festival";
                break;
            case 9:
                this.sqlTables = "music";
                break;
        }
    }

    public int getNumberOfData (int i) {
        int n = 50;
        switch (i) {
            case 0:
                n = 50;
                break;
            case 1:
                n = 50;
                break;
            case 2:
                n = 50;
                break;
            case 3:
                n = 50;
                break;
            case 4:
                n = 50;
                break;
            case 5:
                n = 50;
                break;
            case 6:
                n = 50;
                break;
            case 7:
                n = 50;
                break;
            case 8:
                n = 50;
                break;
            case 9:
                n = 50;
                break;
        }
        return n;
    }

    public Cursor getVocab() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"pop", "en", "th"};
        String sqlTables = this.sqlTables;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }
}
package th.ac.bodin.ppnt.englock;

import com.google.firebase.database.FirebaseDatabase;

public class englock extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

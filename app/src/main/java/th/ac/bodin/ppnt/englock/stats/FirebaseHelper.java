package th.ac.bodin.ppnt.englock.stats;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    private String id;
    private Context context;
    private Map<String, Object> childUpdates;
    private HashMap<String, Object> shopStats;
    private HashMap<String, Object> userStats;

    private int shop_item_amount = 10;

    public FirebaseHelper(Context context) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) this.id = acct.getId();
        this.context = context;
    }

    public boolean isIdExist() {
        return id != null;
    }

    public void createNewDatabase() {
        this.childUpdates = new HashMap<>();
        this.shopStats = new HashMap<>();
        this.userStats = new HashMap<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences("userStats", Context.MODE_PRIVATE);
        userStats.put("points", sharedPreferences.getLong("points", 0));
        userStats.put("quesCorrect", sharedPreferences.getLong("quesCorrect", 0));
        userStats.put("quesFound", sharedPreferences.getLong("quesFound", 0));
        userStats.put("recent0", sharedPreferences.getString("recent0", "-"));
        userStats.put("recent1", sharedPreferences.getString("recent1", "-"));
        userStats.put("recent2", sharedPreferences.getString("recent2", "-"));

        sharedPreferences = context.getSharedPreferences("shopStats", Context.MODE_PRIVATE);
        for(int i = 0; i < shop_item_amount; i++){
            shopStats.put("shopItem" + String.valueOf(i),
                    sharedPreferences.getBoolean("shopItem" + String.valueOf(i), false));
        }
        shopStats.put("selected", sharedPreferences.getInt("selected", 0));

        childUpdates.put("/users/" + id + "/shopStats/", shopStats);
        childUpdates.put("/users/" + id + "/userStats/", userStats);
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.updateChildren(childUpdates);
    }

    public void retrieveDatabase() {
        for(int i = 0; i < shop_item_amount; i++) updateToLocalBooleanStats("shopItem" + String.valueOf(i), "shopStats");
        updateToLocalIntStats("selected", "shopStats");
        updateToLocalLongStats("points", "userStats");
        updateToLocalLongStats("quesCorrect", "userStats");
        updateToLocalLongStats("quesFound", "userStats");
        for(int i = 0; i < 3; i++) updateToLocalStringStats("recent" + String.valueOf(i), "userStats");
    }

    private void updateToLocalLongStats(String childName, String statsType) {
        final String nodeName = childName;
        final String typeName = statsType;
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id).child(typeName).child(nodeName);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    long val = dataSnapshot.getValue(long.class);
                    SharedPreferences points = context.getSharedPreferences(typeName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = points.edit();
                    editor.putLong(nodeName, val);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateToLocalIntStats(String childName, String statsType) {
        final String nodeName = childName;
        final String typeName = statsType;
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id).child(typeName).child(nodeName);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int val = dataSnapshot.getValue(int.class);
                    SharedPreferences points = context.getSharedPreferences(typeName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = points.edit();
                    editor.putInt(nodeName, val);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateToLocalBooleanStats(String childName, String statsType) {
        final String nodeName = childName;
        final String typeName = statsType;
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id).child(typeName).child(nodeName);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    boolean val = dataSnapshot.getValue(boolean.class);
                    SharedPreferences points = context.getSharedPreferences(typeName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = points.edit();
                    editor.putBoolean(nodeName, val);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateToLocalStringStats(String childName, String statsType) {
        final String nodeName = childName;
        final String typeName = statsType;
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id).child(typeName).child(nodeName);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String val = dataSnapshot.getValue(String.class);
                    SharedPreferences points = context.getSharedPreferences(typeName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = points.edit();
                    editor.putString(nodeName, val);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void updateToCloud(String childName, int statsType, long val) {
        if (isIdExist()){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
            switch (statsType) {
                case 0:
                    mRef.child("userStats").child(childName).setValue(val);
                    break;
                case 1:
                    mRef.child("shopStats").child(childName).setValue(val);
                    break;
            }
        }
    }

    public void updateToCloud(String childName, int statsType, int val) {
        if (isIdExist()){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
            switch (statsType) {
                case 0:
                    mRef.child("userStats").child(childName).setValue(val);
                    break;
                case 1:
                    mRef.child("shopStats").child(childName).setValue(val);
                    break;
            }

        }
    }

    public void updateToCloud(String childName, int statsType, String val) {
        if (isIdExist()){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
            switch (statsType) {
                case 0:
                    mRef.child("userStats").child(childName).setValue(val);
                    break;
                case 1:
                    mRef.child("shopStats").child(childName).setValue(val);
                    break;
            }

        }
    }

    public void updateToCloud(String childName, int statsType, Boolean val) {
        if (isIdExist()){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
            switch (statsType) {
                case 0:
                    mRef.child("userStats").child(childName).setValue(val);
                    break;
                case 1:
                    mRef.child("shopStats").child(childName).setValue(val);
                    break;
            }

        }
    }

    //public void setquesCorrect
}

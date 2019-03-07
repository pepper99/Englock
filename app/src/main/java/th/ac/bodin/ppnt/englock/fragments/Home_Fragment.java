package th.ac.bodin.ppnt.englock.fragments;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import th.ac.bodin.ppnt.englock.MainActivity;
import th.ac.bodin.ppnt.englock.R;
import th.ac.bodin.ppnt.englock.stats.FirebaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    public static final String TAG = "OhHaiMARK";

    SignInButton signInButton;
    TextView mTextMessage;
    ImageView ProfilePic, ThemePic;
    AlertDialog alertDialog1;

    public static final int RC_SIGN_IN = 1010;

    GoogleSignInClient mGoogleSignInClient;

    public static Home_Fragment newInstance() {
        return new Home_Fragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        initInstance();

        return inflater.inflate(R.layout.fragment_home_, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ImageView coin = (ImageView) getView().findViewById(R.id.DollarIcon);

        ObjectAnimator anim = ObjectAnimator.ofFloat(coin, View.ROTATION_Y , 360);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(2000);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.start();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());

        SharedPreferences shared = getActivity().getSharedPreferences("userStats", Context.MODE_PRIVATE);
        boolean isPTsaved = shared.getBoolean("isPTsaved", false);

        signInButton = (SignInButton)getView().findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        if(acct != null) updateUI(GoogleSignIn.getLastSignedInAccount(this.getContext()));

        if(isPTsaved) updatePoints();
        else createPoints();

        ThemePic = (ImageView) getView().findViewById(R.id.ThemeImage);

        shared = getActivity().getSharedPreferences("shopStats", Context.MODE_PRIVATE);
        int selected = shared.getInt("selected", -1);
        switch (selected) {
            case 0:
                ThemePic.setImageResource(R.drawable.db_school);
                break;
            case 1:
                ThemePic.setImageResource(R.drawable.db_clothes);
                break;
            case 2:
                ThemePic.setImageResource(R.drawable.db_wheel_of_emotions);
                break;
            case 3:
                ThemePic.setImageResource(R.drawable.db_etiquette);
                break;
            case 4:
                ThemePic.setImageResource(R.drawable.db_landforms);
                break;
        }

    }


    //google account thingyssss
    private void initInstance() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    @Override
    public void onResume() {
        updatePoints();
        super.onResume();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            saveInfo(acct);

            // Signed in successfully, show authenticated UI.]
            updateUI(acct);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void updateUI(GoogleSignInAccount acct){
        ProfilePic = (ImageView)getView().findViewById(R.id.ProfileImage);
        Picasso.get()
                .load(acct.getPhotoUrl())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .error(R.drawable.ic_account_circle_black_24dp)
                .resize(220,220)
                .centerCrop()
                .into(ProfilePic);

        String AcName = acct.getDisplayName();
        mTextMessage = (TextView)getView().findViewById(R.id.welcomeText);
        signInButton.setVisibility(View.INVISIBLE);
        mTextMessage.setText(getResources().getString(R.string.welcome) + "\n       " + AcName);
        mTextMessage.setVisibility(View.VISIBLE);

        SharedPreferences shared = getActivity().getSharedPreferences("shopStats", Context.MODE_PRIVATE);
        ThemePic = (ImageView) getView().findViewById(R.id.ThemeImage);
        int selected = shared.getInt("selected", 0);
        switch (selected) {
            case 0:
                ThemePic.setImageResource(R.drawable.db_school);
                break;
            case 1:
                ThemePic.setImageResource(R.drawable.db_clothes);
                break;
            case 2:
                ThemePic.setImageResource(R.drawable.db_wheel_of_emotions);
                break;
            case 3:
                ThemePic.setImageResource(R.drawable.db_etiquette);
                break;
            case 4:
                ThemePic.setImageResource(R.drawable.db_landforms);
                break;
        }
    }

    private void saveInfo(GoogleSignInAccount acct){
        final String id = acct.getId();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            FirebaseHelper firebaseHelper = new FirebaseHelper(getActivity());

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    String title = getResources().getString(R.string.confirmation);
                    String desc = getResources().getString(R.string.statsFromCloud);
                    String yes = getResources().getString(R.string.yes);
                    String no = getResources().getString(R.string.no);

                    builder.setTitle(title);
                    builder.setMessage(desc);

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    firebaseHelper.retrieveDatabase();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    firebaseHelper.createNewDatabase();
                                    break;
                            }
                            alertDialog1.dismiss();
                        }
                    };
                    builder.setPositiveButton(yes, dialogClickListener);
                    builder.setNegativeButton(no, dialogClickListener);
                    alertDialog1 = builder.create();
                    alertDialog1.show();
                }
                else {
                    Log.d("kuy","nah");
                    firebaseHelper.createNewDatabase();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //points thingyszzzzzz
    private void createPoints(){
        SharedPreferences points = getActivity().getSharedPreferences("userStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = points.edit();

        editor.putLong("points", 0);
        editor.putBoolean("isPTsaved", true);
        editor.commit();

        updatePoints();
    }

    public void updatePoints(){
        SharedPreferences points = getActivity().getSharedPreferences("userStats", Context.MODE_PRIVATE);

        long pts = points.getLong("points", -1);

        mTextMessage = (TextView)getView().findViewById(R.id.PtnCount);
        if(pts != -1) mTextMessage.setText(String.valueOf(pts));
        else mTextMessage.setText("ERROR");
    }
}

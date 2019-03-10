package th.ac.bodin.ppnt.englock.extra_assets;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import th.ac.bodin.ppnt.englock.R;
import th.ac.bodin.ppnt.englock.MainActivity;
import th.ac.bodin.ppnt.englock.stats.FirebaseHelper;

public class ShopDialog extends DialogFragment {

    private static final String KEY_IMG = "key_image";
    private static final String KEY_MESSAGE = "key_message";
    private static final String KEY_YESBUTTON = "key_yesbtn";
    private static final String KEY_NOBUTTON = "key_nobtn";
    private static final String KEY_LAYOUT = "key_layout";
    private static final String KEY_POS = "key_position";
    private static final String KEY_PRICE = "key_price";

    private ImageView imgView;
    private TextView tvMessage;
    private Button yesButton;
    private Button noButton;

    private OnDialogListener onDialogListener;

    private String message;
    private int yesbtn;
    private int nobtn;
    private int layout;
    private int img;
    private int position;
    private long price;

    Product product;

    private android.support.v4.app.Fragment fragment;

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    public interface OnDialogListener {
        void onButtonClick();
    }
    private OnDialogListener getOnDialogListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Fragment fragment = getParentFragment();
            try {
                if (fragment != null) {
                    return (OnDialogListener) fragment;
                } else {
                    return (OnDialogListener) getActivity();
                }
            } catch (ClassCastException ignored) {
            }
        }
        return null;
    }

    public static ShopDialog newInstance(@StringRes int img, String message, @StringRes int yesbtn,
                                         @StringRes int nobtn, @StringRes int layout, int position, long price) {
        ShopDialog fragment = new ShopDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_IMG, img);
        bundle.putString(KEY_MESSAGE, message);
        bundle.putInt(KEY_YESBUTTON, yesbtn);
        bundle.putInt(KEY_NOBUTTON, nobtn);
        bundle.putInt(KEY_LAYOUT, layout);
        bundle.putInt(KEY_POS, position);
        bundle.putLong(KEY_PRICE, price);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static class Builder {
        private String message;
        private int img;
        private int yesbtn;
        private int nobtn;
        private int layout;
        private int position;
        private long price;

        private OnDialogListener onDialogListener;

        public Builder() {
        }

        public Builder setImage(int img) {
            this.img = img;
            return this;
        }

        public Builder setLayout(int layout) {
            this.layout = layout;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setYesButton(int yesbtn) {
            this.yesbtn = yesbtn;
            return this;
        }

        public Builder setNoButton(int nobtn) {
            this.nobtn = nobtn;
            return this;
        }

        public Builder setPosition(int position) {
            this.position = position;
            return this;
        }

        public Builder setPrice(long price) {
            this.price = price;
            return this;
        }

        public Builder setOnDialogListener(OnDialogListener listener) {
            this.onDialogListener = listener;
            return this;
        }

        public ShopDialog build() {
            ShopDialog fragment = ShopDialog.newInstance(img, message, yesbtn, nobtn, layout, position, price);
            fragment.setOnDialogListener(onDialogListener);
            return fragment;
        }
    }

    private void setupView() {
        tvMessage.setText(message);
        yesButton.setText(yesbtn);
        noButton.setText(nobtn);
        imgView.setImageResource(img);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = getOnDialogListener();
                if (listener != null) {
                    listener.onButtonClick();
                }

                int state = confirmPurchase(position);
                //Shop_Fragment.ConfirmDialogCompliant confirmDialog = new Shop_Fragment.ConfirmDialogCompliant(position);
                //confirmDialog.OkClick();
                Log.d("shoppeee", String.valueOf(position));

                if( state == 2 ) Toast.makeText(getActivity(), "Sorry, you don't have enough points.", Toast.LENGTH_SHORT).show();
                else if( state == 0 ) Toast.makeText(getActivity(), "You already bought this item.", Toast.LENGTH_SHORT).show();

                else {
                    product.setBoughtStat(true);;
                }

                dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = getOnDialogListener();
                if (listener != null) {
                    listener.onButtonClick();
                }
                dismiss();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            System.out.println("TOuch outside the dialog ******************** ");
            this.dismiss();
        }
        return false;
    }

    public void setFragment(android.support.v4.app.Fragment fragment){
        this.fragment = fragment;
    }

    public void setProduct(Product product) { this.product = product; }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreArguments(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    private void bindView(View view) {
        imgView = (ImageView) view.findViewById(R.id.coverImg);
        tvMessage = (TextView) view.findViewById(R.id.text_dialog);
        yesButton = (Button) view.findViewById(R.id.btn_dialogyes);
        noButton = (Button) view.findViewById(R.id.btn_dialogno);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_IMG, img);
        outState.putString(KEY_MESSAGE, message);
        outState.putInt(KEY_YESBUTTON, yesbtn);
        outState.putInt(KEY_NOBUTTON, nobtn);
        outState.putInt(KEY_LAYOUT, layout);
        outState.putInt(KEY_POS, position);
        outState.putLong(KEY_PRICE, price);
    }

    private void restoreInstanceState(Bundle bundle) {
        img = bundle.getInt(KEY_IMG);
        message = bundle.getString(KEY_MESSAGE);
        yesbtn = bundle.getInt(KEY_YESBUTTON);
        nobtn = bundle.getInt(KEY_NOBUTTON);
        layout = bundle.getInt(KEY_LAYOUT);
        position = bundle.getInt(KEY_POS);
        price = bundle.getLong(KEY_PRICE);

    }

    private void restoreArguments(Bundle bundle) {
        img = bundle.getInt(KEY_IMG);
        message = bundle.getString(KEY_MESSAGE);
        yesbtn = bundle.getInt(KEY_YESBUTTON);
        nobtn = bundle.getInt(KEY_NOBUTTON);
        layout = bundle.getInt(KEY_LAYOUT);
        position = bundle.getInt(KEY_POS);
        price = bundle.getLong(KEY_PRICE);
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogTheme;
    }

    private int confirmPurchase(int position) {
        long price = this.price;

        SharedPreferences shared = getActivity().getSharedPreferences("userStats", Context.MODE_PRIVATE);
        long current = shared.getLong("points", -1);

        if( current >= price ) {

            shared = getActivity().getSharedPreferences("shopStats", Context.MODE_PRIVATE);
            Boolean test = shared.getBoolean("shopItem" + String.valueOf(position), false);
            if( test ) return 0;

            SharedPreferences.Editor editor = shared.edit();
            editor.putBoolean("shopItem" + String.valueOf(position), true);
            editor.commit();

            test = shared.getBoolean("shopItem" + String.valueOf(position), false);

            if( test ) {
                shared = getActivity().getSharedPreferences("userStats", Context.MODE_PRIVATE);
                editor = shared.edit();
                editor.putLong("points", current - price);
                editor.commit();

                FirebaseHelper firebaseHelper = new FirebaseHelper(getActivity());
                firebaseHelper.updateToCloud("points", 0, current - price);
                firebaseHelper.updateToCloud("shopItem" + String.valueOf(position), 1, true);

                return 3;
            }
            else return 1;
        }

        else return 2;
    }
}


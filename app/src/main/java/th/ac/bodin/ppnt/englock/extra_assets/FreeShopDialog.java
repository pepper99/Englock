package th.ac.bodin.ppnt.englock.extra_assets;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
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
import th.ac.bodin.ppnt.englock.stats.FirebaseHelper;

public class FreeShopDialog extends DialogFragment {

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

    public Activity c;

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

    public void setActivity(Activity c){
        this.c = c;
    }

    public static FreeShopDialog newInstance(@StringRes int img, String message, @StringRes int yesbtn,
                                             @StringRes int nobtn, @StringRes int layout, int position, long price) {
        FreeShopDialog fragment = new FreeShopDialog();
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

        public Builder setOnDialogListener(OnDialogListener listener) {
            this.onDialogListener = listener;
            return this;
        }

        public FreeShopDialog build() {
            FreeShopDialog fragment = FreeShopDialog.newInstance(img, message, yesbtn, nobtn, layout, position, price);
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

                SharedPreferences shared = getActivity().getSharedPreferences("shopStats", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putBoolean("shopItem" + String.valueOf(position), true);
                editor.putInt("selected", position);
                editor.commit();
                shared = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                editor = shared.edit();
                editor.putBoolean("gotFreeItem", true);
                editor.commit();

                Log.d("freeshit = ", String.valueOf(position));
                c.finish();
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
    }

    private void restoreInstanceState(Bundle bundle) {
        img = bundle.getInt(KEY_IMG);
        message = bundle.getString(KEY_MESSAGE);
        yesbtn = bundle.getInt(KEY_YESBUTTON);
        nobtn = bundle.getInt(KEY_NOBUTTON);
        layout = bundle.getInt(KEY_LAYOUT);
        position = bundle.getInt(KEY_POS);

    }

    private void restoreArguments(Bundle bundle) {
        img = bundle.getInt(KEY_IMG);
        message = bundle.getString(KEY_MESSAGE);
        yesbtn = bundle.getInt(KEY_YESBUTTON);
        nobtn = bundle.getInt(KEY_NOBUTTON);
        layout = bundle.getInt(KEY_LAYOUT);
        position = bundle.getInt(KEY_POS);
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
    }
}


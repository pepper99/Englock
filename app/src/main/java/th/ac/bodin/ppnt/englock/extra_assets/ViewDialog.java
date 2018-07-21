package th.ac.bodin.ppnt.englock.extra_assets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import th.ac.bodin.ppnt.englock.R;

public class ViewDialog extends DialogFragment {

    private static final String KEY_MESSAGE = "key_message";
    private static final String KEY_BUTTON = "key_button";
    private static final String KEY_LAYOUT = "key_layout";

    private TextView tvMessage;
    private Button dialogButton;

    private OnDialogListener onDialogListener;

    private String message;
    private int button;
    private int layout;

    public Activity c;

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

    public static ViewDialog newInstance(String message, @StringRes int button, @StringRes int layout) {
        ViewDialog fragment = new ViewDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, message);
        bundle.putInt(KEY_BUTTON, button);
        bundle.putInt(KEY_LAYOUT, layout);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static class Builder {
        private String message;
        private int button;
        private int layout;

        private OnDialogListener onDialogListener;

        public Builder() {
        }

        public Builder setLayout(int layout) {
            this.layout = layout;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setButton(int button) {
            this.button = button;
            return this;
        }

        public Builder setOnDialogListener(OnDialogListener listener) {
            this.onDialogListener = listener;
            return this;
        }

        public ViewDialog build() {
            ViewDialog fragment = ViewDialog.newInstance(message, button, layout);
            fragment.setOnDialogListener(onDialogListener);
            return fragment;
        }
    }

    public void setActivity(Activity c){
        this.c = c;
    }

    private void setupView() {
        tvMessage.setText(message);
        dialogButton.setText(button);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = getOnDialogListener();
                if (listener != null) {
                    listener.onButtonClick();
                }
                if(c != null){
                    c.finish();
                }
                dismiss();
            }
        });
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
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    private void bindView(View view) {
        tvMessage = (TextView) view.findViewById(R.id.text_dialog);
        dialogButton = (Button) view.findViewById(R.id.btn_dialog);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MESSAGE, message);
        outState.putInt(KEY_BUTTON, button);
        outState.putInt(KEY_LAYOUT, layout);
    }

    private void restoreInstanceState(Bundle bundle) {
        message = bundle.getString(KEY_MESSAGE);
        button = bundle.getInt(KEY_BUTTON);
        layout = bundle.getInt(KEY_LAYOUT);
    }

    private void restoreArguments(Bundle bundle) {
        message = bundle.getString(KEY_MESSAGE);
        button = bundle.getInt(KEY_BUTTON);
        layout = bundle.getInt(KEY_LAYOUT);
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

}


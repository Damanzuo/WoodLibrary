package com.wood.commonlib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.wood.commonlib.R;
import com.wood.commonlib.interfaces.OnCommonDialogClickListener;

public class CommonDialog extends Dialog {

    private TextView mCommonTitle;
    private TextView mCommonContent;
    private TextView mCommonConfirm;
    private TextView mCommonCancel;
    private OnCommonDialogClickListener mListener;

    public void setOnCommonDialogClickListener(OnCommonDialogClickListener listener) {
        this.mListener = listener;
    }

    public CommonDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.common_dialog_layout);
        mCommonTitle = findViewById(R.id.common_title);
        mCommonContent = findViewById(R.id.common_content);
        mCommonConfirm = findViewById(R.id.common_confirm);
        mCommonCancel = findViewById(R.id.common_cancel);
        setCanceledOnTouchOutside(false);

        mCommonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onConfirmClick();
                }
                dismiss();
            }
        });

        mCommonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onCancelClick();
                }

                dismiss();
            }
        });
    }


    public void setGoneTitle() {
        if (mCommonTitle != null) {
            mCommonTitle.setVisibility(View.GONE);
        }
    }

    public void setCommonTitle(CharSequence charSequence) {
        mCommonTitle.setText(charSequence);
    }

    public void setCommonContent(CharSequence charSequence) {
        mCommonContent.setText(charSequence);
    }

    public void setCommonConfirm(CharSequence charSequence) {
        mCommonConfirm.setText(charSequence);
    }

    public void setCommonCancel(CharSequence charSequence) {
        mCommonCancel.setText(charSequence);
    }

    public void setCommonTitle(@StringRes int resId) {
        mCommonTitle.setText(resId);
    }

    public void setCommonContent(@StringRes int resId) {
        mCommonContent.setText(resId);
    }

    public void setCommonConfirm(@StringRes int resId) {
        mCommonConfirm.setText(resId);
    }

    public void setCommonCancel(@StringRes int resId) {
        mCommonCancel.setText(resId);
    }
}

package com.fuzhuangfaqianla.xinhuahua.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by xiongchang on 2017/10/14.
 */

public class ShowPasswordUtil implements TextWatcher,View.OnTouchListener {
    EditText editText;
    ImageView imageView;

    public ShowPasswordUtil(EditText editText, ImageView imageView){
        this.editText = editText;
        this.imageView = imageView;

        editText.addTextChangedListener(this);
        imageView.setOnTouchListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(editText.getText().toString().length()>0){
            imageView.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case MotionEvent.ACTION_UP:
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }

        return true;
    }
}

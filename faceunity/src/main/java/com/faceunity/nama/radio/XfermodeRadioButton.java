package com.faceunity.nama.radio;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.faceunity.nama.R;

/**
 * Created by tujh on 2018/3/2.
 */

public class XfermodeRadioButton extends AppCompatRadioButton {

    private String textXfermode;
    private int textSizeXfermode;
    private int textColorXfermodeNormal = 0xffffffff;
    private int textColorXfermodeChecked = 0x99050f14;
    private int textXfermodeWidth;
    private int baseLineY;

    private Paint mMyRadioButtonPaint;

    public XfermodeRadioButton(Context context) {
        this(context, null);
    }

    public XfermodeRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.radioButtonStyle);
    }

    public XfermodeRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.xfermode_radio_btn, defStyleAttr, 0);
        textXfermode = typedArray.getString(R.styleable.xfermode_radio_btn_text_xfermode);
        textSizeXfermode = typedArray.getDimensionPixelSize(R.styleable.xfermode_radio_btn_text_size_xfermode, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, context.getResources().getDisplayMetrics()));
        typedArray.recycle();

        mMyRadioButtonPaint = new Paint();
        mMyRadioButtonPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        mMyRadioButtonPaint.setColor(textColorXfermodeNormal);
        mMyRadioButtonPaint.setTextSize(textSizeXfermode);
        mMyRadioButtonPaint.setAntiAlias(true);

        setButtonDrawable(new StateListDrawable());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textXfermodeWidth = (int) mMyRadioButtonPaint.measureText(textXfermode);
        Paint.FontMetrics fontMetrics = mMyRadioButtonPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        baseLineY = (int) (getMeasuredHeight() - top - bottom) / 2;
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (mMyRadioButtonPaint != null) {
            mMyRadioButtonPaint.setColor(checked ? textColorXfermodeChecked : textColorXfermodeNormal);
            mMyRadioButtonPaint.setXfermode(new PorterDuffXfermode(checked ? PorterDuff.Mode.SRC_ATOP : PorterDuff.Mode.XOR));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = (getMeasuredWidth() - textXfermodeWidth) / 2;
        canvas.drawText(textXfermode, x, baseLineY, mMyRadioButtonPaint);
    }
}

package site.lizihanglove.arch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ArchView extends View {
    private int mBackgroundArchColor = 0x74828F;
    private int mFrontArchColor = 0x3399FF;
    private int mCenterArchTextColor = 0xAAAAAA;
    private float mArchWidth = 20;
    private float mCenterArchTextSize = 0;
    private int mFinalSize;
    private Paint mBackgroundPaint;
    private Paint mFrontPaint;
    private Paint mTextPaint;
    private RectF mOval;

    private float mMaxNumber = 100f;
    private float mCurrentNumber = 0f;

    private String mCenterText = "";

    public ArchView(Context context) {
        this(context, null);
    }

    public ArchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundPaint = new Paint();
        mFrontPaint = new Paint();
        mTextPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ArchView);
        mBackgroundArchColor = array.getColor(R.styleable.ArchView_backgroundColor, mBackgroundArchColor);
        mFrontArchColor = array.getColor(R.styleable.ArchView_frontColor, mFrontArchColor);
        mCenterArchTextColor = array.getColor(R.styleable.ArchView_centerTextColor, mCenterArchTextColor);
        mCenterArchTextSize = array.getDimension(R.styleable.ArchView_centerTextSize, mCenterArchTextSize);
        mArchWidth = array.getDimension(R.styleable.ArchView_archWidth, mArchWidth);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mFinalSize = Math.max(width, height);
        setMeasuredDimension(mFinalSize, mFinalSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mOval = new RectF(mArchWidth, mArchWidth, mFinalSize - mArchWidth, mFinalSize - mArchWidth);
        mBackgroundPaint.setColor(mBackgroundArchColor);
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        mBackgroundPaint.setStrokeWidth(mArchWidth);
        canvas.drawArc(mOval, 135, 270, false, mBackgroundPaint);
        mFrontPaint.setColor(mFrontArchColor);
        mFrontPaint.setAntiAlias(true);
        mFrontPaint.setStyle(Paint.Style.STROKE);
        mFrontPaint.setStrokeCap(Paint.Cap.ROUND);
        mFrontPaint.setStrokeWidth(mArchWidth);
        float ratio = mCurrentNumber / mMaxNumber;
        float sweepAngle = 270 * ratio;
        canvas.drawArc(mOval, 135, sweepAngle, false, mFrontPaint);

        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mCenterArchTextColor);
        mTextPaint.setTextSize(mCenterArchTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (mOval.centerY() - top/2 - bottom/2);
        canvas.drawText(mCenterText, mOval.centerX(), baseLineY, mTextPaint);
    }

    /**
     * 设置最新值
     *
     * @param currentNumber 当前数值
     */
    public void setCurrentNumber(int currentNumber) {
        mCurrentNumber = currentNumber;
        invalidate();
    }

    /**
     * 设置最新值
     *
     * @param maxNumber 最大值
     */
    public void setMaxNumber(int maxNumber) {
        mMaxNumber = maxNumber;
        invalidate();
    }

    /**
     * 设置最新值
     *
     * @param centerText 设置中间文字的值
     */
    public void setText(String centerText) {
        mCenterText = centerText;
        invalidate();
    }
}

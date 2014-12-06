package com.edu.thss.smartdental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.accounts.Account;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Days;
/**
 * TODO: document your custom view class.
 */
public class LineChartView extends View {
	private String mExampleString; // TODO: use a default from R.string...
	private int mExampleColor = Color.RED; // TODO: use a default from
											// R.color...
	private float mExampleDimension = 0; // TODO: use a default from R.dimen...
	private Drawable mExampleDrawable;

	private TextPaint mTextPaint;
	private float mTextWidth;
	private float mTextHeight;

	public LineChartView(Context context) {
		super(context);
		init(null, 0);
	}

	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public LineChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.LineChartView, defStyle, 0);

		mExampleString = a.getString(R.styleable.LineChartView_exampleString);
		mExampleColor = a.getColor(R.styleable.LineChartView_exampleColor,
				mExampleColor);
		// Use getDimensionPixelSize or getDimensionPixelOffset when dealing
		// with
		// values that should fall on pixel boundaries.
		mExampleDimension = a.getDimension(
				R.styleable.LineChartView_exampleDimension, mExampleDimension);

		if (a.hasValue(R.styleable.LineChartView_exampleDrawable)) {
			mExampleDrawable = a
					.getDrawable(R.styleable.LineChartView_exampleDrawable);
			mExampleDrawable.setCallback(this);
		}

		a.recycle();

		// Set up a default TextPaint object
		mTextPaint = new TextPaint();
		mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextAlign(Paint.Align.LEFT);

		// Update TextPaint and text measurements from attributes
		invalidateTextPaintAndMeasurements();
	}

	private void invalidateTextPaintAndMeasurements() {
		mTextPaint.setTextSize(mExampleDimension);
		mTextPaint.setColor(mExampleColor);
		mTextWidth = mTextPaint.measureText(mExampleString);

		Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
		mTextHeight = fontMetrics.bottom;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.


		drawFrame(canvas);
		//drawData(canvas, data);
	}

	/**
	 * Gets the example string attribute value.
	 * 
	 * @return The example string attribute value.
	 */
	public String getExampleString() {
		return mExampleString;
	}

	/**
	 * Sets the view's example string attribute value. In the example view, this
	 * string is the text to draw.
	 * 
	 * @param exampleString
	 *            The example string attribute value to use.
	 */
	public void setExampleString(String exampleString) {
		mExampleString = exampleString;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example color attribute value.
	 * 
	 * @return The example color attribute value.
	 */
	public int getExampleColor() {
		return mExampleColor;
	}

	/**
	 * Sets the view's example color attribute value. In the example view, this
	 * color is the font color.
	 * 
	 * @param exampleColor
	 *            The example color attribute value to use.
	 */
	public void setExampleColor(int exampleColor) {
		mExampleColor = exampleColor;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example dimension attribute value.
	 * 
	 * @return The example dimension attribute value.
	 */
	public float getExampleDimension() {
		return mExampleDimension;
	}

	/**
	 * Sets the view's example dimension attribute value. In the example view,
	 * this dimension is the font size.
	 * 
	 * @param exampleDimension
	 *            The example dimension attribute value to use.
	 */
	public void setExampleDimension(float exampleDimension) {
		mExampleDimension = exampleDimension;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example drawable attribute value.
	 * 
	 * @return The example drawable attribute value.
	 */
	public Drawable getExampleDrawable() {
		return mExampleDrawable;
	}

	/**
	 * Sets the view's example drawable attribute value. In the example view,
	 * this drawable is drawn above the text.
	 * 
	 * @param exampleDrawable
	 *            The example drawable attribute value to use.
	 */
	public void setExampleDrawable(Drawable exampleDrawable) {
		mExampleDrawable = exampleDrawable;
	}
	
	
	public void drawFrame(Canvas canvas) {
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;
		
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(5);
		canvas.drawLine(10, 0, 10, contentHeight, paint);
		canvas.drawLine(10, 0, 0, 15, paint);
		canvas.drawLine(10, 0, 20, 15, paint);
		//paint.setStrokeWidth(9);
		canvas.drawLine(10, contentHeight - 30, contentWidth, contentHeight - 30, paint);
		paint.setStrokeWidth(3);
		paint.setColor(Color.GRAY);
		contentHeight -= 30;
		for (int i = 0; i < 5; i++) {
			canvas.drawLine(10, contentHeight*(float)0.1+contentHeight*(float)0.2*i, contentWidth, contentHeight*(float)0.1+contentHeight*(float)0.2*i, paint);
		}
	}
	
	
	public void drawLine(Canvas canvas, Account[] data) throws ParseException {
		double min_tot, max_tot;
		Date min_date, max_date;
		SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-DD");
		min_date = df.parse("1899-12-31");
		max_date = df.parse("2999-12-31");
		min_tot = 1000000;
		max_tot = 0;
		Date tmp;
		for (Account account:data) {
//			tmp = df.parse(account.time.substring(10));
//			if (tmp.before(min_date)) min_date = tmp;
//			if (tmp.after(max_date)) max_date = tmp;
//			if (account.firstTotal > max_tot) max_tot = account.firstTotal;
//			if (account.finalTotal < min_tot) min_tot = account.finalTotal;
		}
		int len = data.length;
		DateTime min_datetime, max_datetime, tmp_datetime;
		min_datetime = new DateTime(min_date);
		max_datetime = new DateTime(max_date);
//		Days.daysBetween(tmp_datetime, min_datetime).getDays();
		
	}
}

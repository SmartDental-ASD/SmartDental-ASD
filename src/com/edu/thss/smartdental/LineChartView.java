package com.edu.thss.smartdental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.bool;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.edu.thss.smartdental.model.general.parseJson;
import com.edu.thss.smartdental.model.general.parseJson.Account;
/**
 * TODO: document your custom view class.
 */
public class LineChartView extends View {
	public String mExampleString; // TODO: use a default from R.string...
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
		Account data[];
		data = new Account[1];
		try {
			drawLine(canvas, data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		paint.setColor(0xff00ffff);
		paint.setStrokeWidth(5);
		canvas.drawLine(70, 0, 70, contentHeight, paint);
		canvas.drawLine(70, 0, 60, 15, paint);
		canvas.drawLine(70, 0, 80, 15, paint);
		//paint.setStrokeWidth(9);
		canvas.drawLine(70, contentHeight - 30, contentWidth, contentHeight - 30, paint);
		paint.setStrokeWidth(3);
		paint.setColor(0xff00ffff);
		contentHeight -= 30;
		for (int i = 0; i < 5; i++) {
			canvas.drawLine(70, contentHeight*(float)0.1+contentHeight*(float)0.2*i, contentWidth, contentHeight*(float)0.1+contentHeight*(float)0.2*i, paint);
		}
	}
	
//	class drug {
//		int medicineId;
//		String medicineName;
//		double medicinePrice;
//		int medicineCount;
//		double medicineReimbusement;
//		double medicineRatio;
//	}
//	class Account {
//		String patientName;
//		int patientId;
//		String time;
//		drug medicine[];
//		int success;
//		float firstTotal;
//		float finalTotal;
//		public Account(String time, float firstTotal, float finalTotal) {
//			this.time = time;
//			this.firstTotal = firstTotal;
//			this.finalTotal = finalTotal;
//		}
//	}

	public void drawLine(Canvas canvas, Account[] data) throws ParseException {
		parseJson strToJson = new parseJson();
		data = strToJson.parseSimpleAccount(mExampleString);
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;
		contentWidth -= 70;
		contentHeight -= 30;
		float min_tot, max_tot;
		Date min_date, max_date;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		max_date = df.parse("1899-12-31");
		min_date = df.parse("2999-12-31");
		min_tot = 1000000;
		max_tot = 0;
		Date tmp;
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(7);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		int len = data.length;
		int i = 0;
		//Merge account with same time field
		while (i < len - 1) {
			if (data[i].time.substring(0,10).equals(data[i+1].time.substring(0,10))) {
				data[i].firstTotal += data[i+1].firstTotal;
				data[i].finalTotal += data[i+1].finalTotal;
				int j = i+1;
				while (j < len-1) {
					data[j] = data[j+1];
					j++;
				}
				len--;
			}
			else {
				i++;
			}
 		}
		for (i = 0; i < len; i++) {
			Account account = data[i];
			tmp = df.parse(account.time.substring(0,10));
			if (tmp.before(min_date)) min_date = tmp;
			if (tmp.after(max_date)) max_date = tmp;
			if (account.firstTotal > max_tot) max_tot = (float)account.firstTotal;
			if (account.finalTotal < min_tot) min_tot = (float)account.finalTotal;
		}
		DateTime min_datetime, max_datetime, tmp_datetime;
		min_datetime = new DateTime(min_date);
		max_datetime = new DateTime(max_date);
		if (len == 1) {
			min_datetime = min_datetime.minusDays(1);
			max_datetime = max_datetime.plusDays(1);
		}
		if (min_tot == max_tot) {
			min_tot -= 1;
			max_tot += 1;
		}
		int max_duration = Days.daysBetween(min_datetime, max_datetime).getDays();
		float x = 0;
		float y = 0;
		for (i = 0; i < len; i++) {
			tmp = df.parse(data[i].time.substring(0,10));
			tmp_datetime = new DateTime(tmp);
			int duration = Days.daysBetween(min_datetime, tmp_datetime).getDays();
			float _x = 70 + (float)0.1*contentWidth+(float)0.8*contentWidth*duration/max_duration;
			float _y = contentHeight*(float)0.9 - ((float)data[i].firstTotal - min_tot) / (max_tot - min_tot) * contentHeight * (float)0.8;
			if (x != 0 || y != 0) canvas.drawLine(x, y, _x, _y, paint);
			x = _x;
			y = _y;
			canvas.drawCircle(x, y, 5, paint);
		}
		x = 0;
		y = 0;
        PathEffect effects = new DashPathEffect(
                        new float[]{35,15,35,15}, 1);
        paint.setPathEffect(effects);
		paint.setColor(Color.GREEN);
		paint.setTextSize(30);
		for (i = 0; i < len; i++) {
			tmp = df.parse(data[i].time.substring(0,10));
			tmp_datetime = new DateTime(tmp);
			int duration = Days.daysBetween(min_datetime, tmp_datetime).getDays();
			float _x = 70 + (float)0.1*contentWidth+(float)0.8*contentWidth*duration/max_duration;
			float _y = contentHeight*(float)0.9 - ((float)data[i].finalTotal - min_tot) / (max_tot - min_tot) * contentHeight * (float)0.8;
			if (x != 0 || y != 0) {Path path = new Path(); path.moveTo(x, y); path.lineTo(_x, _y); canvas.drawPath(path, paint);}
			x = _x;
			y = _y;
			canvas.drawCircle(x, y, 5, paint);
		}
		paint.setStyle(Paint.Style.FILL);
		contentHeight += 30;
		paint.setColor(0xff00ffff);
		paint.setTextSize(25);
		float tot[];
		tot = new float[len*2];
		for (i = 0; i < len; i++) {
			tot[i*2] = (float)data[i].finalTotal;
			tot[i*2+1] = (float)data[i].firstTotal;
			tmp = df.parse(data[i].time.substring(0,10));
			tmp_datetime = new DateTime(tmp);
			int duration = Days.daysBetween(min_datetime, tmp_datetime).getDays();
			float _x = 70 + (float)0.1*contentWidth+(float)0.8*contentWidth*duration/max_duration;
			if (i==1 && Days.daysBetween(min_datetime, tmp_datetime).getDays()*0.8*contentWidth/max_duration < paint.measureText(data[i].time.substring(5,10))*1.5 + 10) continue;
			if (i==len-2 && Days.daysBetween(tmp_datetime, new DateTime(df.parse(data[i+1].time.substring(0,10)))).getDays()*0.8*contentWidth/max_duration < paint.measureText(data[i].time.substring(5,10))*1.5 + 10) continue;
			if (i==0 || i==len-1 || Days.daysBetween(new DateTime(df.parse(data[i-1].time.substring(0,10))), tmp_datetime).getDays()*0.8*contentWidth/max_duration > paint.measureText(data[i].time.substring(5,10)) + 10)
				if (i==0||i==len-1)
					canvas.drawText(data[i].time.substring(0,10), _x - paint.measureText(data[i].time.substring(0,10))/2, contentHeight, paint);
				else 
					canvas.drawText(data[i].time.substring(5,10), _x - paint.measureText(data[i].time.substring(5,10))/2, contentHeight, paint);
		}
		for (i = 0; i < len * 2 - 1; i++) {
			for (int j = i + 1; j < len * 2; j++) {
				if (tot[i] > tot[j]) {
					tot[i] += tot[j];
					tot[j] = tot[i] - tot[j];
					tot[i] -= tot[j];
				}
			}
		}
		contentHeight -= 30;
		len *= 2;
		i = 0;
		while (i < len - 1) {
			if (tot[i] == tot[i+1]) {
				int j = i+1;
				while (j < len-1) {
					tot[j] = tot[j+1];
					j++;
				}
				len--;
			}
			else {
				i++;
			}
 		}		
		for (i = 0; i < len; i++) {
			float _y = contentHeight*(float)0.9 - (tot[i] - min_tot) / (max_tot - min_tot) * contentHeight * (float)0.8;
			if (i==len-2 && (tot[i+1] - tot[i]) * contentHeight * (float)0.8 / (max_tot - min_tot) < 30) continue;
			if (i==0 || i==len-1 || (tot[i] - tot[i-1]) * contentHeight * (float)0.8 / (max_tot - min_tot) > 30)
				canvas.drawText(Float.toString(tot[i]), 60 - paint.measureText(Float.toString(tot[i])), _y+(float)12.5, paint);
		}
	}
}

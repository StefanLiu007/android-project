package com.eden.util;

import com.eden.view.KCalendar;
import com.eden.view.KCalendar.OnCalendarClickListener;
import com.eden.view.KCalendar.OnCalendarDateChangedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class Calendar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		KCalendar calendar = new KCalendar(this);
		
		setContentView(calendar);
		
		calendar.setOnCalendarClickListener(new OnCalendarClickListener() {
			@Override
			public void onCalendarClick(int row, int col, String dateFormat) {
//				Toast.makeText(Calendar.this, dateFormat, Toast.LENGTH_SHORT).show();
				Intent data = new Intent();
				data.putExtra("马志远大帅比", dateFormat);
				setResult(RESULT_OK,data);
				finish();
//				startActivity(new Intent(Calendar.this,PersonalInfo.class));
			}
		});
		
		calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
			@Override
			public void onCalendarDateChanged(int year, int month) {
				Toast.makeText(Calendar.this, year + "-" + month, Toast.LENGTH_SHORT).show();
				
			}
		});
	}
}

package edu.buaa.vehiclemanagementsystem.view.activity;

import java.util.Calendar;
import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.ToggleButton;
import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.util.LogUtil;
import edu.buaa.vehiclemanagementsystem.view.activity.base.BaseActivity;

@EActivity(R.layout.activity_choose_location)
public class ChooseLocationActivity extends BaseActivity {

	@ViewById(R.id.dp_start_time)
	DatePicker dpStartTime;
	@ViewById(R.id.dp_end_time)
	DatePicker dpEndTime;
	@ViewById(R.id.et_code)
	EditText etCode;
	@ViewById(R.id.tb_filter_stop_point)
	ToggleButton tbFilterStopPoint;

	@ViewById(R.id.np_index)
	NumberPicker npIndex;
	@ViewById(R.id.np_item_per_page)
	NumberPicker npItemPerPage;

	protected String startTime;
	protected String endTime;
	protected int index;
	protected int itemPerPage;

	@AfterViews
	void initDataPicker() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		Date date = new Date();
		dpStartTime.setMaxDate(date.getTime());
		dpEndTime.setMaxDate(date.getTime());
		startTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
		endTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
		dpStartTime.init(year, monthOfYear, dayOfMonth,
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						startTime = year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth;
						LogUtil.log(TAG, "startTime " + startTime);
					}
				});

		dpEndTime.init(year, monthOfYear, dayOfMonth,
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						endTime = year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth;
						LogUtil.log(TAG, "endTime " + endTime);
					}
				});
	}

	@AfterViews
	void initNumberPicker() {
		// TODO Auto-generated method stub
		npIndex.setMinValue(0);
		npIndex.setMaxValue(10000);
		npIndex.setValue(0);
		npIndex.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				// TODO Auto-generated method stub

			}
		});
		npIndex.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				index = newVal;
			}
		});
		npItemPerPage.setMaxValue(Integer.MAX_VALUE);
		npItemPerPage.setMinValue(0);
		npItemPerPage.setValue(50);
		npItemPerPage.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {

			}
		});
		npItemPerPage.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {
				itemPerPage = newVal;
			}
		});
	}

	@Click(R.id.btn_download_locus_info)
	void sendRequest() {
		String data = "";
		String code = etCode.getText().toString().trim();

		String filterStopPoint = tbFilterStopPoint.isChecked() ? "1" : "0";

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(code);
		stringBuilder.append("!");
		stringBuilder.append(startTime);
		stringBuilder.append("!");
		stringBuilder.append(endTime);
		stringBuilder.append("!");
		stringBuilder.append(filterStopPoint);
		stringBuilder.append("!");
		stringBuilder.append(index);
		stringBuilder.append("!");
		stringBuilder.append(itemPerPage);

		data = stringBuilder.toString();
		Intent intent = new Intent(this, LocationListActivity_.class);
		intent.putExtra("data", data);
		startActivity(intent);
	}
}

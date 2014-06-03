package edu.buaa.vehiclemanagementsystem.view.activity.tabs;

import org.androidannotations.annotations.EActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.model.Vehicle;
import edu.buaa.vehiclemanagementsystem.util.Constants;

@EActivity
public class HomeActivity extends TabActivity {

	private TabHost tabHost;
	private Vehicle vehicle;
	private Bundle bundle;
	private Intent preIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preIntent = getIntent();
		setContentView(R.layout.activity_home);
		vehicle = (Vehicle) getIntent().getSerializableExtra(Constants.VEHICLE);
		tabHost = getTabHost();
		addTabs("location", "车辆定位", R.drawable.main_bar_carlocation, LocationActivity_.class);
		addTabs("locus", "车辆轨迹", R.drawable.main_bar_carhistory, LocusActivity_.class);
		addTabs("state", "车辆状态", R.drawable.main_bar_carstate, StateActivity_.class);
		addTabs("more", "更多", R.drawable.main_bar_carmore, MoreActivity_.class);
		tabHost.setCurrentTab(0);
	}

	private void addTabs(String tag, String text, int drawableId, Class clazz) {
		Intent intent = new Intent();
		intent.putExtras(preIntent);
		intent.setClass(this, clazz);
		Drawable drawableTop = getResources().getDrawable(drawableId);
		drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
		TextView textView = (TextView) View.inflate(this, R.layout.tab_textview, null);
		textView.setText(text);
		textView.setCompoundDrawables(null, drawableTop, null, null);
		tabHost.addTab(tabHost.newTabSpec(tag).setIndicator(textView).setContent(intent));
	}
}

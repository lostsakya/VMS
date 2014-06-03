package edu.buaa.vehiclemanagementsystem.view.fragment;

import edu.buaa.vehiclemanagementsystem.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.Polyline;

@EActivity(R.layout.activity_tabs)
public class TabActivity extends FragmentActivity {

	private String TAG = getClass().getSimpleName();
	private Fragment currentFragment;
	private SupportMapFragment locationFragment;
	private StatusFragment statusFragment;
	private MoreFragment moreFragment;
	private LatLng latlng;
	private ArrayList<LatLng> positions;
	private Marker marker;
	private int index;
	private AMap aMap;
	private Polyline polyline;

	// @ViewById(R.id.tab_location)
	RadioButton tabLocation;
	// @ViewById(R.id.tab_status)
	RadioButton tabStatus;
	// @ViewById(R.id.tab_more)
	RadioButton tabMore;

	@AfterViews
	void initFragment() {

		locationFragment = SupportMapFragment.newInstance();
		statusFragment = new StatusFragment();
		moreFragment = new MoreFragment();

		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		// fragmentTransaction.add(R.id.fragment_container, locationFragment);
		// fragmentTransaction.add(R.id.fragment_container, statusFragment);
		// fragmentTransaction.add(R.id.fragment_container, moreFragment);
		fragmentTransaction.commit();

		tabLocation.performClick();
	}

	private void showFragment(Fragment fragment) {

		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.show(fragment);
		fragmentTransaction.commit();
	}

	// @CheckedChange({ R.id.tab_location, R.id.tab_status, R.id.tab_more, })
	// void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	//
	// if (isChecked) {
	//
	// FragmentTransaction fragmentTransaction = getSupportFragmentManager()
	// .beginTransaction();
	//
	// switch (buttonView.getId()) {
	// case R.id.tab_location:
	// fragmentTransaction.show(currentFragment = locationFragment);
	// fragmentTransaction.hide(statusFragment);
	// fragmentTransaction.hide(moreFragment);
	// break;
	// case R.id.tab_status:
	// fragmentTransaction.show(currentFragment = statusFragment);
	// fragmentTransaction.hide(locationFragment);
	// fragmentTransaction.hide(moreFragment);
	// break;
	// case R.id.tab_more:
	// fragmentTransaction.show(currentFragment = moreFragment);
	// fragmentTransaction.hide(statusFragment);
	// fragmentTransaction.hide(locationFragment);
	// break;
	// default:
	// break;
	// }
	//
	// fragmentTransaction.commit();
	// }
	// }

}

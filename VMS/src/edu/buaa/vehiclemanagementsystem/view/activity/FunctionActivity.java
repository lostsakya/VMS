package edu.buaa.vehiclemanagementsystem.view.activity;

import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.view.activity.base.BaseActivity;

import android.content.Intent;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_function_list)
public class FunctionActivity extends BaseActivity {

	@Click(R.id.btn_all_info)
	void allInfo() {
		startActivity(new Intent(this, TerminalListActivity_.class));
	}

	@Click(R.id.btn_group_info)
	void groupInfo() {
		startActivity(new Intent(this, GroupListActivity_.class));
	}

	@Click(R.id.btn_state_info)
	void stateInfo() {
		startActivity(new Intent(this, StateListActivity_.class));
	}

	@Click(R.id.btn_locus_info)
	void locusInfo() {
		startActivity(new Intent(this, ChooseLocationActivity_.class));
	}

}

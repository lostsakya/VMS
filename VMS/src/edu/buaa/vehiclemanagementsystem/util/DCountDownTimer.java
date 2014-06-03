package edu.buaa.vehiclemanagementsystem.util;

import android.os.CountDownTimer;
import android.widget.ProgressBar;

public class DCountDownTimer extends CountDownTimer {

	private ProgressBar progressBar;
	private long millisInFuture;
	private long countDownInterval;

	public DCountDownTimer(ProgressBar progressBar, long millisInFuture,
			long countDownInterval) {
		super(millisInFuture, countDownInterval);
		this.millisInFuture = millisInFuture;
		this.countDownInterval = countDownInterval;
		this.progressBar = progressBar;
		showStartPoint();
	}

	private void showStartPoint() {

	}

	@Override
	public void onFinish() {
		progressBar.setProgress(progressBar.getMax());
		showEndPoint();
	}

	private void showEndPoint() {

	}

	@Override
	public void onTick(long millisUntilFinished) {
		int progress = progressBar.getProgress();
		int max = progressBar.getMax();
		if (progress < max) {
			progressBar.setProgress(progress + 1);
		}

	}
}

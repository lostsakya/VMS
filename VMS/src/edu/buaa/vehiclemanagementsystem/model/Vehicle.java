package edu.buaa.vehiclemanagementsystem.model;

import java.io.Serializable;

public class Vehicle implements Serializable {
	// 车牌号,终端编号,在线状态（0离线，1在线）,速度,是否报警（0正常，1异常）,分组编号,车牌颜色,驾驶员
	private String license;
	private String code;
	private String onlineState;
	private String speed;
	private String alarmState;
	private String groupCode;
	private String color;
	private String driver;

	public Vehicle(String license, String code, String onlineState, String speed,
			String alarmState, String groupCode, String color, String driver) {
		this.license = license;
		this.code = code;
		this.onlineState = onlineState;
		this.speed = speed;
		this.alarmState = alarmState;
		this.groupCode = groupCode;
		this.color = color;
		this.driver = driver;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(String alarmState) {
		this.alarmState = alarmState;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	// @Override
	// public String toString() {
	// return "Vehicle [license=" + license + ", code=" + code + ", state=" +
	// state + ", speed=" + speed + ", alarm=" + alarm + ", groupCode=" +
	// groupCode + ", color=" + color
	// + ", driver=" + driver + "]";
	// }
	// 车牌号,终端编号,在线状态（0离线，1在线）,速度,是否报警（0正常，1异常）,分组编号,车牌颜色,驾驶员
	@Override
	public String toString() {
		return "车辆信息：<br/><br/>车牌号=" + license + ", 终端编号=" + code + ", 在线状态="
				+ ("1".equals(onlineState) ? "在线" : "离线") + ", 速度=" + speed + ", 是否报警="
				+ ("0".equals(alarmState) ? "正常" : "异常") + ", 分组编号=" + groupCode
				+ ", 车牌颜色=" + color + ", 驾驶员=" + driver;
	}
}

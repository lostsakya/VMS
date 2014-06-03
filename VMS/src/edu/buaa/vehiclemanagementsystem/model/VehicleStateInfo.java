package edu.buaa.vehiclemanagementsystem.model;

public class VehicleStateInfo {
	// 车辆状态：
	// 终端编号,车牌号,定位状态(0定位无效，1定位有效),在线状态(0离线，1在线)
	// ,最新时间,油位,速度,方向,经度,纬度,海拔,状态描述,报警描述,驾驶员,车队名称,位置描述
	private String code;
	private String license;
	private String locateState;
	private String onlineState;
	private String lastTime;
	private String gasPosition;
	private String speed;
	private String orientation;
	private String longitude;
	private String latitude;
	private String altitude;
	private String stateDescription;
	private String alarmDescription;
	private String driver;
	private String groupName;
	private String positionDescription;

	public VehicleStateInfo(String code, String license, String locateState,
			String onlineState, String lastTime, String gasPosition, String speed,
			String orientation, String longitude, String latitude, String altitude,
			String stateDescription, String alarmDescription, String driver,
			String groupName, String positionDescription) {
		this.code = code;
		this.license = license;
		this.locateState = locateState;
		this.onlineState = onlineState;
		this.lastTime = lastTime;
		this.gasPosition = gasPosition;
		this.speed = speed;
		this.orientation = orientation;
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
		this.stateDescription = stateDescription;
		this.alarmDescription = alarmDescription;
		this.driver = driver;
		this.groupName = groupName;
		this.positionDescription = positionDescription;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLocateState() {
		return locateState;
	}

	public void setLocateState(String locateState) {
		this.locateState = locateState;
	}

	public String getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getGasPosition() {
		return gasPosition;
	}

	public void setGasPosition(String gasPosition) {
		this.gasPosition = gasPosition;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public String getAlarmDescription() {
		return alarmDescription;
	}

	public void setAlarmDescription(String alarmDescription) {
		this.alarmDescription = alarmDescription;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPositionDescription() {
		return positionDescription;
	}

	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}

	// 车辆状态：
	// 终端编号,车牌号,定位状态(0定位无效，1定位有效),在线状态(0离线，1在线)
	// ,最新时间,油位,速度,方向,经度,纬度,海拔,状态描述,报警描述,驾驶员,车队名称,位置描述
	@Override
	public String toString() {
		return "车辆状态<br/> [终端编号="
				+ code
				+ ", 车牌号="
				+ license
				+ ", 定位状态="
				+ ("1".equals(locateState) ? "定位有效" : "定位无效")
				+ ", 在线状态="
				+ ("1".equals(onlineState) ? "在线" : "离线")
				+ ", 最新时间="
				+ lastTime
				+ ", 油位="
				+ gasPosition
				+ ", 速度="
				+ speed
				+ ", 方向="
				+ orientation
				+ ", 经度="
				+ longitude
				+ ", 纬度="
				+ latitude
				+ ", 海拔="
				+ altitude
				+ ", 状态描述="
				+ (stateDescription != null ? stateDescription.replaceAll("\\+", ",")
						: "暂无数据")
				+ ", 报警描述="
				+ (alarmDescription != null ? alarmDescription.replaceAll("\\+", ",")
						: "暂无数据") + ", 驾驶员=" + driver + ", 车队名称=" + groupName + ", 位置描述="
				+ positionDescription + "]";
	}
}

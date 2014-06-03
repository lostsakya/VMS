package edu.buaa.vehiclemanagementsystem.model;

public class LocusData {
	// 时间,定位状态(0定位无效，1定位有效),速度,方向,油位,驾驶员姓名,经度,纬度,海拔,报警描述,状态描述,位置描述
	private String time;
	private String locateState;
	private String speed;
	private String oritation;
	private String gasPosition;
	private String driver;
	private String longitude;
	private String latitude;
	private String altitude;
	private String alarmDescription;
	private String stateDescription;
	private String positionDescription;

	public LocusData(String time, String locateState, String speed, String oritation,
			String gasPosition, String driver, String longitude, String latitude,
			String altitude, String alarmDescription, String stateDescription,
			String positionDescription) {
		this.time = time;
		this.locateState = locateState;
		this.speed = speed;
		this.oritation = oritation;
		this.gasPosition = gasPosition;
		this.driver = driver;
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
		this.alarmDescription = alarmDescription;
		this.stateDescription = stateDescription;
		this.positionDescription = positionDescription;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocateState() {
		return locateState;
	}

	public void setLocateState(String locateState) {
		this.locateState = locateState;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getOritation() {
		return oritation;
	}

	public void setOritation(String oritation) {
		this.oritation = oritation;
	}

	public String getGasPosition() {
		return gasPosition;
	}

	public void setGasPosition(String gasPosition) {
		this.gasPosition = gasPosition;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
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

	public String getAlarmDescription() {
		return alarmDescription;
	}

	public void setAlarmDescription(String alarmDescription) {
		this.alarmDescription = alarmDescription;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public String getPositionDescription() {
		return positionDescription;
	}

	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}

	// 时间,定位状态(0定位无效，1定位有效),速度,方向,油位,驾驶员姓名,经度,纬度,海拔,报警描述,状态描述,位置描述
	@Override
	public String toString() {
		return "轨迹数据<br/> [时间=" + time + ", 定位状态="
				+ ("1".equals(locateState) ? "定位无效" : "定位有效") + ", 速度=" + speed + ", 方向="
				+ oritation + ", 油位=" + gasPosition + ", 驾驶员姓名=" + driver + ", 经度="
				+ longitude + ", 纬度=" + latitude + ", 海拔=" + altitude + ", 报警描述="
				+ alarmDescription + ", 状态描述=" + stateDescription + ", 位置描述="
				+ positionDescription + "]";
	}
	// @Override
	// public String toString() {
	// return "LocusData [time=" + time + ", locateState=" + locateState +
	// ", speed="
	// + speed + ", oritation=" + oritation + ", gasPosition=" + gasPosition
	// + ", driver=" + driver + ", longitude=" + longitude + ", latitude="
	// + latitude + ", altitude=" + altitude + ", alarmDescription="
	// + alarmDescription + ", stateDescription=" + stateDescription
	// + ", positionDescription=" + positionDescription + "]";
	// }

}

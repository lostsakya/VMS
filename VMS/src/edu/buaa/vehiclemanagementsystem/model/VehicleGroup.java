package edu.buaa.vehiclemanagementsystem.model;

public class VehicleGroup {
	private String name;
	private String code;

	public VehicleGroup(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Group [name=" + name + ", code=" + code + "]";
	}

}

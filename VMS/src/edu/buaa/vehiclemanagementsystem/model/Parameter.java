package edu.buaa.vehiclemanagementsystem.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Parameter {
	@JSONField(name = "Func")
	private int func;
	@JSONField(name = "Type")
	private int type;
	@JSONField(name = "Data")
	private String data;

	public Parameter() {
	}

	public Parameter(int func, int type, String data) {
		this.func = func;
		this.type = type;
		this.data = data;
	}

	public int getFunc() {
		return func;
	}

	public void setFunc(int func) {
		this.func = func;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Parameters [func=" + func + ", type=" + type + ", data=" + data + "]";
	}
}

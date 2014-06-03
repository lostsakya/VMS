package edu.buaa.vehiclemanagementsystem.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Result implements Serializable {
	@JSONField(name = "ResultID")
	private int resultId;
	@JSONField(name = "DataList")
	private String dataList;
	@JSONField(name = "Count")
	private int count;

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public String getDataList() {
		return dataList;
	}

	public void setDataList(String dataList) {
		this.dataList = dataList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Result [resultId=" + resultId + ", dataList=" + dataList + ", count="
				+ count + "]";
	}

}

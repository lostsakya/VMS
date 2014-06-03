package edu.buaa.vehiclemanagementsystem.controller.parser;

import edu.buaa.vehiclemanagementsystem.model.LocusData;
import edu.buaa.vehiclemanagementsystem.model.Vehicle;
import edu.buaa.vehiclemanagementsystem.model.VehicleGroup;
import edu.buaa.vehiclemanagementsystem.model.VehicleStateInfo;

import android.text.TextUtils;

import java.util.ArrayList;

public class Parser {
	// 车牌号,终端编号,在线状态（0离线，1在线）,速度,是否报警（0正常，1异常）,分组编号,车牌颜色,驾驶员
	public static ArrayList<Vehicle> parseVehicles(String rawData) {
		if (TextUtils.isEmpty(rawData)) {
			return null;
		}
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		String[] data = rawData.split("\\|");
		for (int i = 0; i < data.length; i++) {
			String raw = data[i];
			String[] properties = raw.split(",", -1);
			parseNullData(properties);
			Vehicle vehicle = new Vehicle(properties[0], properties[1], properties[2],
					properties[3], properties[4], properties[5], properties[6],
					properties[7]);
			vehicles.add(vehicle);
		}
		return vehicles;
	}

	public static ArrayList<VehicleGroup> parseGroupInfo(String rawData) {
		if (TextUtils.isEmpty(rawData)) {
			return null;
		}
		ArrayList<VehicleGroup> groups = new ArrayList<VehicleGroup>();
		String[] data = rawData.split("\\|");
		for (int i = 0; i < data.length; i++) {
			String raw = data[i];
			String[] properties = raw.split(",", -1);
			parseNullData(properties);
			VehicleGroup group = new VehicleGroup(properties[0], properties[1]);
			groups.add(group);
		}
		return groups;
	}

	public static ArrayList<VehicleStateInfo> parseStateInfo(String rawData) {
		if (TextUtils.isEmpty(rawData)) {
			return null;
		}
		ArrayList<VehicleStateInfo> stateInfos = new ArrayList<VehicleStateInfo>();
		String[] data = rawData.split("\\|");
		for (int i = 0; i < data.length; i++) {
			String raw = data[i];
			String[] properties = raw.split(",", -1);
			parseNullData(properties);
			VehicleStateInfo stateInfo = new VehicleStateInfo(properties[0],
					properties[1], properties[2], properties[3], properties[4],
					properties[5], properties[6], properties[7], properties[8],
					properties[9], properties[10], properties[11], properties[12],
					properties[13], properties[14], properties[15]);
			stateInfos.add(stateInfo);
		}
		return stateInfos;
	}

	public static ArrayList<LocusData> parseLocusData(String rawData) {
		if (TextUtils.isEmpty(rawData)) {
			return null;
		}
		ArrayList<LocusData> locusDatas = new ArrayList<LocusData>();
		String[] data = rawData.split("\\|");
		for (int i = 0; i < data.length; i++) {
			String raw = data[i];
			String[] properties = raw.split(",", -1);
			parseNullData(properties);
			LocusData locusData = new LocusData(properties[0], properties[1],
					properties[2], properties[3], properties[4], properties[5],
					properties[6], properties[7], properties[8], properties[9],
					properties[10], properties[11]);
			locusDatas.add(locusData);
		}
		return locusDatas;
	}

	private static void parseNullData(String[] strings) {

		for (int i = 0; i < strings.length; i++) {

			if (TextUtils.isEmpty(strings[i])) {
				strings[i] = "暂无数据";
			}
		}

	}

}

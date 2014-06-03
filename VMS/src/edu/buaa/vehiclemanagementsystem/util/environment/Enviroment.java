package edu.buaa.vehiclemanagementsystem.util.environment;

public class Enviroment {

	private static final String SCHEME = "http";
	//TODO
	private static final String HOST = "202.142.21.121";

	// public static final int PORT = 9001;

	private static final int PORT = 80;
	private static final String SERVICE = "/Caradmin/ajax/MainAppFunc.aspx?data=";

	public static final int TIMEOUT = 5000;
	public static final boolean DEBUG = true;

	public static final String URL = SCHEME + "://" + HOST + ":" + PORT + SERVICE;

}

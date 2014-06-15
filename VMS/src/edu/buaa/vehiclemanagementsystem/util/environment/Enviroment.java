package edu.buaa.vehiclemanagementsystem.util.environment;

public class Enviroment {

	private static final String SCHEME = "http";
	public static final String DEFAULT_HOST = "202.142.21.121";
	private static Enviroment instance = new Enviroment();
	public int DEFAULT_PORT = 80;
	private static final String SERVICE = "/Caradmin/ajax/MainAppFunc.aspx?data=";
	public static final int TIMEOUT = 5000;
	public static final boolean DEBUG = true;

	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;

	private String url;

	private Enviroment() {
	}

	public static Enviroment getInstance() {
		return instance;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return SCHEME + "://" + getHost() + ":" + getPort() + SERVICE;
	}

}

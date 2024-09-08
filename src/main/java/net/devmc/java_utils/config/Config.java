package net.devmc.java_utils.config;

public interface Config {
	String getString(String key);
	int getInt(String key);
	boolean getBoolean(String key);
	double getDouble(String key);

	boolean put(String key, Object value);

	boolean remove(String key);
	boolean remove(String key, Object value);

	void save();
}

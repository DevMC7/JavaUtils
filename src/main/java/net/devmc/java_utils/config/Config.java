package net.devmc.java_utils.config;

import java.io.*;

@SuppressWarnings("unused")
public abstract class Config {

	private final File file;

	public Config(File file) {
		this.file = file;
		this.load();
	}

	abstract String getString(String key);
	abstract int getInt(String key);
	abstract boolean getBoolean(String key);
	abstract double getDouble(String key);

	abstract boolean put(String key, Object value);

	abstract boolean remove(String key);
	abstract boolean remove(String key, Object value);

	abstract void load();
	abstract void save();

	public String getPath() {
		return file.getAbsolutePath();
	}
}

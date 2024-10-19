package net.devmc.java_utils.config;

import java.io.*;
import java.nio.file.Path;

@SuppressWarnings("unused")
public abstract class Config extends File {

	public Config(String path) {
		super(Path.of(path).toAbsolutePath().toUri());
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
}

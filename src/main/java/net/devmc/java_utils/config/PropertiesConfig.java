package net.devmc.java_utils.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig implements Config {

	private final String filePath;
	private final Properties properties;

	public PropertiesConfig(String filePath) {
		this.filePath = filePath;
		properties = new Properties();
		try (FileInputStream inputStream = new FileInputStream(filePath)) {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getString(String key) {
		return properties.getProperty(key);
	}

	@Override
	public int getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

	@Override
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(properties.getProperty(key));
	}

	@Override
	public double getDouble(String key) {
		return Double.parseDouble(properties.getProperty(key));
	}

	@Override
	public boolean put(String key, Object value) {
		if (properties.containsKey(key)) {
			return false;
		}
		properties.put(key, value);
		return true;
	}

	@Override
	public boolean remove(String key) {
		if (!properties.containsKey(key)) {
			return false;
		}
		properties.remove(key);
		return true;
	}

	@Override
	public boolean remove(String key, Object value) {
		if (!properties.containsKey(key)) {
			return false;
		}
		properties.remove(key, value);
		return true;
	}

	@Override
	public void save() {
		try (java.io.OutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, null);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
	}
}

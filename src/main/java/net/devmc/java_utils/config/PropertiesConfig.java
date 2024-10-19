package net.devmc.java_utils.config;

import net.devmc.java_utils.config.event.ConfigLoadEvent;
import net.devmc.java_utils.config.event.ConfigReloadEvent;
import net.devmc.java_utils.config.event.ConfigSaveEvent;
import net.devmc.java_utils.event.EventManager;

import java.io.*;
import java.util.Properties;

@SuppressWarnings("unused")
public class PropertiesConfig extends Config {

	private final File file;
	private final Properties properties;

	public EventManager eventManager;

	public PropertiesConfig(File file) {
		super(file);
		this.file = file;
		this.properties = new Properties();
	}

	public PropertiesConfig(String filePath) {
		this(new File(filePath));
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
	public void load() {
		try (FileInputStream inputStream = new FileInputStream(file)) {
			properties.load(inputStream);
			if (eventManager != null) {
				if (properties.isEmpty()) eventManager.call(new ConfigLoadEvent(this));
				else eventManager.call(new ConfigReloadEvent(this));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save() {
		try (OutputStream outputStream = new FileOutputStream(file)) {
			properties.store(outputStream, null);
			if (eventManager != null) eventManager.call(new ConfigSaveEvent(this));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

package net.devmc.java_utils.config;

import net.devmc.java_utils.config.event.ConfigLoadEvent;
import net.devmc.java_utils.config.event.ConfigReloadEvent;
import net.devmc.java_utils.config.event.ConfigSaveEvent;
import net.devmc.java_utils.event.EventManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class YamlConfig extends Config {
	private final Map<String, Object> data = new HashMap<>();
	public EventManager eventManager;

	public YamlConfig(String path) {
		super(path);
	}

	public YamlConfig(File file) {
		super(file.getAbsolutePath());
	}

	@Override
	public String getString(String key) {
		Object value = data.get(key);
		return value != null ? value.toString() : null;
	}

	@Override
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	@Override
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key));
	}

	@Override
	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}

	@Override
	public boolean put(String key, Object value) {
		data.put(key, value);
		return true;
	}

	@Override
	public boolean remove(String key) {
		return data.remove(key) != null;
	}

	@Override
	public boolean remove(String key, Object value) {
		return data.remove(key, value);
	}

	@Override
	public void load() {
		if (!data.isEmpty() && eventManager != null) {
			eventManager.call(new ConfigReloadEvent(this));
			data.clear();
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(this))) {
			String line;
			String currentKey = null;
			Map<String, Object> currentMap = data;

			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("#")) {
					continue;
				}

				if (line.contains(":")) {
					String[] parts = line.split(":", 2);
					String key = parts[0].trim();
					String value = parts[1].trim();

					if (value.isEmpty()) {
						currentKey = key;
						Map<String, Object> nestedMap = new HashMap<>();
						currentMap.put(key, nestedMap);
						currentMap = nestedMap;
					} else {
						currentMap.put(key, parseValue(value));
					}
				} else if (line.startsWith("-")) {
					String listItem = line.substring(1).trim();
					List<Object> list = (List<Object>) currentMap.computeIfAbsent(currentKey, k -> new ArrayList<>());
					list.add(parseValue(listItem));
				} else {
					currentMap = data;
				}
			}
			if (eventManager != null) eventManager.call(new ConfigLoadEvent(this));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load YAML configuration", e);
		}
	}

	@Override
	public void save() {
		if (eventManager != null) eventManager.call(new ConfigSaveEvent(this));
		throw new UnsupportedOperationException("Saving YAML is not implemented.");
	}

	private Object parseValue(String value) {
		if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
			return Boolean.parseBoolean(value);
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ignored) {}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ignored) {}
		return value;
	}
}

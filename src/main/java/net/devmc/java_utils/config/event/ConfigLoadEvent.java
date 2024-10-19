package net.devmc.java_utils.config.event;

import net.devmc.java_utils.config.Config;
import net.devmc.java_utils.event.Event;

public record ConfigLoadEvent(Config config) implements Event {
	@Override
	public String getName() {
		return "ConfigLoadEvent for file " + config.getPath();
	}
}

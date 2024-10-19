package net.devmc.java_utils.event;

public interface Event {
	default String getName() {
		return this.getClass().getSimpleName();
	}
}

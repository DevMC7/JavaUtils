package net.devmc.java_utils.event;

public class CancellableEvent implements Event{
	public boolean cancelled;
	@Override
	public String getName() {
		return "CancellableEvent";
	}
}

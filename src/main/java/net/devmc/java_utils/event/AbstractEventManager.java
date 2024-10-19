package net.devmc.java_utils.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class AbstractEventManager<T> {
	private final Set<Listener> listeners = new HashSet<>();

	public void register(Listener... listeners) {
		this.listeners.addAll(List.of(listeners));
	}

	public void register(Listener listener) {
		listeners.add(listener);
	}

	public void unregister(Listener listener) {
		listeners.remove(listener);
	}

	@SafeVarargs
	public final void call(T... ts) {
		for (T t : ts) {
			if (t != null) this.call(t);
		}
	}

	public void call(T t) {
		for (EventPriority priority : EventPriority.values()) {
			for (Listener listener : listeners) {
				Method[] methods = listener.getClass().getDeclaredMethods();
				for (Method method : methods) {
					if (method.getParameterCount() == 1 && method.getParameterTypes()[0].isAssignableFrom(t.getClass()) && method.isAnnotationPresent(EventHandler.class)) {
						EventHandler eventHandler = method.getAnnotation(EventHandler.class);
						if (eventHandler.priority() != priority) continue;
						if (t instanceof CancellableEvent cancellableEvent && !eventHandler.ignoreCancelled() && cancellableEvent.cancelled)
							continue;
						method.setAccessible(true);
						try {
							method.invoke(listener, t);
						} catch (InvocationTargetException | IllegalAccessException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
	}

}
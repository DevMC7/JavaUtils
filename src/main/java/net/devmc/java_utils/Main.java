package net.devmc.java_utils;

import net.devmc.java_utils.event.Event;
import net.devmc.java_utils.event.EventHandler;
import net.devmc.java_utils.event.EventManager;
import net.devmc.java_utils.event.Listener;

public class Main {

	public static void main(String[] args) {
		EventManager eventHandler = new EventManager();
		Listener listener = new Listener(){
			@EventHandler()
			public void onEvent(Event event){
                System.out.println("Event received: " + event.getName() + " " + event);
            }
		};
		eventHandler.register(listener);
		eventHandler.call(new Event() {
			@Override
			public String getName() {
				return "test";
			}
		});
    }
}

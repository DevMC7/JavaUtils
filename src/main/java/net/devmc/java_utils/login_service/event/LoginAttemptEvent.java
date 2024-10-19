package net.devmc.java_utils.login_service.event;

import net.devmc.java_utils.event.Event;
import net.devmc.java_utils.login_service.Account;

public record LoginAttemptEvent(Account account, boolean failed) implements Event {
	@Override
	public String getName() {
		return "LoginAttemptEvent for user " + account.getUsername() + " failed=" + failed;
	}
}

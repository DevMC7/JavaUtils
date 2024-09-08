package net.devmc.java_utils.login_service.event;

import net.devmc.java_utils.event.Event;
import net.devmc.java_utils.login_service.Account;

public record AccountCreationEvent(Account account, boolean failed) implements Event {
}

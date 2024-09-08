package net.devmc.java_utils.login_service;

import net.devmc.java_utils.event.EventManager;
import net.devmc.java_utils.login_service.event.AccountAuthenticationEvent;
import net.devmc.java_utils.login_service.event.AccountCreationEvent;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class LoginService {

	private final UUID uuid;
	private final String directory;
	private final EventManager eventManager;

	private final List<Account> users = new ArrayList<>();

	public LoginService(String directory, EventManager eventManager) {
		this.uuid = UUID.randomUUID();
		this.directory = directory;
		this.eventManager = eventManager;
		if (!new File(directory).exists() && !new File(directory).mkdirs()) throw new RuntimeException("Failed to create users directory");
		File[] files = new File(directory).listFiles();
		if (files == null) return;
		for (File file : files) {
			try (Scanner scanner = new Scanner(file)) {
				// TODO: Import user
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public LoginService(String directory) {
		this(directory, new EventManager());
	}

	public boolean register(Account account, PrintStream out) {
		if (out == null) {
			out = System.out;
		}
		boolean failed;
		if (users.contains(account)) {
			failed = true;
		}
		users.add(account);
		try {
			File file = new File(directory + "\\" + account.getUsername() + "." + account.getUuid().toString() + ".dat");
			if (file.exists()) {
				out.println("Failed to create user: User file already exists");
				failed = true;
			}
			if (!file.createNewFile()) {
				out.println("Failed to create account file");
				failed = true;
			}
			try (FileWriter writer = new FileWriter(file)) {
				// TODO: Save user
				failed = false;
			}
		} catch (IOException e) {
			out.println("An unexpected error occurred while creating the user account");
			failed = true;
		}
		eventManager.call(new AccountCreationEvent(account, failed));
		return !failed;
	}

	public boolean register(Account account) {
		return register(account, System.out);
	}

	public boolean register(final String username, final UUID uuid, final String password) {
		return register(new Account(username, uuid, password));
	}

	public AuthenticationResult authenticate(Account account, final String username, final UUID uuid, final String password) {
		AuthenticationResult result = account.authenticate(username, uuid, password);
		if (result == AuthenticationResult.SUCCESS) {
			eventManager.call(new AccountAuthenticationEvent(account));
		}
		return result;
	}
}

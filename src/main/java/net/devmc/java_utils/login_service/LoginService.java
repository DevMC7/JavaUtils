package net.devmc.java_utils.login_service;

import net.devmc.java_utils.event.EventManager;
import net.devmc.java_utils.login_service.event.AccountAuthenticationEvent;
import net.devmc.java_utils.login_service.event.AccountCreationEvent;
import net.devmc.java_utils.login_service.event.LoginAttemptEvent;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class LoginService {

	private final UUID uuid;
	private final String directory;
	public EventManager eventManager;

	private final List<Account> users = new ArrayList<>();

	public LoginService(String directory) {
		this.uuid = UUID.randomUUID();
		this.directory = directory;
		if (!new File(directory).exists() && !new File(directory).mkdirs()) throw new RuntimeException("Failed to create users directory");
		File[] files = new File(directory).listFiles();
		if (files == null) return;
		for (File file : files) {
			try (Scanner scanner = new Scanner(file)) {
				users.add(new Account(scanner.nextLine(), UUID.fromString(scanner.nextLine()), scanner.nextLine()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public boolean register(Account account, PrintStream out) {
		if (out == null) {
			out = System.out;
		}
		boolean failed = users.contains(account);
		try {
			if (!failed) {
				File file = new File(directory + "\\" + account.getUsername() + "." + account.getUuid().toString() + ".dat");
				if (file.exists()) {
					out.println("Failed to create user: User file already exists");
					failed = true;
				} else if (!file.createNewFile()) {
					out.println("Failed to create account file");
					failed = true;
				} else {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
						writer.append(account.getUsername());
						writer.newLine();
						writer.append(String.valueOf(account.getUuid()));
						writer.newLine();
						writer.append(account.password);
						users.add(account);
					}
				}
			}
		} catch (IOException e) {
			out.println("An unexpected error occurred while creating the user account");
			failed = true;
		}
		if (eventManager != null) eventManager.call(new AccountCreationEvent(account, failed));
		return !failed;
	}

	public boolean register(Account account) {
		return register(account, System.out);
	}

	public boolean register(final String username, final UUID uuid, final String password) {
		return register(new Account(username, uuid, password));
	}

	public AuthenticationResult authenticate(Account account, final String username, final UUID uuid, final String password) {
		if (!users.contains(account)) return AuthenticationResult.USER_NOT_FOUND;
		AuthenticationResult result = account.authenticate(username, uuid, password);
		if (eventManager != null) eventManager.call(new LoginAttemptEvent(account, result != AuthenticationResult.SUCCESS));
		if (result != AuthenticationResult.SUCCESS) {
			if (eventManager != null) eventManager.call(new AccountAuthenticationEvent(account));
		}
		return result;
	}
}

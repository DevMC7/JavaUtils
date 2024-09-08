package net.devmc.java_utils.login_service;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.UUID;

@SuppressWarnings("unused")
public class Account implements Serializable {

	private final String username;
	private final UUID uuid;
	private final String password;

	private boolean authenticated = false;

	public Account (PrintStream out, InputStream in) {
		Scanner scanner = new Scanner(in);
		out.println("Enter username: ");
		String username = scanner.nextLine();

		out.println("Enter user ID: ");
		UUID uuid = UUID.fromString(scanner.nextLine());

		out.println("Enter password: ");
		String password = scanner.nextLine();

		this.username = username;
		this.uuid = uuid;
		this.password = password;
	}

	public Account (Scanner scanner) {
		System.out.println("Enter username: ");
		String username = scanner.nextLine();

		System.out.println("Enter user ID: ");
		UUID uuid = UUID.fromString(scanner.nextLine());

		System.out.println("Enter password: ");
		String password = scanner.nextLine();

		this.username = username;
		this.uuid = uuid;
		this.password = password;
	}

	public Account () {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = scanner.nextLine();

		System.out.println("Enter user ID: ");
		UUID uuid = UUID.fromString(scanner.nextLine());

		System.out.println("Enter password: ");
		String password = scanner.nextLine();

		this.username = username;
		this.uuid = uuid;
		this.password = password;
	}

	public Account (String username, UUID uuid, String password) {
		this.username = username;
		this.uuid = uuid;
		this.password = password;
	}

	AuthenticationResult authenticate(final String username, final UUID uuid, final String password) {
		if (this.authenticated) {
			return AuthenticationResult.ALREADY_LOGGED_IN;
		}
		if (username.isEmpty()) {
			return AuthenticationResult.USERNAME_NOT_PROVIDED;
		}
		if (password.isEmpty()) {
			return AuthenticationResult.PASSWORD_NOT_PROVIDED;
		}
		if (password.equals(this.password)) {
			authenticated = true;
			return AuthenticationResult.SUCCESS;
		} else {
			return AuthenticationResult.WRONG_PASSWORD;
		}
	}

	public String getUsername() {
		return this.username;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void logout() {
		authenticated = false;
	}
}

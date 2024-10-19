package net.devmc.java_utils.login_service;

public enum AuthenticationResult {

	SUCCESS ("success"),
	USER_NOT_FOUND("user not found"),
	WRONG_PASSWORD ("wrong password"),
	USERNAME_NOT_PROVIDED ("username not provided"),
	PASSWORD_NOT_PROVIDED ("password not provided"),
	ALREADY_LOGGED_IN ("user already logged in");

	AuthenticationResult(String id) {
	}
}

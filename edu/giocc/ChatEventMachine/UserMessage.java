package edu.giocc.ChatEventMachine;

import edu.giocc.MinDispatch.Event;

public class UserMessage extends Event {
	public User user;
	public String message;

	public UserMessage(User user, String message) {
		this.user = user;
		this.message = message;
	}
}

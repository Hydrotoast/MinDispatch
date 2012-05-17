package edu.giocc.ChatEventMachine;

import edu.giocc.MinDispatch.Event;

public class UserDeparture extends Event {
	public User user;
	
	public UserDeparture(User user) {
		this.user = user;
	}
}

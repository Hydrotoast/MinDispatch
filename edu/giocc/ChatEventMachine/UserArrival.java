package edu.giocc.ChatEventMachine;

import edu.giocc.MinDispatch.Event;

public class UserArrival extends Event {
	public User user;
	
	public UserArrival(User user) {
		this.user = user;
	}
}

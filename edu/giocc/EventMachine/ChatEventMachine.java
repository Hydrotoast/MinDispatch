package edu.giocc.EventMachine;

import edu.giocc.MinDispatch.Event;
import edu.giocc.MinDispatch.EventDispatcher;
import edu.giocc.MinDispatch.Handler;

public class ChatEventMachine {
	private static class User {
		public String name;
		
		public User(String name) {
			this.name = name;
		}
	}
	
	private static class UserArrival extends Event {
		public User user;
		
		public UserArrival(User user) {
			this.user = user;
		}
	}
	
	private static class UserDeparture extends Event {
		public User user;
		
		public UserDeparture(User user) {
			this.user = user;
		}
	}
	
	private static class UserMessage extends Event {
		public User user;
		public String message;

		public UserMessage(User user, String message) {
			this.user = user;
			this.message = message;
		}
	}

	
	public static void main(String[] args) {
		EventDispatcher dispatcher = new EventDispatcher();
		dispatcher.registerChannel(UserArrival.class, new Handler() {
			@Override
			public void dispatch(Event evt) {
				
			}
		});
		
		dispatcher.registerChannel(UserArrival.class, new Handler() {
			@Override
			public void dispatch(Event evt) {
				UserArrival arrival = (UserArrival)evt;
				
				System.out.println(arrival.user.name + " has entered the room.");
			}
		});
		
		dispatcher.registerChannel(UserDeparture.class, new Handler() {
			@Override
			public void dispatch(Event evt) {
				UserDeparture departure = (UserDeparture)evt;
				
				System.out.println(departure.user.name + " has left the room.");
			}
		});
		
		dispatcher.registerChannel(UserMessage.class, new Handler() {
			@Override
			public void dispatch(Event evt) {
				UserMessage message = (UserMessage)evt;
				String userMessage = String.format("%s: %s", message.user.name, message.message);
				System.out.println(userMessage);
			}
		});
		
		User foo = new User("foo");
		User bar = new User("bar");
		dispatcher.dispatch(new UserArrival(foo));
		dispatcher.dispatch(new UserArrival(bar));
		dispatcher.dispatch(new UserMessage(foo, "hello, bar!"));
		dispatcher.dispatch(new UserMessage(bar, "hello, foo!"));
		dispatcher.dispatch(new UserMessage(foo, "goodbye, bar!"));
		dispatcher.dispatch(new UserDeparture(foo));
	}
}

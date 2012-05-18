package edu.giocc.EventMachine;

import edu.giocc.MinDispatch.Event;
import edu.giocc.MinDispatch.EventDispatcher;
import edu.giocc.MinDispatch.Handler;

public class ChatAIEventMachine {
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
				
				if (message.user.name.compareTo("Phoenix Wright") != 0) {
					User wright = new User("Phoenix Wright");
					EventQueue.getInstance().enqueue(new UserMessage(wright, "I object!"));
				}
				
				System.out.println(userMessage);
			}
		});
		
		User foo = new User("foo");
		User bar = new User("bar");
		EventQueue.getInstance().enqueue(new UserArrival(foo));
		EventQueue.getInstance().enqueue(new UserArrival(bar));
		EventQueue.getInstance().enqueue(new UserMessage(foo, "hello, bar!"));
		EventQueue.getInstance().enqueue(new UserMessage(bar, "hello, foo!"));
		EventQueue.getInstance().enqueue(new UserMessage(foo, "goodbye, bar!"));
		EventQueue.getInstance().enqueue(new UserDeparture(foo));
		
		while (!EventQueue.getInstance().isEmpty()) {
			dispatcher.dispatch(EventQueue.getInstance().dequeue());
		}
	}
}

package edu.giocc.EventMachine;

import java.util.ArrayList;

import edu.giocc.MinDispatch.Event;
import edu.giocc.MinDispatch.EventDispatcher;
import edu.giocc.MinDispatch.Handler;

public class ChatEventMachine {
	private static class ChatState {
		private EventDispatcher dispatcher;
		private ArrayList<User> users;
		
		public ChatState(EventDispatcher dispatcher) {
			this.dispatcher = dispatcher;
			this.users = new ArrayList<User>();
		}
		
		public void broadcast(User user, String userMessage) {
			for (User recipient : users) {
				if (!recipient.equals(user))
					recipient.sendMessage(userMessage);
			}
		}
		
		public void addUser(User user) {
			users.add(user);
		}
		
		public void removeUser(User user) {
			users.remove(user);
		}
	}
	
	private static class User {
		public String name;
		
		public User(String name) {
			this.name = name;
		}
		
		public void sendMessage(String userMessage) {
			String message = String.format("%s received message: %s", name, userMessage);
			System.out.println(message);
		}
	}
	
	private static class UserArrival extends Event {
		public ChatState state;
		public User user;
		
		public UserArrival(ChatState state, User user) {
			this.state = state;
			this.user = user;
		}
	}
	
	private static class UserDeparture extends Event {
		public ChatState state;
		public User user;
		
		public UserDeparture(ChatState state, User user) {
			this.state = state;
			this.user = user;
		}
	}
	
	private static class UserMessage extends Event {
		public ChatState state;
		public User user;
		public String message;

		public UserMessage(ChatState state, User user, String message) {
			this.state = state;
			this.user = user;
			this.message = message;
		}
	}
	
	public static void main(String[] args) {
		EventDispatcher dispatcher = new EventDispatcher();
		ChatState state = new ChatState(dispatcher);
		
		dispatcher.registerChannel(UserArrival.class, new Handler() {
			@Override
			public void dispatch(Event evt) {
				UserArrival arrival = (UserArrival)evt;
				arrival.state.addUser(arrival.user);
				
				System.out.println(arrival.user.name + " has entered the room.");
			}
		});
		
		dispatcher.registerChannel(UserDeparture.class, new Handler() {
			@Override
			public void dispatch(Event evt) {
				UserDeparture departure = (UserDeparture)evt;
				departure.state.removeUser(departure.user);
				
				System.out.println(departure.user.name + " has left the room.");
			}
		});
		
		dispatcher.registerChannel(UserMessage.class, new Handler() {
			@Override
			public void dispatch(Event evt) {
				UserMessage message = (UserMessage)evt;
				String userMessage = String.format("%s: %s", message.user.name, message.message);
				System.out.println(userMessage);

				message.state.broadcast(message.user, userMessage);
			}
		});
		
		// Users require their own message queue for distributed computing
		User foo = new User("foo");
		User bar = new User("bar");
		dispatcher.dispatch(new UserArrival(state, foo));
		dispatcher.dispatch(new UserArrival(state, bar));
		dispatcher.dispatch(new UserMessage(state, foo, "hello, bar!"));
		dispatcher.dispatch(new UserMessage(state, bar, "hello, foo!"));
		dispatcher.dispatch(new UserMessage(state, foo, "goodbye, bar!"));
		dispatcher.dispatch(new UserDeparture(state, foo));
		dispatcher.dispatch(new UserMessage(state, bar, "foo?"));
	}
}

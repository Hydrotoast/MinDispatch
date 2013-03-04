package edu.giocc.EventMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import edu.giocc.MinDispatch.Event;
import edu.giocc.MinDispatch.EventDispatcher;
import edu.giocc.MinDispatch.Handler;

public class ChatAIEventMachine {
	private static class ChatState {
		private ArrayList<User> users;

		public ChatState() {
			this.users = new ArrayList<User>();
		}

		public synchronized void broadcast(Event evt) {
			for (User recipient : users)
				recipient.dispatch(evt);
		}

		public void addUser(User user) {
			users.add(user);
		}

		public void removeUser(User user) {
			users.remove(user);
		}
	}

	private static class ChatHandler extends Handler {
		protected ChatState state;

		public ChatHandler(ChatState state) {
			this.state = state;
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

	private static class User {
		public Queue<Event> eventQueue;
		public String name;

		public User(Queue<Event> eventQueue, String name) {
			this.eventQueue = eventQueue;
			this.name = name;
		}

		public void dispatch(Event evt) {
			if (evt.getClass() == UserMessage.class) {
				UserMessage message = (UserMessage) evt;
				processMessage(message.user, message.message);
			}
		}

		public void processMessage(User user, String userMessage) {
			if (user.equals(this))
				return;
		}

		public void sendMessage(String message) {
			eventQueue.add(new UserMessage(this, message));
		}
	}

	private static class ChatAI extends User {
		private static class ChatAIHandler extends Handler {
			public Queue<Event> eventQueue;
			public ChatAI ai;

			public ChatAIHandler(Queue<Event> eventQueue, ChatAI ai) {
				this.eventQueue = eventQueue;
				this.ai = ai;
			}
		}

		private static class AIEvent extends Event {
			public String message;
		}

		private static class Greeting extends AIEvent {
			public Greeting() {
				message = "Hello to you";
			}
		}

		private static class Farewell extends AIEvent {
			public Farewell() {
				message = "Goodbye to you";
			}
		}

		private static class AnswerToLife extends AIEvent {
			public AnswerToLife() {
				message = "42";
			}
		}

		private EventDispatcher responseDispatcher;
		private HashMap<String, Event> respMap;

		public ChatAI(Queue<Event> eventQueue, String name) {
			super(eventQueue, name);
			this.responseDispatcher = new EventDispatcher();
			this.respMap = new HashMap<String, Event>();
			respMap.put("hello", new Greeting());
			respMap.put("goodbye", new Farewell());
			respMap.put("answer to life", new AnswerToLife());

			Handler AIHandler = new ChatAIHandler(eventQueue, this) {
				@Override
				public void dispatch(Event evt) {
					AIEvent event = (AIEvent) evt;
					eventQueue.add(new UserMessage(ChatAI.this, event.message));
				}
			};

			responseDispatcher.registerChannel(Greeting.class, AIHandler);
			responseDispatcher.registerChannel(Farewell.class, AIHandler);
			responseDispatcher.registerChannel(AnswerToLife.class, AIHandler);
		}

		public void respond(User user, String request) {
			Event evt = respMap.get(request);
			if (evt != null) {
				responseDispatcher.dispatch(evt);
			}
		}

		@Override
		public void processMessage(User user, String userMessage) {
			respond(user, userMessage);
		}
	}

	public static void setup(EventDispatcher dispatcher, ChatState state) {
		dispatcher.registerChannel(UserArrival.class, new ChatHandler(state) {
			@Override
			public void dispatch(Event evt) {
				UserArrival arrival = (UserArrival) evt;
				state.addUser(arrival.user);

				System.out
						.println(arrival.user.name + " has entered the room.");
			}
		});

		dispatcher.registerChannel(UserDeparture.class, new ChatHandler(state) {
			@Override
			public void dispatch(Event evt) {
				UserDeparture departure = (UserDeparture) evt;
				state.removeUser(departure.user);

				System.out.println(departure.user.name + " has left the room.");
			}
		});

		dispatcher.registerChannel(UserMessage.class, new ChatHandler(state) {
			@Override
			public void dispatch(Event evt) {
				UserMessage message = (UserMessage) evt;
				String userMessage = String.format("%s: %s", message.user.name,
						message.message);
				System.out.println(userMessage);

				// Broadcast messages
				state.broadcast(message);
			}
		});
	}

	public static void main(String[] args) {
		EventDispatcher dispatcher = new EventDispatcher();
		ChatState state = new ChatState();
		Queue<Event> eventQueue = new LinkedList<Event>();
		
		setup(dispatcher, state);

		// Initialize user and AI
		User me = new User(eventQueue, "me");
		User ai = new ChatAI(eventQueue, "ai");
		dispatcher.dispatch(new UserArrival(me));
		dispatcher.dispatch(new UserArrival(ai));
		
		Scanner s = new Scanner(System.in);
		String line = "";
		do {
			// Parse events from the user
			line = s.nextLine();
			me.sendMessage(line);
			
			// Handle events
			while (!eventQueue.isEmpty()) {
				Event evt = eventQueue.remove();
				dispatcher.dispatch(evt);
			}
		} while (!line.equals("goodbye") && s.hasNext());
		s.close();
		
		dispatcher.dispatch(new UserDeparture(me));
		dispatcher.dispatch(new UserDeparture(ai));
	}
}

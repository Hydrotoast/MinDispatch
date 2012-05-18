package edu.giocc.EventMachine;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.giocc.MinDispatch.Event;

public class EventQueue {
	private static EventQueue instance;
	private Queue<Event> eventQueue;
	
	private EventQueue() {
		eventQueue = new LinkedList<Event>();
	}
	
	public static EventQueue getInstance() {
		if (instance == null)
			instance = new EventQueue();
		
		return instance;
	}
	
	public void enqueue(Event e) {
		eventQueue.add(e);
	}
	
	public Event peek() {
		return eventQueue.peek();
	}
	
	public Event dequeue() throws NoSuchElementException {
		return eventQueue.remove();
	}
	
	public boolean isEmpty() {
		return eventQueue.isEmpty();
	}
	
	public int size() {
		return eventQueue.size();
	}
}

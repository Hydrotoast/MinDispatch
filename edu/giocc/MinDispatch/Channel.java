package edu.giocc.MinDispatch;

/**
 * Channels push messages downstream a data flow.
 * 
 * @author Gio Carlo Cielo
 * 
 * @param <E> the type of messages pushed by this channel.
 */
public interface Channel<E extends Message> {
	/**
	 * Pushes messages downstream a data flow.
	 * 
	 * @param content the message to dispatch.
	 */
	public void dispatch(E message);
}

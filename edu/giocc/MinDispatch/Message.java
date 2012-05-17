package edu.giocc.MinDispatch;

/**
 * Content contains the data necessary for all messages.
 * 
 * @author Gio Carlo Cielo
 */
public interface Message {
	/**
	 * Retrieves the message type for dynamic routers.
	 * 
	 * @return the message type.
	 */
	public Class<? extends Message> getType();
}

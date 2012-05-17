package edu.giocc.MinDispatch;

/**
 * Router with a rule-based dictionary for determining routing points.
 * 
 * @author Gio Carlo Cielo
 * 
 * @param <E> the type of messages contained by this router.
 */
public interface DynamicRouter<E extends Message> {
	/**
	 * Inserts a channel into the recipient list with the respective rule.
	 * 
	 * @param channel the channel to add.
	 * @param content the content rule to add.
	 */
	public void registerChannel(Class<? extends E> contentType,
			Channel<? extends E> channel);

	/**
	 * Dispatches the specified message to the appropriate channel.
	 * 
	 * @param mesage the message to dispatch.
	 */
	public abstract void dispatch(E content);
}
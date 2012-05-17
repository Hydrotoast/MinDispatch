package edu.giocc.MinDispatch;

public class Event implements Message {

	@Override
	public Class<? extends Message> getType() {
		return getClass();
	}

}

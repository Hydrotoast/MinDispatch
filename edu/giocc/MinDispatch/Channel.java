// This file is part of MinDispatch
// Copyright (C) 2012 Gio Carlo Cielo Borje
//
// MinDispatch is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// MinDispatch is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

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

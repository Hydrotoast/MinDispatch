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
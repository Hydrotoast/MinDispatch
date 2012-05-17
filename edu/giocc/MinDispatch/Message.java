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

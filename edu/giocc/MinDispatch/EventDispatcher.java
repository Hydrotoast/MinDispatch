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

import java.util.HashMap;
import java.util.Map;

public class EventDispatcher implements DynamicRouter<Event> {
	private Map<Class<? extends Event>, Handler> handlers;

	public EventDispatcher() {
		handlers = new HashMap<Class<? extends Event>, Handler>();
	}

	@Override
	public void registerChannel(Class<? extends Event> contentType,
			Channel<? extends Event> channel) {
		handlers.put(contentType, (Handler) channel);
	}

	@Override
	public void dispatch(Event content) {
		handlers.get(content.getClass()).dispatch(content);
	}
}

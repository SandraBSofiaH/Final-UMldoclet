/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.project3;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Solver3 {

	private final Map<TaskAttribute, Value> values = new LinkedHashMap<TaskAttribute, Value>();

	private final LoadPlanable loadPlanable;

	public Solver3(LoadPlanable loadPlanable) {
		this.loadPlanable = loadPlanable;
	}

	public void setData(TaskAttribute attribute, Value value) {
		final Value previous = values.remove(attribute);
		if (previous != null && attribute == TaskAttribute.START) {
			final Instant previousInstant = (Instant) previous;
			if (previousInstant.compareTo((Instant) value) > 0) {
				value = previous;
			}
		}
		values.put(attribute, value);
		if (values.size() > 2) {
			removeFirstElement();
		}
		assert values.size() <= 2;

	}

	private void removeFirstElement() {
		final Iterator<Entry<TaskAttribute, Value>> it = values.entrySet().iterator();
		it.next();
		it.remove();
	}

	public Value getData(TaskAttribute attribute) {
		Value result = values.get(attribute);
		if (result == null) {
			if (attribute == TaskAttribute.END) {
				return computeEnd();
			}
			if (attribute == TaskAttribute.START) {
				return computeStart();
			}
			return LoadInDays.inDay(1);
			// throw new UnsupportedOperationException(attribute.toString());
		}
		return result;
	}

	private Instant computeEnd() {
		Instant current = (Instant) values.get(TaskAttribute.START);
		int fullLoad = ((Load) values.get(TaskAttribute.LOAD)).getFullLoad();
		while (fullLoad > 0) {
			fullLoad -= loadPlanable.getLoadAt(current);
			current = current.increment();
		}
		return current.decrement();
	}

	private Instant computeStart() {
		Instant current = (Instant) values.get(TaskAttribute.END);
		int fullLoad = ((Load) values.get(TaskAttribute.LOAD)).getFullLoad();
		while (fullLoad > 0) {
			fullLoad -= loadPlanable.getLoadAt(current);
			current = current.decrement();
		}
		return current.increment();
	}

}
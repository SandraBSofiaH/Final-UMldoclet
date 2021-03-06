/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  https://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * https://plantuml.com/patreon (only 1$ per month!)
 * https://plantuml.com/paypal
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
package net.sourceforge.plantuml.activitydiagram3.gtile;

import java.util.Arrays;
import java.util.List;

public abstract class GAbstractConnection implements GConnection {

	protected final GPoint gpoint1;
	protected final GPoint gpoint2;

	public GAbstractConnection(GPoint gpoint1, GPoint gpoint2) {
		this.gpoint1 = gpoint1;
		this.gpoint2 = gpoint2;
	}

	@Override
	public String toString() {
		return "[" + gpoint1 + "]->[" + gpoint2 + "]";
	}

	@Override
	final public List<GPoint> getHooks() {
		return Arrays.asList(gpoint1, gpoint2);
	}

}

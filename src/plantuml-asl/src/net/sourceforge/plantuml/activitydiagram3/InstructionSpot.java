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
package net.sourceforge.plantuml.activitydiagram3;

import java.util.Objects;

import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileKilled;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class InstructionSpot extends MonoSwimable implements Instruction {

	private boolean killed = false;
	private final LinkRendering inlinkRendering;
	private final String spot;
	private final HColor color;

	public boolean containsBreak() {
		return false;
	}

	public InstructionSpot(String spot, HColor color, LinkRendering inlinkRendering, Swimlane swimlane) {
		super(swimlane);
		this.spot = spot;
		this.inlinkRendering = Objects.requireNonNull(inlinkRendering);
		this.color = color;
	}

	public Ftile createFtile(FtileFactory factory) {
		Ftile result = factory.spot(getSwimlaneIn(), spot, color);
		result = eventuallyAddNote(factory, result, result.getSwimlaneIn());
		if (killed) {
			return new FtileKilled(result);
		}
		return result;
	}

	public CommandExecutionResult add(Instruction other) {
		throw new UnsupportedOperationException();
	}

	final public boolean kill() {
		this.killed = true;
		return true;
	}

	public LinkRendering getInLinkRendering() {
		return inlinkRendering;
	}

}

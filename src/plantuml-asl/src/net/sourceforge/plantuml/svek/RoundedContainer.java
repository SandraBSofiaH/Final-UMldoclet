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
package net.sourceforge.plantuml.svek;

import java.awt.geom.Dimension2D;

import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public final class RoundedContainer {

	private final Dimension2D dim;
	private final double titleHeight;
	private final double attributeHeight;
	private final HColor borderColor;
	private final HColor backColor;
	private final HColor imgBackcolor;
	private final UStroke stroke;
	private final double rounded;
	private final double shadowing;

	public RoundedContainer(Dimension2D dim, double titleHeight, double attributeHeight, HColor borderColor,
			HColor backColor, HColor imgBackcolor, UStroke stroke, double rounded, double shadowing) {
		if (dim.getWidth() == 0) {
			throw new IllegalArgumentException();
		}
		this.rounded = rounded;
		this.dim = dim;
		this.imgBackcolor = imgBackcolor;
		this.titleHeight = titleHeight;
		this.borderColor = borderColor;
		this.backColor = backColor;
		this.attributeHeight = attributeHeight;
		this.stroke = stroke;
		this.shadowing = shadowing;
	}

	public void drawU(UGraphic ug) {
		ug = ug.apply(backColor.bg()).apply(borderColor);
		final URectangle rect = new URectangle(dim.getWidth(), dim.getHeight()).rounded(rounded);
		rect.setDeltaShadow(shadowing);
		ug.apply(stroke).draw(rect);

		final double yLine = titleHeight + attributeHeight;

		ug = ug.apply(imgBackcolor.bg());

		final double thickness = stroke.getThickness();

		final URectangle inner = new URectangle(dim.getWidth() - 4 * thickness,
				dim.getHeight() - titleHeight - 4 * thickness - attributeHeight).rounded(rounded);
		ug.apply(imgBackcolor).apply(new UTranslate(2 * thickness, yLine + 2 * thickness)).draw(inner);

		if (titleHeight > 0) {
			ug.apply(stroke).apply(UTranslate.dy(yLine)).draw(ULine.hline(dim.getWidth()));
		}

		if (attributeHeight > 0) {
			ug.apply(stroke).apply(UTranslate.dy(yLine - attributeHeight)).draw(ULine.hline(dim.getWidth()));
		}

	}
}

/*
 * RectTool.java
 * 
 * Author:
 *       Alessio Parma <alessio.parma@gmail.com>
 * 
 * Copyright (C) 2011 by Alessio Parma <alessio.parma@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package zora.tools.shapes;

import zora.core.Canvas;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsRectItem;
import com.trolltech.qt.gui.QPen;

public final class RectTool extends ShapeTool {

	/*
	 * FIELDS
	 */

	private QRectF rect = new QRectF();
	private QGraphicsRectItem rectItem;

	/*
	 * CONSTRUCTOR
	 */

	public RectTool(Canvas canvas) {
		super(canvas);
	}

	/*
	 * PUBLIC METHODS
	 */

	@Override
	public void addShapeToScene(QPointF currPos) {
		rect.setTopLeft(currPos);
		rect.setBottomRight(currPos);
		rectItem = scene().addRect(rect);
		rectItem.setPen(new QPen(fgColor(), lineWidth()));
	}

	@Override
	public void moveShapeOnScene(QPointF currPos) {
		double newWidth = currPos.x() - startPos().x();
		double newHeight = currPos.y() - startPos().y();

		if (newWidth < 0) {
			rect.setLeft(currPos.x());
			rect.setWidth(-newWidth);
		} else {
			rect.setWidth(newWidth);
		}

		if (newHeight < 0) {
			rect.setTop(currPos.y());
			rect.setHeight(-newHeight);
		} else {
			rect.setHeight(newHeight);
		}

		rectItem.setRect(rect);
	}

	@Override
	public void removeShapeFromScene(QPointF currPos) {
		scene().removeItem(rectItem);
	}

	@Override
	public void drawShapeOnImage(QPointF currPos) {
		image().setPen(new QPen(bgColor(), lineWidth()));
		image().drawRect(rect);
	}

}

/*
 * ShapeTool.java
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
import zora.tools.Tool;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QPainter.CompositionMode;

public abstract class ShapeTool extends Tool {

	/*
	 * FIELDS
	 */

	private QPointF startPos = new QPointF();

	private int lineWidth = 2;

	/*
	 * CONSTRUCTOR
	 */

	public ShapeTool(Canvas canvas) {
		super(canvas);
	}

	/*
	 * PROPERTIES
	 */

	protected QPointF startPos() {
		return startPos;
	}

	protected int lineWidth() {
		return lineWidth;
	}

	/*
	 * PUBLIC METHODS
	 */

	public abstract void addShapeToScene(QPointF currPos);

	public abstract void moveShapeOnScene(QPointF currPos);

	public abstract void removeShapeFromScene(QPointF currPos);

	public abstract void drawShapeOnImage(QPointF currPos);

	@Override
	public void onPointerMove(QPointF ptrPos) {
		moveShapeOnScene(ptrPos);
	}

	@Override
	public void onPointerPress(QPointF ptrPos) {
		startPos.setX(ptrPos.x());
		startPos.setY(ptrPos.y());

		addShapeToScene(ptrPos);
	}

	@Override
	public void onPointerRelease(QPointF ptrPos) {
		removeShapeFromScene(ptrPos);

		image().save();
		image().setCompositionMode(CompositionMode.CompositionMode_Source);
		drawShapeOnImage(ptrPos);
		image().restore();

		canvas().refresh(new QRectF(startPos, ptrPos));
	}

	@Override
	public void dispose() {

	}
}

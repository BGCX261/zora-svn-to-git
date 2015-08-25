/*
 * BrushTool.java
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

package zora.tools.brushes;

import java.util.Vector;

import zora.core.Canvas;
import zora.tools.Tool;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;

public abstract class BrushTool extends Tool {

	private QPointF lastPos = new QPointF();

	/*
	 * CONSTRUCTOR
	 */

	public BrushTool(Canvas canvas) {
		super(canvas);
	}

	/*
	 * PROPERTIES
	 */

	protected QPointF lastPos() {
		return lastPos;
	}

	/*
	 * PUBLIC METHODS
	 */

	public abstract void drawBrushOnImage(QPointF pos);

	@Override
	public void onPointerMove(QPointF ptrPos) {
		Vector<QPointF> points = linearInterpolation(lastPos, ptrPos);

		for (QPointF p : points) {
			drawBrushAt(p);
		}

		canvas().refresh(new QRectF(lastPos, ptrPos));

		lastPos.setX(ptrPos.x());
		lastPos.setY(ptrPos.y());
	}

	@Override
	public void onPointerPress(QPointF ptrPos) {
		lastPos.setX(ptrPos.x());
		lastPos.setY(ptrPos.y());

		drawBrushAt(lastPos);

		canvas().refresh(new QRectF(lastPos, ptrPos));
	}

	@Override
	public void onPointerRelease(QPointF ptrPos) {
		// Do nothing
	}

	@Override
	public void dispose() {

	}

	/*
	 * PRIVATE METHODS
	 */

	private void drawBrushAt(QPointF pos) {
		image().save();
		image().setPen(fgColor());
		drawBrushOnImage(pos);
		image().restore();
	}

	private Vector<QPointF> linearInterpolation(QPointF a, QPointF b) {
		// Vector holding interpolated points.
		Vector<QPointF> points = new Vector<QPointF>();

		// If given points are equal, we return an empty vector.
		if (a.equals(b)) {
			return points;
		}

		double deltaX = b.x() - a.x();
		double sx = Math.signum(deltaX);
		deltaX = Math.abs(deltaX);

		double deltaY = b.y() - a.y();
		double sy = Math.signum(deltaY);
		deltaY = Math.abs(deltaY);

		// We know that either deltaX or deltaY are not zero, since it they
		// would both be zero if and only if 'a' had been equal to 'b' (but we
		// excluded that with an if above).
		double dx, dy;
		if (deltaX >= deltaY) {
			dx = sx;
			dy = sy * (deltaY / deltaX);
		} else {
			dx = sx * (deltaX / deltaY);
			dy = sy;
		}

		double ix = a.x();
		double iy = a.y();
		QPointF ip;
		do {
			ix += dx;
			iy += dy;
			ip = new QPointF(Math.round(ix), Math.round(iy));
			points.add(ip);
		} while (!ip.equals(b));

		return points;
	}
}

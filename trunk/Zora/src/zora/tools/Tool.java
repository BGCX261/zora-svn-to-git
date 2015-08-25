/*
 * Tool.java
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

package zora.tools;

import zora.core.Canvas;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QPainter;

public abstract class Tool {

	private Canvas canvas;

	/*
	 * CONSTRUCTOR
	 */

	public Tool(Canvas canvas) {
		this.canvas = canvas;
	}

	/*
	 * PROPERTIES
	 */

	protected Canvas canvas() {
		return canvas;
	}

	protected QPainter image() {
		return canvas.image();
	}

	protected QGraphicsScene scene() {
		return canvas.scene();
	}

	protected QColor fgColor() {
		return canvas.fgColor();
	}

	protected QColor bgColor() {
		return canvas.bgColor();
	}

	/*
	 * PUBLIC METHODS
	 */

	public abstract void onPointerMove(QPointF ptrPos);

	public abstract void onPointerPress(QPointF ptrPos);

	public abstract void onPointerRelease(QPointF ptrPos);

	public abstract void dispose();

}

/*
 * Canvas.java
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

package zora.core;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsPixmapItem;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPixmap;

public final class Canvas {

	private QPainter image;
	private QGraphicsScene scene;

	private QPixmap pixmap;
	private QGraphicsPixmapItem pixmapItem;

	private QColor fgColor;
	private QColor bgColor;

	/*
	 * CONSTRUCTORS
	 */

	public Canvas() {
		this(Constants.DEFAULT_CANVAS_WIDTH, Constants.DEFAULT_CANVAS_HEIGHT);
	}

	public Canvas(int width, int height) {
		scene = new QGraphicsScene(0, 0, width, height);

		QPixmap alpha = new QPixmap(width, height);
		alpha.fill(QColor.red);
		QGraphicsPixmapItem alphaItem = scene.addPixmap(alpha);
		alphaItem.setZValue(-1);

		pixmap = new QPixmap(width, height);
		pixmap.fill(QColor.white);
		pixmapItem = scene.addPixmap(pixmap);
		pixmapItem.setZValue(0);

		image = new QPainter(pixmap);

		fgColor = QColor.red;
		bgColor = QColor.blue;
	}

	/*
	 * PROPERTIES
	 */

	public QPainter image() {
		return image;
	}

	public QGraphicsScene scene() {
		return scene;
	}

	public QColor fgColor() {
		return fgColor;
	}

	public void setFgColor(QColor fgColor) {
		this.fgColor = fgColor.clone();
	}

	public QColor bgColor() {
		return bgColor;
	}

	public void setBgColor(QColor bgColor) {
		this.bgColor = bgColor.clone();
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * 
	 */
	public void refresh() {
		// TODO Put refresh somewhere else!!!
		pixmapItem.setPixmap(pixmap);
	}

	/**
	 * 
	 * @param area
	 */
	public void refresh(QRectF area) {
		// TODO Put refresh somewhere else!!!
		pixmapItem.setPixmap(pixmap);
	}
}

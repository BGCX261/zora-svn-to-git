/*
 * ColorButton.java
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

package zora.gui.widgets;

import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QWidget;

public final class ColorButton extends QWidget {

	private final int colorButtonSize = 16;

	private final Signal1<QColor> clicked = new Signal1<QColor>();

	private QColor color;
	private QPainter painter;

	/* CONSTRUCTORS */

	public ColorButton(QColor color) {
		this.color = color;
		setFixedSize(colorButtonSize, colorButtonSize);
	}

	/* SIGNALS */

	public Signal1<QColor> clicked() {
		return clicked;
	}

	/* PUBLIC METHODS */

	@Override
	public String toString() {
		int r = color.red();
		int g = color.green();
		int b = color.blue();
		return String.format("ColorButton(%d, %d, %d)", r, g, b);
	}

	/* PROTECTED METHODS */

	protected void paintEvent(QPaintEvent ev) {
		painter = new QPainter(this);
		painter.drawLine(0, 0, 80, 80);
		painter.fillRect(rect(), color);
	}

	protected void mouseMoveEvent(QMouseEvent ev) {
		System.out.println(toString());
	}

	protected void mousePressEvent(QMouseEvent ev) {
		clicked.emit(color);
	}

	protected void mouseReleaseEvent(QMouseEvent ev) {
		System.out.println(toString());
	}

}

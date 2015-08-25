/*
 * CanvasView.java
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

import zora.core.Canvas;
import zora.tools.Tool;
import zora.tools.ToolType;
import zora.tools.brushes.PencilTool;
import zora.tools.shapes.LineTool;
import zora.tools.shapes.RectTool;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.opengl.QGLWidget;

public final class CanvasView extends QGraphicsView {

	/*
	 * FIELDS
	 */

	private Canvas canvas;

	private StatusBar statusBar;
	private Tool tool;
	private boolean pointerIsPressed = false;

	/*
	 * CONSTRUCTOR
	 */

	public CanvasView(StatusBar statusBar) {
		this.statusBar = statusBar;

		buildView();

		setTool(ToolType.LINE_TOOL);
	}

	/*
	 * PUBLIC METHODS
	 */

	public void setTool(ToolType toolType) {
		switch (toolType) {
		case LINE_TOOL:
			tool = new LineTool(canvas);
			break;
		case PENCIL_TOOL:
			tool = new PencilTool(canvas);
			break;
		case RECT_TOOL:
			tool = new RectTool(canvas);
			break;
		default:
			tool = new PencilTool(canvas);
			break;
		}
	}

	public void setFgColor(QColor fgColor) {
		canvas.setFgColor(fgColor);
	}

	public void setBgColor(QColor bgColor) {

	}

	/* PROTECTED METHODS */

	protected void mouseMoveEvent(QMouseEvent ev) {
		QPointF mappedPos = mapToScene(ev.pos());
		statusBar.showCoordinates(mappedPos);
		if (pointerIsPressed) {
			tool.onPointerMove(mappedPos);
		}
	}

	protected void mousePressEvent(QMouseEvent ev) {
		if (!pointerIsPressed) {
			pointerIsPressed = true;
			tool.onPointerPress(mapToScene(ev.pos()));

		}
	}

	protected void mouseReleaseEvent(QMouseEvent ev) {
		if (pointerIsPressed) {
			pointerIsPressed = false;
			tool.onPointerRelease(mapToScene(ev.pos()));
		}
	}

	/* PRIVATE METHODS */

	private void buildView() {
		// Enables mouse tracking to receive mouse move events even if no button
		// is pressed, so mouse coordinates can be printed in the statusbar.
		setMouseTracking(true);

		// ...
		setViewport(new QGLWidget());

		canvas = new Canvas();
		setScene(canvas.scene());
	}

}

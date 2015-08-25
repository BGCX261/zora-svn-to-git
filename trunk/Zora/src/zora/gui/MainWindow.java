/**
 * @file MainWindow.java
 * 
 * @author Alessio Parma <alessio.parma@gmail.com>
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

package zora.gui;

import zora.core.Constants;
import zora.gui.widgets.CanvasView;
import zora.gui.widgets.ColorChooser;
import zora.gui.widgets.StatusBar;
import zora.gui.widgets.ToolChooser;
import zora.tools.ToolType;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public final class MainWindow extends QMainWindow {

	private CanvasView canvasView;

	/* CONSTRUCTORS */

	public MainWindow() {
		StatusBar statusBar = new StatusBar();
		setStatusBar(statusBar);

		QWidget central = new QWidget();
		QVBoxLayout vbox = new QVBoxLayout(central);

		ColorChooser colorChooser = new ColorChooser();
		colorChooser.colorChoosen().connect(this, "onColorChoosen(QColor)");

		ToolChooser toolChooser = new ToolChooser();
		toolChooser.toolChoosen().connect(this, "onToolChoosen(ToolType)");

		canvasView = new CanvasView(statusBar);
		vbox.addWidget(canvasView);

		vbox.addWidget(toolChooser);
		vbox.addWidget(colorChooser);

		setCentralWidget(central);

		buildWindow();
	}

	/* SIGNAL HANDLERS */

	protected void onColorChoosen(QColor color) {
		canvasView.setFgColor(color);
	}

	protected void onToolChoosen(ToolType toolType) {
		canvasView.setTool(toolType);
	}

	protected void save() {
		// TODO
	}

	protected void quit() {
		close();
	}

	protected void setLineTool() {
		canvasView.setTool(ToolType.LINE_TOOL);
	}

	protected void setPencilTool() {
		canvasView.setTool(ToolType.PENCIL_TOOL);
	}

	protected void setRectTool() {
		canvasView.setTool(ToolType.RECT_TOOL);
	}

	protected void about() {
		QMessageBox.information(this, "Info", "It's your turn now :-)");
	}

	/* PRIVATE METHODS */

	private void buildWindow() {
		setWindowTitle(tr(Constants.PROGRAM_NAME));

		buildMenu();
	}

	private void buildMenu() {
		QAction action;

		// File
		QMenu file = menuBar().addMenu(tr("&File"));

		// File -> Save
		action = file.addAction(tr("&Save"));
		action.setShortcut(tr("Ctrl+S"));
		action.setStatusTip(tr("Save the image to file"));
		action.triggered.connect(this, "save()");

		// File -> Quit
		action = file.addAction(tr("&Quit"));
		action.setShortcut(tr("Ctrl+Q"));
		action.setStatusTip(tr("Quit the application"));
		action.triggered.connect(this, "quit()");

		// Edit
		QMenu edit = menuBar().addMenu(tr("&Edit"));

		// View
		QMenu view = menuBar().addMenu(tr("&View"));

		// Tools
		QMenu tools = menuBar().addMenu(tr("&Tools"));

		// Tools -> Pencil
		action = tools.addAction(tr("&Pencil"));
		action.triggered.connect(this, "setPencilTool()");

		// Tools -> Line
		action = tools.addAction(tr("&Line"));
		action.triggered.connect(this, "setLineTool()");

		// Tools -> Rectangle
		action = tools.addAction(tr("&Rectangle"));
		action.triggered.connect(this, "setRectTool()");

		// Image
		QMenu image = menuBar().addMenu(tr("&Image"));

		// Help
		QMenu help = menuBar().addMenu(tr("&Help"));

		// Help -> About...
		action = help.addAction(tr("&About..."));
		action.setStatusTip(tr("Quit the application"));
		action.triggered.connect(this, "about()");
	}

}

/**
 * @file ToolChooser.java
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

package zora.gui.widgets;

import zora.tools.ToolType;

import com.trolltech.qt.gui.QButtonGroup;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QWidget;

public final class ToolChooser extends QWidget {

	private Signal1<ToolType> toolChoosen = new Signal1<ToolType>();

	private QButtonGroup group = new QButtonGroup();

	/* CONSTRUCTORS */

	public ToolChooser() {
		group.buttonClicked.connect(this, "onToolButtonClicked()");

		QGridLayout grid = new QGridLayout(this);

		ToolButton tb;

		tb = new ToolButton(ToolType.PENCIL_TOOL);
		tb.setIcon(new QIcon("icons/actions/tool-pencil-22"));
		tb.setChecked(true);
		group.addButton(tb);
		grid.addWidget(tb, 0, 0);

		tb = new ToolButton(ToolType.LINE_TOOL);
		tb.setIcon(new QIcon("icons/actions/tool-line-22"));
		group.addButton(tb);
		grid.addWidget(tb, 0, 1);

	}

	/* SIGNALS */

	public Signal1<ToolType> toolChoosen() {
		return toolChoosen;
	}

	/* PUBLIC METHODS */

	@Override
	public String toString() {
		return "ToolChooser()";
	}

	/* SIGNAL HANDLERS */

	protected void onToolButtonClicked() {
		ToolButton tb = (ToolButton) group.checkedButton();
		toolChoosen.emit(tb.toolType());
	}
}

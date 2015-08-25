using System;
using Qyoto;

namespace Canvas
{
	public class FigureEditor : QGraphicsView
	{
		public FigureEditor (QGraphicsScene c, QWidget parent, string name, WindowFlags f)
			: base(c, parent)
		{
			SetObjectName (name);
			SetWindowFlags (f);
			SetRenderHint (RenderHint.Antialiasing | RenderHint.SmoothPixmapTransform);
		}
		
		public void Clear()
		{
			Scene().Clear();
		}
	}
}
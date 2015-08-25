using System;
using Qyoto;

namespace Canvas
{
	public class EdgeItem : QGraphicsLineItem
	{
		private static int count = 0;

		public EdgeItem (NodeItem source, NodeItem target)
		{
			++count;
			SetPen( *tp ); // !!!
    		source.AddOutEdge(this);
    		target.AddInEdge(this);
    		SetLine(new QLineF(source.x(), source.y(), target.x(), target.y()));
    		SetZValue(127);
    		SetBoundingRegionGranularity(0.05);
		}

		public static int Count {
			get { return count; }
		}

		public void SetFromPoint (int x, int y)
		{
			SetLine(new QLineF(x, y, line().p2().x(), line().p2().y()));
		}

		public void SetToPoint (int x, int y)
		{
			SetLine(new QLineF(line().p1().x(), line().p1().y(), x, y));
		}
	}
}
using System.Collections.Generic;
using Qyoto;

namespace Canvas
{
	public class NodeItem : QGraphicsEllipseItem
	{
		private List<EdgeItem> inList = new List<EdgeItem> ();
		private List<EdgeItem> outList = new List<EdgeItem> ();

		public NodeItem () : base(new QRectF(-3, -3, 6, 6))
		{
			setPen( *tp );
		    setBrush( *tb );
		    setZValue( 128 );
		    setFlag(ItemIsMovable);
		}

		public void AddInEdge (EdgeItem edge)
		{
			inList.append (edge);
		}

		public void AddOutEdge (EdgeItem edge)
		{
			outList.append (edge);
		}

		protected QVariant ItemChange (GraphicsItemChange change, QVariant val)
		{
			if (change == GraphicsItemChange.ItemPositionHasChanged) {
				foreach (EdgeItem ein in inList) {
					ein.SetToPoint(X(), Y());
				}
				foreach (EdgeItem eout in outList) {
					eout.SetFromPoint(X(), Y());
				}
			}
			
			return base.ItemChange(change, val);
		}
	}
}
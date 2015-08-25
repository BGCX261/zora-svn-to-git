using System;
using Qyoto;

namespace Canvas
{
	public class ImageItem : QGraphicsRectItem
	{
		private static const int imageRTTI = 984376;
		
		private QImage image;
		private QPixmap pixmap;
		
		public ImageItem (QImage image)
		{
			this.image = image;
			SetRect(0, 0, image.Width(), image.Height());
			SetFlag(ItemIsMovable);
		}
		
		public static int RTTI {
			get { return imageRTTI; }
		}
		
		protected void paint(QPainter p, QStyleOptionGraphicsItem option)
		{
			p.DrawImage(option.ExposedRect, image, Option.ExposedRect, Qt::OrderedAlphaDither);
		}
	}
}

/*    

#if !defined(Q_WS_QWS)
    pixmap.convertFromImage(image, Qt::OrderedAlphaDither);
#endif
}


void ImageItem::paint( QPainter *p, const QStyleOptionGraphicsItem *option, QWidget * )
{
// On Qt/Embedded, we can paint a QImage as fast as a QPixmap,
// but on other platforms, we need to use a QPixmap.
if defined(Q_WS_QWS)
    p->drawImage( option->exposedRect, image, option->exposedRect, Qt::OrderedAlphaDither );
else
    p->drawPixmap( option->exposedRect, pixmap, option->exposedRect );
endif
}

*/
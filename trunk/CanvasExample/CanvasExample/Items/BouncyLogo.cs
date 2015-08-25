using System;
using Qyoto;

namespace Canvas
{
	public class BouncyLogo : QGraphicsPixmapItem
	{
		private double xvel;
		private double yvel;
		
		public BouncyLogo ()
		{
			xvel = yvel = 0;
		}
		
	    public void advance(int);
	    public int type() const;
	
	    public QPainterPath shape() const;
	
	    public void initPos();
		
		private initSpeed();
	}
}





BouncyLogo::BouncyLogo() :
{
    setPixmap(QPixmap(":/trolltech/examples/graphicsview/portedcanvas/qt-trans.xpm"));
}

const int logo_rtti = 1234;

int BouncyLogo::type() const
{
    return logo_rtti;
}

QPainterPath BouncyLogo::shape() const
{
    QPainterPath path;
    path.addRect(boundingRect());
    return path;
}

void BouncyLogo::initPos()
{
    initSpeed();
    int trial=1000;
    do {
        setPos(qrand()%int(scene()->width()),qrand()%int(scene()->height()));
        advance(0);
    } while (trial-- && xvel==0.0 && yvel==0.0);
}

void BouncyLogo::initSpeed()
{
    const double speed = 4.0;
    double d = (double)(qrand()%1024) / 1024.0;
    xvel = d*speed*2-speed;
    yvel = (1-d)*speed*2-speed;
}

void BouncyLogo::advance(int stage)
{
    switch ( stage ) {
      case 0: {
        double vx = xvel;
        double vy = yvel;

        if ( vx == 0.0 && vy == 0.0 ) {
            // stopped last turn
            initSpeed();
            vx = xvel;
            vy = yvel;
        }

        double nx = x() + vx;
        double ny = y() + vy;

        if ( nx < 0 || nx >= scene()->width() )
            vx = -vx;
        if ( ny < 0 || ny >= scene()->height() )
            vy = -vy;

        for (int bounce=0; bounce<4; bounce++) {
            QList<QGraphicsItem *> l=scene()->collidingItems(this);
            for (QList<QGraphicsItem *>::Iterator it=l.begin(); it!=l.end(); ++it) {
                QGraphicsItem *hit = *it;
                QPainterPath advancedShape = QMatrix().translate(xvel, yvel).map(shape());
                if ( hit->type()==logo_rtti && hit->collidesWithPath(mapToItem(hit, advancedShape)) ) {
                    switch ( bounce ) {
                      case 0:
                        vx = -vx;
                        break;
                      case 1:
                        vy = -vy;
                        vx = -vx;
                        break;
                      case 2:
                        vx = -vx;
                        break;
                      case 3:
                        // Stop for this turn
                        vx = 0;
                        vy = 0;
                        break;
                    }
                    xvel = vx;
                    yvel = vy;
                    break;
                }
            }
        }

        if ( x()+vx < 0 || x()+vx >= scene()->width() )
            vx = 0;
        if ( y()+vy < 0 || y()+vy >= scene()->height() )
            vy = 0;

        xvel = vx;
        yvel = vy;
      } break;
      case 1:
        moveBy(xvel, yvel);
        break;
    }
}
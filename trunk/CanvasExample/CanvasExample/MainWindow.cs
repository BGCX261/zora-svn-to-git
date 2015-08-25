using System;
using Qyoto;

namespace Canvas
{
	class MainWindow : QMainWindow
	{
		private FigureEditor editor;
		
		public MainWindow ()
		{
			editor = new FigureEditor (canvas, this);
			
			InitMenuBar();
		
		    StatusBar();
		
		    SetCentralWidget(editor);
		
		    printer = 0;
		
		    Init();
		}
		
		public static void Main (string[] args)
		{
			Console.WriteLine ("Hello World!");
		}
		
		[Q_SLOT]
		private void Init()
		{
		    Clear();
		
		    static int r=24;
		    qsrand(++r);
		
		    mainCount++;
		    butterflyimg = 0;
		    logoimg = 0;
		
		    int i;
		    for ( i=0; i < int(canvas.width()) / 56; i++) {
		        addButterfly();
		    }
		    for ( i=0; i < int(canvas.width()) / 85; i++) {
		        addHexagon();
		    }
		    for ( i=0; i < int(canvas.width()) / 128; i++) {
		        addLogo();
		    }
		}
		
		[Q_SLOT]
		private void Clear()
		{
		    editor.Clear();
		}
		
		private void InitMenuBar ()
		{
			var menu = MenuBar ();
			QAction action;
		
		    var file = menu.AddMenu ("&File");
			
			action = file.AddAction ("&Fill canvas");
			action.SetShortcuts (new QKeySequence ("Ctrl+F"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("Init()"));
			
			action = file.AddAction ("&Erase canvas");
			action.SetShortcuts (new QKeySequence ("Ctrl+E"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("Clear()"));
			
			action = file.AddAction ("&New view");
			action.SetShortcuts (new QKeySequence ("Ctrl+N"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("NewView()"));
			
			file.AddSeparator ();
			
			action = file.AddAction ("&Print...");
			action.SetShortcuts (new QKeySequence ("Ctrl+P"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("Clear()"));
			
			file.AddSeparator ();
			
			action = file.AddAction ("E&xit");
			action.SetShortcuts (new QKeySequence ("Ctrl+E"));
			Connect (action, SIGNAL ("triggered()"), qApp, SLOT ("quit()"));
			
			var edit = menu.AddMenu ("&Edit");
			
			action = edit.AddAction ("Add &Circle");
			action.SetShortcuts (new QKeySequence ("Alt+C"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddCircle()"));
		
			action = edit.AddAction ("Add &Hexagon");
			action.SetShortcuts (new QKeySequence ("Alt+H"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddHexagon()"));
			
			action = edit.AddAction ("Add &Polygon");
			action.SetShortcuts (new QKeySequence ("Alt+P"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddPolygon()"));
			
			action = edit.AddAction ("Add Spl&ine");
			action.SetShortcuts (new QKeySequence ("Alt+I"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddSpline()"));
			
			action = edit.AddAction ("Add &Text");
			action.SetShortcuts (new QKeySequence ("Alt+T"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddText()"));
			
			action = edit.AddAction ("Add &Line");
			action.SetShortcuts (new QKeySequence ("Alt+L"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddLine()"));
			
			action = edit.AddAction ("Add &Rectangle");
			action.SetShortcuts (new QKeySequence ("Alt+R"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddRectangle()"));
			
			action = edit.AddAction ("Add &Sprite");
			action.SetShortcuts (new QKeySequence ("Alt+S"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddSprite()"));
			
			action = edit.AddAction ("Create &Mesh");
			action.SetShortcuts (new QKeySequence ("Alt+M"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddMesh()"));
			
			action = edit.AddAction ("Add &Alpha-blended image");
			action.SetShortcuts (new QKeySequence ("Alt+A"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("AddButterfly()"));
			
			var view = menu.AddMenu ("&View");
			
			action = view.AddAction ("&Enlarge");
			action.SetShortcuts (new QKeySequence ("Shift+Ctrl++"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("Enlarge()"));
			
			action = view.AddAction ("Shr&ink");
			action.SetShortcuts (new QKeySequence ("Shift+Ctrl+-"));
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("Shrink()"));
			
		    view.AddSeparator();
			
			action = view.AddAction ("&Rotate clockwise");
			action.SetShortcuts (new QKeySequence ("Ctrl+-")); // Page down
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("RotateClockwise()"));
			
			action = view.AddAction ("Rotate &counterclockwise");
			action.SetShortcuts (new QKeySequence ("Ctrl+-")); // Page up
			Connect (action, SIGNAL ("triggered()"), this, SLOT ("RotateCounterClockwise()"));
			
		    view->insertItem("&Zoom in", this, SLOT(zoomIn()), Qt::CTRL+Qt::Key_Plus);
		    view->insertItem("Zoom &out", this, SLOT(zoomOut()), Qt::CTRL+Qt::Key_Minus);
		    view->insertItem("Translate left", this, SLOT(moveL()), Qt::CTRL+Qt::Key_Left);
		    view->insertItem("Translate right", this, SLOT(moveR()), Qt::CTRL+Qt::Key_Right);
		    view->insertItem("Translate up", this, SLOT(moveU()), Qt::CTRL+Qt::Key_Up);
		    view->insertItem("Translate down", this, SLOT(moveD()), Qt::CTRL+Qt::Key_Down);
		    view->insertItem("&Mirror", this, SLOT(mirror()), Qt::CTRL+Qt::Key_Home);
		
		    menu.AddSeparator();
			
			var help = menu.AddMenu ("&Help");
			
		    help->insertItem("&About", this, SLOT(help()), Qt::Key_F1);
		    help->setItemChecked(dbf_id, TRUE);
		}
	}
}

static uint mainCount = 0;
static QImage *butterflyimg;
static QImage *logoimg;

Main::Main(QGraphicsScene& c, QWidget* parent, const char* name, Qt::WindowFlags f) :
    Q3MainWindow(parent,name,f),
    canvas(c)
{
    
}

Main::~Main()
{
    delete printer;
    if ( !--mainCount ) {
        delete[] butterflyimg;
        butterflyimg = 0;
        delete[] logoimg;
        logoimg = 0;
    }
}

void Main::newView()
{
    // Open a new view... have it delete when closed.
    Main *m = new Main(canvas, 0, 0, Qt::WDestructiveClose);
    m->show();
}



void Main::help()
{
    static QMessageBox* about = new QMessageBox( "Qt Canvas Example",
            "<h3>The QCanvas classes example</h3>"
            "<ul>"
                "<li> Press ALT-S for some sprites."
                "<li> Press ALT-C for some circles."
                "<li> Press ALT-L for some lines."
                "<li> Drag the objects around."
                "<li> Read the code!"
            "</ul>", QMessageBox::Information, 1, 0, 0, this, 0, FALSE );
    about->setButtonText( 1, "Dismiss" );
    about->show();
}

void Main::aboutQt()
{
    QMessageBox::aboutQt( this, "Qt Canvas Example" );
}

void Main::enlarge()
{
    canvas.setSceneRect(0, 0, canvas.width()*4/3, canvas.height()*4/3);
}

void Main::shrink()
{
    canvas.setSceneRect(0, 0, qMax(canvas.width()*3/4, qreal(1.0)), qMax(canvas.height()*3/4, qreal(1.0)));
}

void Main::rotateClockwise()
{
    editor->rotate( 22.5 );
}

void Main::rotateCounterClockwise()
{
    editor->rotate( -22.5 );
}

void Main::zoomIn()
{
    editor->scale( 2.0, 2.0 );
}

void Main::zoomOut()
{
    editor->scale( 0.5, 0.5 );
}

void Main::mirror()
{
    editor->scale( -1, 1 );
}

void Main::moveL()
{
    editor->translate( -16, 0 );
}

void Main::moveR()
{
    editor->translate( +16, 0 );
}

void Main::moveU()
{
    editor->translate( 0, -16 );
}

void Main::moveD()
{
    editor->translate( 0, +16 );
}

void Main::print()
{
    if ( !printer ) printer = new QPrinter;
    if ( printer->setup(this) ) {
        QPainter pp(printer);
        canvas.render(&pp);
    }
}


void Main::addSprite()
{
    BouncyLogo* i = new BouncyLogo;
    canvas.addItem(i);
    i->initPos();
    i->setZValue(qrand()%256);
}

QString butterfly_fn;
QString logo_fn;


void Main::addButterfly()
{
    if ( butterfly_fn.isEmpty() )
        return;
    if ( !butterflyimg ) {
        butterflyimg = new QImage[4];
        butterflyimg[0].load( butterfly_fn );
        butterflyimg[1] = butterflyimg[0].smoothScale( int(butterflyimg[0].width()*0.75),
                int(butterflyimg[0].height()*0.75) );
        butterflyimg[2] = butterflyimg[0].smoothScale( int(butterflyimg[0].width()*0.5),
                int(butterflyimg[0].height()*0.5) );
        butterflyimg[3] = butterflyimg[0].smoothScale( int(butterflyimg[0].width()*0.25),
                int(butterflyimg[0].height()*0.25) );
    }
    QAbstractGraphicsShapeItem* i = new ImageItem(butterflyimg[qrand()%4]);
    canvas.addItem(i);
    i->setPos(qrand()%int(canvas.width()-butterflyimg->width()),
            qrand()%int(canvas.height()-butterflyimg->height()));
    i->setZValue(qrand()%256+250);
}

void Main::addLogo()
{
    if ( logo_fn.isEmpty() )
        return;
    if ( !logoimg ) {
        logoimg = new QImage[4];
        logoimg[0].load( logo_fn );
        logoimg[1] = logoimg[0].smoothScale( int(logoimg[0].width()*0.75),
                int(logoimg[0].height()*0.75) );
        logoimg[2] = logoimg[0].smoothScale( int(logoimg[0].width()*0.5),
                int(logoimg[0].height()*0.5) );
        logoimg[3] = logoimg[0].smoothScale( int(logoimg[0].width()*0.25),
                int(logoimg[0].height()*0.25) );
    }
    QAbstractGraphicsShapeItem* i = new ImageItem(logoimg[qrand()%4]);
    canvas.addItem(i);
    i->setPos(qrand()%int(canvas.width()-logoimg->width()),
            qrand()%int(canvas.height()-logoimg->width()));
    i->setZValue(qrand()%256+256);
}



void Main::addCircle()
{
    QAbstractGraphicsShapeItem* i = canvas.addEllipse(QRectF(0,0,50,50));
    i->setFlag(QGraphicsItem::ItemIsMovable);
    i->setPen(Qt::NoPen);
    i->setBrush( QColor(qrand()%32*8,qrand()%32*8,qrand()%32*8) );
    i->setPos(qrand()%int(canvas.width()),qrand()%int(canvas.height()));
    i->setZValue(qrand()%256);
}

void Main::addHexagon()
{
    const int size = int(canvas.width() / 25);
    Q3PointArray pa(6);
    pa[0] = QPoint(2*size,0);
    pa[1] = QPoint(size,-size*173/100);
    pa[2] = QPoint(-size,-size*173/100);
    pa[3] = QPoint(-2*size,0);
    pa[4] = QPoint(-size,size*173/100);
    pa[5] = QPoint(size,size*173/100);
    QGraphicsPolygonItem* i = canvas.addPolygon(pa);
    i->setFlag(QGraphicsItem::ItemIsMovable);
    i->setPen(Qt::NoPen);
    i->setBrush( QColor(qrand()%32*8,qrand()%32*8,qrand()%32*8) );
    i->setPos(qrand()%int(canvas.width()),qrand()%int(canvas.height()));
    i->setZValue(qrand()%256);
}

void Main::addPolygon()
{
    const int size = int(canvas.width()/2);
    Q3PointArray pa(6);
    pa[0] = QPoint(0,0);
    pa[1] = QPoint(size,size/5);
    pa[2] = QPoint(size*4/5,size);
    pa[3] = QPoint(size/6,size*5/4);
    pa[4] = QPoint(size*3/4,size*3/4);
    pa[5] = QPoint(size*3/4,size/4);
    QGraphicsPolygonItem* i = canvas.addPolygon(pa);
    i->setFlag(QGraphicsItem::ItemIsMovable);
    i->setPen(Qt::NoPen);
    i->setBrush( QColor(qrand()%32*8,qrand()%32*8,qrand()%32*8) );
    i->setPos(qrand()%int(canvas.width()),qrand()%int(canvas.height()));
    i->setZValue(qrand()%256);
}

void Main::addSpline()
{
    const int size = int(canvas.width()/6);

    Q3PointArray pa(12);
    pa[0] = QPoint(0,0);
    pa[1] = QPoint(size/2,0);
    pa[2] = QPoint(size,size/2);
    pa[3] = QPoint(size,size);
    pa[4] = QPoint(size,size*3/2);
    pa[5] = QPoint(size/2,size*2);
    pa[6] = QPoint(0,size*2);
    pa[7] = QPoint(-size/2,size*2);
    pa[8] = QPoint(size/4,size*3/2);
    pa[9] = QPoint(0,size);
    pa[10]= QPoint(-size/4,size/2);
    pa[11]= QPoint(-size/2,0);

    QPainterPath path;
    path.moveTo(pa[0]);
    for (int i = 1; i < pa.size(); i += 3)
        path.cubicTo(pa[i], pa[(i + 1) % pa.size()], pa[(i + 2) % pa.size()]);

    QGraphicsPathItem* item = canvas.addPath(path);
    item->setFlag(QGraphicsItem::ItemIsMovable);
    item->setPen(Qt::NoPen);
    item->setBrush( QColor(qrand()%32*8,qrand()%32*8,qrand()%32*8) );
    item->setPos(qrand()%int(canvas.width()),qrand()%int(canvas.height()));
    item->setZValue(qrand()%256);
}

void Main::addText()
{
    QGraphicsTextItem* i = canvas.addText("QCanvasText");
    i->setFlag(QGraphicsItem::ItemIsMovable);
    i->setPos(qrand()%int(canvas.width()),qrand()%int(canvas.height()));
    i->setZValue(qrand()%256);
}

void Main::addLine()
{
    QGraphicsLineItem* i = canvas.addLine(QLineF( qrand()%int(canvas.width()), qrand()%int(canvas.height()),
                                                  qrand()%int(canvas.width()), qrand()%int(canvas.height()) ));
    i->setFlag(QGraphicsItem::ItemIsMovable);
    i->setPen( QPen(QColor(qrand()%32*8,qrand()%32*8,qrand()%32*8), 6) );
    i->setZValue(qrand()%256);
}

void Main::addMesh()
{
    int x0 = 0;
    int y0 = 0;

    if ( !tb ) tb = new QBrush( Qt::red );
    if ( !tp ) tp = new QPen( Qt::black );

    int nodecount = 0;

    int w = int(canvas.width());
    int h = int(canvas.height());

    const int dist = 30;
    int rows = h / dist;
    int cols = w / dist;

#ifndef QT_NO_PROGRESSDIALOG
    Q3ProgressDialog progress( "Creating mesh...", "Abort", rows,
                              this, "progress", TRUE );
#endif

    canvas.update();

    Q3MemArray<NodeItem*> lastRow(cols);
    for ( int j = 0; j < rows; j++ ) {
        int n = j%2 ? cols-1 : cols;
        NodeItem *prev = 0;
        for ( int i = 0; i < n; i++ ) {
            NodeItem *el = new NodeItem;
            canvas.addItem(el);
            nodecount++;
            int r = qrand();
            int xrand = r %20;
            int yrand = (r/20) %20;
            el->setPos( xrand + x0 + i*dist + (j%2 ? dist/2 : 0 ),
                        yrand + y0 + j*dist );

            if ( j > 0 ) {
                if ( i < cols-1 )
                    canvas.addItem(new EdgeItem( lastRow[i], el));
                if ( j%2 )
                    canvas.addItem(new EdgeItem( lastRow[i+1], el));
                else if ( i > 0 )
                    canvas.addItem(new EdgeItem( lastRow[i-1], el));
            }
            if ( prev ) {
                canvas.addItem(new EdgeItem( prev, el));
            }
            if ( i > 0 ) lastRow[i-1] = prev;
            prev = el;
        }
        lastRow[n-1]=prev;
#ifndef QT_NO_PROGRESSDIALOG
        progress.setProgress( j );
        if ( progress.wasCancelled() )
            break;
#endif
    }
#ifndef QT_NO_PROGRESSDIALOG
    progress.setProgress( rows );
#endif
    // qDebug( "%d nodes, %d edges", nodecount, EdgeItem::count() );
}

void Main::addRectangle()
{
    QAbstractGraphicsShapeItem *i = canvas.addRect( QRectF(0,
                                                          0,
                                                          canvas.width()/5,
                                                          canvas.width()/5) );
    i->setFlag(QGraphicsItem::ItemIsMovable);
    int z = qrand()%256;
    i->setBrush( QColor(z,z,z) );
    i->setPen( QPen(QColor(qrand()%32*8,qrand()%32*8,qrand()%32*8), 6) );
    i->setZValue(z);
}
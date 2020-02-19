#include <QApplication>
#include <QFrame>
#include <QLayout>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include <QVBoxLayout>
#include <QDial>
#include <QLCDNumber>

int main( int argc, char ** argv)
{
  // Creem tots els components
  QApplication app(argc, argv);
  QFrame *w=new QFrame();
  QVBoxLayout *mainLayout=new QVBoxLayout(w);
  QHBoxLayout *qButtonLayout = new QHBoxLayout();
  QPushButton *quitButton = new QPushButton("&Quit", w);

  QLCDNumber *lcdHours = new QLCDNumber();
  QLCDNumber *lcdMins = new QLCDNumber();
  QLabel *labelHours = new QLabel("Hours", w);
  QLabel *labelMins = new QLabel("Minutes", w);
  QDial *dialHours = new QDial();
  QDial *dialMins = new QDial();
  dialHours->setMaximum(23);
  dialMins->setMaximum(59);


  QHBoxLayout *labelsLayout  = new QHBoxLayout();
  labelsLayout->addWidget(labelHours);
  labelsLayout->addWidget(labelMins);
  mainLayout->addLayout(labelsLayout);

  QHBoxLayout *lcdLayout  = new QHBoxLayout();
  lcdLayout->addWidget(lcdHours);
  lcdLayout->addWidget(lcdMins);
  mainLayout->addLayout(lcdLayout);

  QHBoxLayout *dialsLayout  = new QHBoxLayout();
  dialsLayout->addWidget(dialHours);
  dialsLayout->addWidget(dialMins);
  mainLayout->addLayout(dialsLayout);


  // Insertem l'espai i el buttó de quit al layout horizontal del botó
  qButtonLayout->addItem(new QSpacerItem(30,10,QSizePolicy::Expanding,QSizePolicy::Minimum));
  qButtonLayout->addWidget(quitButton);
  
  // Afegim tots els elements dins del layout principal
  mainLayout->addItem(new QSpacerItem(20,40,QSizePolicy::Minimum,QSizePolicy::Expanding));
  mainLayout->addLayout(qButtonLayout);

  app.connect(quitButton, SIGNAL(clicked()), w, SLOT(close()));

  app.connect(dialHours, SIGNAL(valueChanged(int)), lcdHours, SLOT(display(int)));
  app.connect(dialMins, SIGNAL(valueChanged(int)), lcdMins, SLOT(display(int)));

  w->show();
  return app.exec();
}

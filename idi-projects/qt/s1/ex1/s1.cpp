#include <QPushButton>
#include <QApplication>
#include <QFrame>
#include <QLayout>
#include <QLineEdit>

int main(int argc, char **argv ) {
  QApplication a(argc, argv);
  QString fontFamily = "Arial";
  a.setFont(fontFamily);
  // Crea un frame
  QFrame F(0, NULL);
  // Crea un contenidor horitzontal
  QHBoxLayout* cH = new QHBoxLayout(&F);
  //1.1.4.2 QVBoxLayout* cH = new QVBoxLayout(&F);

  // Afegeix una caixa de text
  QLineEdit* le = new QLineEdit(&F);
  cH->addWidget(le);

  // Afegeix un espai (horitzontal, vertical)
  QSpacerItem *sp = new QSpacerItem(100,20);
  //1.1.4.1  QSpacerItem *sp = new QSpacerItem(200,20);
  cH->addItem(sp);

  QVBoxLayout* cV = new QVBoxLayout(&F); //1.1.4.3

  // Afegeix un boto
  QPushButton* ok = new QPushButton("D'acord", &F);
  cV->addWidget(ok);
  // Afegeix un altre boto
  QPushButton* surt = new QPushButton("Surt", &F);
  cV->addWidget(surt);                  //1.1.4.3
  cH->addLayout(cV);                    //1.1.4.3
  F.show();
  return a.exec();
}

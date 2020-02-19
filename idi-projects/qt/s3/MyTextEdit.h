// Afegir aquest include a la llista de includes
#include <QtGui/QWidget>
#include <QtGui/QTextEdit>
#include <QtDesigner/QDesignerExportWidget>

class MyTextEdit : public QTextEdit
{
  Q_OBJECT
  public:
    // Constructor
    MyTextEdit (QWidget * parent=0);
  public slots:
    // Afegir tots els slots del meuwidget
    void change2Red();
    void change2Blue();
  signals:
    // Afegir tots els signals
};

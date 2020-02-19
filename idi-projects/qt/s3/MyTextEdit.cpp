#include "MyTextEdit.h"

MyTextEdit::MyTextEdit(QWidget* parent): QTextEdit(parent) {
  //ui.setupUi(this);
}


void MyTextEdit::change2Red() {
  this->setStyleSheet(
                "MyTextEdit#textBox {"
                "background-color: red;"
                "}"
                );
}

void MyTextEdit::change2Blue() {
  this->setStyleSheet(
                "MyTextEdit#textBox {"
                "background-color: blue;"
                "}"
                );
  
}

#include <QApplication>
#include "ColorWidget.h"

int main(int argc, char **argv) {
	QApplication app(argc, argv);
    ColorWidget colorWidget;

    //app.connect(dialMins, SIGNAL(valueChanged(int)), lcdMins, SLOT(display(int)));

    colorWidget.show();
	return app.exec();
}

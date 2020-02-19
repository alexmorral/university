TEMPLATE    = app
QT         += opengl

#LIBS += -lGLEW
LIBS += /usr/lib/libGLEW.a -framework GLUT -framework OpenGL
INCLUDEPATH +=  /usr/include#/glm
INCLUDEPATH += ./Model

HEADERS += MyGLWidget.h

SOURCES += main.cpp \
        MyGLWidget.cpp ./Model/model.cpp

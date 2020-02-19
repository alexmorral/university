TEMPLATE    = app
QT         += opengl

LIBS += /usr/lib/libGLEW.a -framework GLUT -framework OpenGL
INCLUDEPATH +=  /usr/include

HEADERS += MyGLWidget.h

SOURCES += main.cpp \
        MyGLWidget.cpp
RESOURCES += shaders.qrc

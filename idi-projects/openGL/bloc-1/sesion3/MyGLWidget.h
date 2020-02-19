#include <QGLWidget>
#include <QGLShader>
#include <QGLShaderProgram>
#include <QKeyEvent>
#include "glm/glm.hpp"
#include <glm/gtc/matrix_transform.hpp>
#include <math.h>

#define GLM_FORCE_RADIANS
class MyGLWidget : public QGLWidget 
{
  Q_OBJECT

  public:
    MyGLWidget (QGLFormat &f, QWidget *parent=0);
  
  protected:
    // initializeGL() - Aqui incluim les inicialitzacions del contexte grafic.
    virtual void initializeGL ();

    // paintGL - Mètode cridat cada cop que cal refrescar la finestra.
    // Tot el que es dibuixa es dibuixa aqui.
    virtual void paintGL ();
 
    // resizeGL() - Es cridat quan canvi la mida del widget
    virtual void resizeGL (int width, int height);  

  private:
    void createBuffers ();
    
    void initShaders();
    
    void keyPressEvent(QKeyEvent *e);
    
    void modelTransform(char dir);
    
    GLuint VAO, VBO, VBO2;
    GLint varLoc, transLoc; 
    float scl;
    QGLShader *fs, *vs;
    QGLShaderProgram *program;
    glm::vec3 position, rotation;
    float angle = 0.0;
    
};

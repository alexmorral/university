#include <GL/glew.h>
#include "MyGLWidget.h"

#include <iostream>

MyGLWidget::MyGLWidget (QGLFormat &f, QWidget* parent) : QGLWidget(f, parent)
{
  setFocusPolicy(Qt::ClickFocus);  // per rebre events de teclat
}

void MyGLWidget::initializeGL ()
{
  // glew és necessari per cridar funcions de les darreres versions d'OpenGL
  glewExperimental = GL_TRUE;
  glewInit(); 
  glGetError();  // Reinicia la variable d'error d'OpenGL

  glClearColor (0.5, 0.7, 1.0, 1.0); // defineix color de fons (d'esborrat)
  initShaders();
  createBuffers();
}

void MyGLWidget::paintGL ()
{
  glClear (GL_COLOR_BUFFER_BIT);  // Esborrem el frame-buffer

  // Activem l'Array a pintar 
  glBindVertexArray(VAO);
 
  // Pintem l'escena
  glDrawArrays(GL_TRIANGLES, 0, 3);
  
  // Desactivem el VAO
  glBindVertexArray(0);
}

void MyGLWidget::resizeGL (int w, int h)
{
  glViewport (0, 0, w, h);
}

void MyGLWidget::createBuffers ()
{
  glm::vec3 Vertices[3];  // Tres vèrtexs amb X, Y i Z
  Vertices[0] = glm::vec3(-0.8, -0.8, 0.0);
  Vertices[1] = glm::vec3(0.8, -0.8, 0.0);
  Vertices[2] = glm::vec3(0.0, 0.8, 0.0);
  
  glm::vec3 Colors[3];
  Colors[0] = glm::vec3(1.0, 0.0, 0.0);
  Colors[1] = glm::vec3(0.0, 1.0, 0.0);
  Colors[2] = glm::vec3(0.0, 0.0, 1.0);
  
  // Creació del Vertex Array Object (VAO) que usarem per pintar
  glGenVertexArrays(1, &VAO);
  glBindVertexArray(VAO);

  // Creació del buffer amb les dades dels vèrtexs
  glGenBuffers(1, &VBO);
  glBindBuffer(GL_ARRAY_BUFFER, VBO);
  glBufferData(GL_ARRAY_BUFFER, sizeof(Vertices), Vertices, GL_STATIC_DRAW);
  // Activem l'atribut que farem servir per vèrtex (només el 0 en aquest cas)	
  GLint loc = glGetAttribLocation(program->programId(), "vertex");
  glVertexAttribPointer(loc, 3, GL_FLOAT, GL_FALSE, 0, 0);
  glEnableVertexAttribArray(loc);
  
  
  /**COLOR**/
  // Creació del buffer amb les dades dels vèrtexs
  glGenBuffers(1, &VBO2);
  glBindBuffer(GL_ARRAY_BUFFER, VBO2);
  glBufferData(GL_ARRAY_BUFFER, sizeof(Colors), Colors, GL_STATIC_DRAW);
  // Activem l'atribut que farem servir per vèrtex (només el 0 en aquest cas)	
  loc = glGetAttribLocation(program->programId(), "color");
  glVertexAttribPointer(loc, 3, GL_FLOAT, GL_FALSE, 0, 0);
  glEnableVertexAttribArray(loc);
  
  // Desactivem el VAO
  glBindVertexArray(0);
}

void MyGLWidget::initShaders() {
    fs = new QGLShader(QGLShader::Fragment, this);
    vs = new QGLShader(QGLShader::Vertex, this);
    
    fs->compileSourceFile(":/shaders/fragshad.frag");
    vs->compileSourceFile(":/shaders/vertshad.vert");
    
    program = new QGLShaderProgram(this);
    program->addShader(fs);
    program->addShader(vs);
    program->link();
    
    program->bind();
    
    
}


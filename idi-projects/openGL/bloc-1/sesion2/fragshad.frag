#version 330 core

in vec3 fcolor;
out vec4 FragColor;

void main() {
<<<<<<< HEAD
  FragColor = vec4(1.);
  if((int(gl_FragCoord.y)%10 > 0) && (int(gl_FragCoord.y)%10 <= 5)) {
    if(gl_FragCoord.x < 400. && gl_FragCoord.y > 400) //sup-izq rojo
	FragColor = vec4(1.,0.,0.,1.);
    if(gl_FragCoord.x >= 400. && gl_FragCoord.y > 400) //sup-der azul
	FragColor = vec4(0.,0.,1.,1.);
    if(gl_FragCoord.x < 400. && gl_FragCoord.y <= 400) //inf-izq amarillo
	FragColor = vec4(1.,1.,0.,1.);
    if(gl_FragCoord.x >= 400. && gl_FragCoord.y <= 400) //inf-der verde
	FragColor = vec4(0.,1.,0.,1.);
  } else {
    discard;
  }
}
=======
  FragColor = vec4(fcolor, 1.);
  
}
>>>>>>> origin

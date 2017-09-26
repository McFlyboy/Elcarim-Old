#version 400 core

in vec2 vertex;
in vec2 textureCoord;

out vec2 pass_textureCoord;

uniform float aspectRatio;
uniform vec2 position;
uniform vec2 scale;

void main(void){
	vec2 worldPosition = vertex * scale + position;
	gl_Position = vec4(worldPosition.x * aspectRatio, worldPosition.y, 0.0, 1.0);
	pass_textureCoord = textureCoord;
}
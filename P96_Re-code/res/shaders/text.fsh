#version 400 core

in vec2 pass_textureCoord;

out vec4 out_Color;

uniform sampler2D fontTexture;
uniform vec3 color;

void main(void){
	out_Color = vec4(color, texture(fontTexture, pass_textureCoord).a);
}
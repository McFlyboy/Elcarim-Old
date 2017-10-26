#version 400 core

in vec2 passTextureCoord;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform int colorActive;
uniform float red;
uniform float green;
uniform float blue;

void main(void){
	if(colorActive == 1){
		out_Color = vec4(red, green, blue, texture(modelTexture, passTextureCoord).a);
	}
	else{
		out_Color = texture(modelTexture, passTextureCoord);
	}
}
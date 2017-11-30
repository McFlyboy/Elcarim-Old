#version 400 core

in vec2 passTextureCoord;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform int horizontalTextureCount;
uniform int verticalTextureCount;
uniform int textureOffsetX;
uniform int textureOffsetY;
uniform int colorActive;
uniform vec3 color;

void main(void){
	vec4 tex = texture(modelTexture, vec2((passTextureCoord.x + textureOffsetX) / horizontalTextureCount, (passTextureCoord.y + textureOffsetY) / verticalTextureCount));
	if(colorActive == 1){
		out_Color = vec4(color, tex.a);
	}
	else{
		out_Color = tex;
	}
}
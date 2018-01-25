#version 400 core

in vec2 passTextureCoord;

out vec4 out_Color;

uniform float sceneBrightness;
uniform sampler2D modelTexture;
uniform int horizontalTextureCount;
uniform int verticalTextureCount;
uniform int textureOffsetX;
uniform int textureOffsetY;
uniform int monochrome;
uniform int colorActive;
uniform vec3 color;

void main(void){
	vec4 tex = texture(modelTexture, vec2((passTextureCoord.x + textureOffsetX) / horizontalTextureCount, (passTextureCoord.y + textureOffsetY) / verticalTextureCount));
	if(colorActive == 1){
		if(monochrome == 1){
			float colorAvg = (color.r + color.g + color.b) / 3f;
			vec3 monoColor = vec3(colorAvg, colorAvg, colorAvg);
			out_Color = vec4(monoColor, tex.a);
		}
		else{
			out_Color = vec4(color, tex.a);
		}
	}
	else{
		if(monochrome == 1){
			float colorAvg = (tex.r + tex.g + tex.b) / 3f;
			out_Color = vec4(colorAvg, colorAvg, colorAvg, tex.a);
		}
		else{
			out_Color = tex;
		}
	}
	out_Color.rgb *= sceneBrightness;
}
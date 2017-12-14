package com.nyhammer.p96.structure;

import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.util.Color3f;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class Menu{
	public enum PositionType{
		FROM_LEFT, FROM_CENTER, FROM_RIGHT;
	}
	private TextField[] optionTexts;
	private int activeOption;
	private boolean optionSelected;
	private boolean optionContinue;
	private TargetTimer continueTimer;
	private Vector2f position;
	private float spacing;
	private PositionType positionType;
	public Menu(Timer baseTimer, String... options){
		optionTexts = new TextField[options.length];
		for(int i = 0; i < options.length; i++){
			optionTexts[i] = new TextField();
			optionTexts[i].setText(options[i]);
		}
		activeOption = 0;
		optionSelected = false;
		optionContinue = false;
		continueTimer = new TargetTimer(baseTimer, 0.2);
		position = new Vector2f();
		spacing = 0.3f;
		positionType = PositionType.FROM_CENTER;
		updatePositions();
		updateColors();
	}
	public int getActiveOption(){
		return activeOption;
	}
	public boolean isOptionSelected(){
		return optionSelected;
	}
	public void setOptionSelected(boolean selected){
		optionSelected = selected;
		if(selected){
			optionTexts[activeOption].setColors(new Color3f(1f, 0f, 0f));
			continueTimer.resume();
		}
		else{
			optionTexts[activeOption].setColors(new Color3f(1f, 1f, 0f));
		}
	}
	public boolean isOptionContinue(){
		if(!optionContinue){
			if(continueTimer.targetReached()){
				continueTimer.reset();
				optionContinue = true;
			}
		}
		return optionContinue;
	}
	public void setOptionContinue(boolean optionContinue){
		this.optionContinue = optionContinue;
	}
	public void setPosition(Vector2f position){
		this.position = position;
		updatePositions();
	}
	public void setSpacing(float spacing){
		this.spacing = spacing;
		updatePositions();
	}
	public void setPositionType(PositionType type){
		positionType = type;
		updatePositions();
	}
	public void scrollUp(){
		activeOption--;
		if(activeOption < 0){
			activeOption = optionTexts.length -1;
		}
		ResourceStorage.getSound("optionSound").play();
		updateColors();
	}
	public void scrollDown(){
		activeOption++;
		if(activeOption == optionTexts.length){
			activeOption = 0;
		}
		ResourceStorage.getSound("optionSound").play();
		updateColors();
	}
	public void render(){
		if(optionContinue){
			return;
		}
		for(TextField optionText : optionTexts){
			Render.addToQueue(optionText);
		}
	}
	private void updatePositions(){
		for(int i = 0; i < optionTexts.length; i++){
			if(positionType == PositionType.FROM_CENTER){
				optionTexts[i].position.x = position.x;
			}
			else if(positionType == PositionType.FROM_LEFT){
				optionTexts[i].position.x = position.x + optionTexts[i].getWidth() / 2f;
			}
			else{
				optionTexts[i].position.x = position.x - optionTexts[i].getWidth() / 2f;
			}
			optionTexts[i].position.y = position.y + spacing * (float)(optionTexts.length - 1) / 2f - spacing * i;
		}
	}
	public void updateColors(){
		for(TextField optionText : optionTexts){
			optionText.setColors(new Color3f(1f, 1f, 1f));
		}
		optionTexts[activeOption].setColors(new Color3f(1f, 1f, 0f));
	}
	public void reset(){
		activeOption = 0;
		updateColors();
		optionSelected = false;
		optionContinue = false;
		continueTimer.reset();
	}
}
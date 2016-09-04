package it.emarolab.cagg.debugging.baseComponents;

import it.emarolab.cagg.debugging.UILog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

///// UTILITY CLASS FOR CLICK LISTENER
public abstract class BooleanButtonListener implements ActionListener{

	private final JGradientBooleanButton booleanBtn;

	public BooleanButtonListener(JGradientBooleanButton jGradientBooleanButton) {
		this.booleanBtn = jGradientBooleanButton;
	}
	
	abstract public void doOnAction( ActionEvent e);
	abstract public void doOffAction( ActionEvent e);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// change state at every click
		this.booleanBtn.changeState(); // update also labels
		// call auctioneers
		try{
			if( this.booleanBtn.isPressed())
				doOnAction( e);	
			else doOffAction( e);
		} catch( Exception ex){
			UILog.error(ex);
			this.booleanBtn.changeState(); // reset
		}
	}
}
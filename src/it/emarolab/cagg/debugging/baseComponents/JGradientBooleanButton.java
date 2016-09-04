package it.emarolab.cagg.debugging.baseComponents;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JButton;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.JGradientButton.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends the standard {@link javax.swing.JButton} by appling a color gradient on it.<br>
 * For references, see: <a href="http://stackoverflow.com/questions/7115672/change-jbutton-gradient-color-but-only-for-one-button-not-all">this thread</a>.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.DeserialisationGui
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 */
@SuppressWarnings("serial")
public final class JGradientBooleanButton extends JButton{
    
	private Color color1, color2;
	private boolean active = true;
	private String actualLabel, onLabel, offLabel;
	
	public JGradientBooleanButton( String onLabel, String offLabel, Color c1, Color c2){
		// interface to JGradientButton
		super( "");
		this.color1 = c1;
		this.color2 = c2;
        setContentAreaFilled(false);
        setFocusPainted(false); // used for demonstration
        // interface for boolean button
        this.onLabel = onLabel;
        this.offLabel = offLabel;
        setLabel();
    }
	
	////// INTERNAL SETTERS
	protected void setLabel(){
		if( this.active)
			this.actualLabel = this.onLabel;
		else this.actualLabel = this.offLabel;
		this.setText( this.actualLabel);
	}
	public void changeState(){
		this.active = ! active;
		this.setLabel();
	}
	@Override
	public void setText( String text){
		//UILog.warning( "You should not call setText on the boolean button: " + this + ". It would be automatically managed trhough listeners.");
		super.setText( text);
	}
	///// GETTERS
    public Boolean isPressed() {
		return ! active;
	}
    public Boolean isActive() {
		return active;
	}
	public String getActuallabel() {
		return actualLabel;
	}

	@Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint( new Point(0, 0), color1,  new Point(0, getHeight()), color2));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}
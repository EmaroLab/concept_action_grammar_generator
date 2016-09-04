package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.SourceDebuggingPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel}
 * to show the source text and allows the user to modify it. To node that the parsing procedure used in the GUI always
 * consider the last source text configuration shown in this panel to proceed with the system computations.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.DebuggingGui
 */
@SuppressWarnings("serial")
public class SourceDebuggingPanel extends BaseDebugginGuiPanel{
	
	private JEditorPane sourceTextPane;
	
	// default constructors
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public SourceDebuggingPanel(JSplitPane parent, String title, Color c1, Color c2) {
		super(parent, title, c1, c2);
	}
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color, Boolean)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 * @param isRight set to {@code true} if this panel should be placed as a right child of the parent {@code JSplitPane}. 
	 * {@code false} otherwise.
	 */
	public SourceDebuggingPanel(JSplitPane parent, String title, Color c1, Color c2, Boolean isRight) {
		super(parent, title, c1, c2, isRight);
	}
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JPanel, String, Color, Color)}.
	 * @param parent the {@code JPanel} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public SourceDebuggingPanel(JPanel parent, String title, Color c1, Color c2) {
		super(parent, title, c1, c2);
	
	}
	
	
	// visualisation (custom setting with respect to the super class [contentPanel])
	/** 
	 * In this panel the layout of the {@code contentPanel} is set to {@code BoxLayout(contentPanel, BoxLayout.PAGE_AXIS)}.
	 * Than, it sets the {@code contentScrollPane} to never scroll horizontally for text line wrap.
	 * Finally, it adds a {@link javax.swing.JEditorPane} in which show the source text and set its behavior.
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()
	 **/
	@Override
	public void setContents(){
		super.setContents();
		// set content panel (it is inside the scroll)
		contentPanel.setLayout( new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		// add text area
		sourceTextPane = new JEditorPane();
		//sourceTextPane.setDocument( new TabbedDocument());
		contentPanel.add( sourceTextPane);
	}
	/*public static final int TAB_SIZE = 4;
	static class TabbedDocument extends DefaultStyledDocument {
		private int lineCharCount = 0;
    	@Override
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    		if( str.equals( DebuggingDefaults.SYS_LINE_SEPARATOR))
    			lineCharCount = 0;
    		else if( str.equals( "\t")){
    			Integer tabOffs = TAB_SIZE - ( lineCharCount % TAB_SIZE);
    			if( tabOffs == 0)
    				tabOffs = TAB_SIZE;
    			lineCharCount += tabOffs;
    			String tabReplace = "";
    			for( int i = 0; i < tabOffs; i++)
    				tabReplace += " ";
    			str = str.replace( "\t", tabReplace);
    		} else lineCharCount += 1;
    		super.insertString(offs, str, a);
    	}
    }*/
	
	// get text (if it has been changed from text area)
	/**
	 * @return the actual text shown in this panel .
	 */
	public String getText(){
		return sourceTextPane.getText();
	}
	/**
	 * @param text the text to be shown in this panel.
	 */
	public void setText( String text){
		int caretPosition = sourceTextPane.getCaretPosition();
		sourceTextPane.setText( text + DebuggingDefaults.ADD_TO_SOUCE_TAIL);
		sourceTextPane.setCaretPosition(Math.min(caretPosition, text.length())); // set scroll to top
	}
}

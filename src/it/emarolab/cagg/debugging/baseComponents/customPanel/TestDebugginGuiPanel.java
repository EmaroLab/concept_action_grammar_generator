package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.core.evaluation.interfacing.GuiEvaluationInterface;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.DebuggingGui.DebuggingStaticActioner;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.JGradientBooleanButton;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JSplitPane;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.text.DefaultCaret;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel}
 * to allow user to insert input and analyse system results. Particularly, the user input are given in the realted text field
 * as a sentences in order to simulate a speaking situation. While, the generic system results are shown in the text field below.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 * @see it.emarolab.cagg.core.evaluatorOLD.GUISemanticEvaluator
 * @see it.emarolab.cagg.core.evaluatorOLD.InputFormatter
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.DeserialisationGui
 */
@SuppressWarnings("serial")
public class TestDebugginGuiPanel extends BaseDebugginGuiPanel {

	// private class fields
	private JEditorPane inTextPane;
	private JTextArea outTextPane;
	// for call test on enter (be sure to call setTestObject before to test the grammar) 
	private GrammarBase<?> merger = null;
	private SemanticTreeGuiPanel treePanel = null;
	
	private JGradientBooleanButton booleanButton = null; // for start/stop state uodate on pressed enter key 
	private GuiEvaluationInterface evaluator = null;
	
	// constructors
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public TestDebugginGuiPanel(JSplitPane parent, String title, Color c1, Color c2) {
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
	public TestDebugginGuiPanel(JSplitPane parent, String title, Color c1, Color c2, Boolean isRight) {
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
	public TestDebugginGuiPanel(JPanel parent, String title, Color c1, Color c2) {
		super(parent, title, c1, c2);
	}

	// initialisation
	/**
	 * Set the object used from this test panel. Be sure to call this method before to use this panel.
	 * By default it is called when the button {@code Create} is clicked.
	 * @param merger the object describing the complete parsed grammar.
	 * @param treePanel the panel to show the complete rule tree.
	 */
	public void setTestObject( GrammarBase<?> merger, SemanticTreeGuiPanel treePanel){
		this.merger = merger;
		this.treePanel = treePanel;
	}

	// visualisation (called in constructor from super class)
	/**
	 * In this panel the layout of the {@code contentPanel} is set to {@code BoxLayout(contentPanel, BoxLayout.PAGE_AXIS)}.
	 * Than, it sets labels to give information to the user, as well as input component {@link javax.swing.JEditorPane} and
	 * output component {@link javax.swing.JTextArea}.
	 * Finally, it implements {@code enter} key listener in to compute the system solution 
	 * (the same action as hitting the button {@code Test}). 
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()
	 **/
	@Override
	public void setContents(){ 
		super.setContents();
		// set content panel (it is inside the scroll)
		contentPanel.setLayout( new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		// set input instruction label and separator
		addLabel( DebuggingDefaults.LABEL_INTEST);
		addSeparator();
		// add and set input text field
		inTextPane = new JEditorPane();
		final TestDebugginGuiPanel testPanel = this;
		inTextPane.addKeyListener(new KeyListener() {
			@Override
		    public void keyReleased(KeyEvent e) {
				if( e.getKeyCode() == KeyEvent.VK_ENTER){	// if press enter
					inTextPane.setText( inTextPane.getText().replace( DebuggingDefaults.SYS_LINE_SEPARATOR, "")); // remove new line from input
					if( booleanButton != null){
						if( booleanButton.isActive()){
							evaluator = DebuggingStaticActioner.testClick( merger, testPanel, treePanel, booleanButton);
							booleanButton.changeState();
						}
					} else UILog.warning( "no bullean button set for key enter action.");
				}
		    }
			@Override public void keyTyped(KeyEvent e) {}
			@Override public void keyPressed(KeyEvent e) {}
		});
		inTextPane.setBackground( DebuggingDefaults.COLOR_TEST_FIELD);
		contentPanel.add( inTextPane);
		
		// set outout instruction label and separator
		addSeparator();
		addLabel( DebuggingDefaults.LABEL_OUTTEST);
		addSeparator();
		// add and set output text field
		outTextPane = new JTextArea();
		outTextPane.setBackground( DebuggingDefaults.COLOR_TEST_FIELD);
		outTextPane.setEditable(false);
		outTextPane.setLineWrap( true);
		outTextPane.setWrapStyleWord(true);
		outTextPane.setFont( DebuggingDefaults.DEFAULT_GUI_FONT);
		
		DefaultCaret caret = (DefaultCaret) outTextPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		contentPanel.add( outTextPane);
	}
	
	
	// simulated in/out interface  and update visualising contents
	/**
	 * Get the data written by the user in the relate text field.
	 * @return the string given from the user as speaking simulation.
	 */
	public String getInputText(){ // it simulate peach
		this.setOutputText( ""); // reset
		return this.inTextPane.getText();
	}
	/**
	 * Write the system result to the user input in the relate text field.
	 * @param results the result descriptions to show to the user.
	 */
	public void setOutputText( String results){ // it shows simulated software results.
		this.outTextPane.setText( results);
	}
	public void appendToOuptuText( String result){
		this.outTextPane.setText( this.outTextPane.getText() + result);
	}
	public void appendToOuptuTextLn( String result){
		appendToOuptuTextLn( result + DebuggingDefaults.SYS_LINE_SEPARATOR);
	}
	

	
	
	/*public JGradientBooleanButton getBooleanButton() {
		return booleanButton;
	}*/
	public void setBooleanButton(JGradientBooleanButton booleanButton) {
		this.booleanButton = booleanButton;
	}
	public GuiEvaluationInterface getEvaluator() {
		GuiEvaluationInterface tmp = evaluator; // consume evaluator
		evaluator = null;
		return tmp;
	}
}


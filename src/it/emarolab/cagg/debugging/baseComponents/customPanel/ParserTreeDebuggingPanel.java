package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser;
import it.emarolab.cagg.core.language.parser.ANTLRInterface.ANTLRGenerated.CAGGSyntaxDefinitionParser.NuanceGrammarContext;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.antlr.v4.runtime.tree.gui.TreeViewer;



/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.ParserTreeDebuggingPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel}
 * to shows the parsing tree resulting form the ANTLR library.
 * </p>
 *
 * @see it.emarolab.cagg.core.language.parser.TextualParser
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 */
@SuppressWarnings("serial")
public class ParserTreeDebuggingPanel extends BaseDebugginGuiPanel{

	// constructors
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public ParserTreeDebuggingPanel(JSplitPane parent, String title, Color c1, Color c2) {
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
	public ParserTreeDebuggingPanel(JSplitPane parent, String title, Color c1, Color c2, Boolean isRight) {
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
	public ParserTreeDebuggingPanel(JPanel parent, String title, Color c1, Color c2) {
		super(parent, title, c1, c2);
	}
	
	// visualisation
	/**
	 * In this pane implementation it does not call it super method {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()} 
	 * since the contentPanel should not be further set.
	 **/
	@Override
	public void setContents(){  // called in the constructor
		// do not call the super class method (the content panel should not be set !!!)
	}
	
	/**
	 * Given the object to call ANTLR parsing this method updates and shows the resulting parsing tree.
	 * @param textualParser the object that execute the ANTLR library to parse the source grammar.
	 */
	public void visualizeTree( TextualParser textualParser){
		NuanceGrammarContext tree = textualParser.getTree();
		CAGGSyntaxDefinitionParser parser = textualParser.getParser();
		if( tree != null && parser != null){
			removeContents(); // clear this gui tab
			 // create visualisation tree and show on windows
	        TreeViewer viewer = new TreeViewer( Arrays.asList( parser.getRuleNames()), tree);
			viewer.setScale( DebuggingDefaults.TREE_SCALE_TYPE);//scale a little
			contentPanel.add( viewer);
			updateContents(); // update this gui tab 
			UILog.info( "parsing tree pannel refreshed in GUI.");
		} else UILog.error( "cannot visualize parsing tree (null tree or parser) !!!");
	}
}

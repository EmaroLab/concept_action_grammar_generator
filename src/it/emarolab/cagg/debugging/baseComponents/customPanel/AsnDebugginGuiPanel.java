package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JSplitPane;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel}
 * to show the AST node data in the GUI when it is selected from the {@link it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel}. 
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.DataFactoryVisualizer
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 */
@SuppressWarnings("serial")
public class AsnDebugginGuiPanel extends SubSplitDebuggingGuiPanel {

	// default constructors
	/**
	 * Just calls its super class constructor:
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel#SubSplitDebuggingGuiPanel( JSplitPane, JSplitPane, String, Color, Color)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing the split view of this object.
	 * @param subparent the the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object. 
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public AsnDebugginGuiPanel( JSplitPane parent, JSplitPane subparent, String title, Color c1, Color c2) {
		super( parent, subparent, title, c1, c2);
	}
	/**
	 * Just calls its super class constructor:
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel#SubSplitDebuggingGuiPanel( JSplitPane, JSplitPane, String, Color, Color, Boolean)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing the split view of this object.
	 * @param subparent the the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object. 
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 * @param isRight set to {@code true} if this panel should be placed as a right child of the parent {@code JSplitPane}. 
	 * {@code false} otherwise.
	 */
	public AsnDebugginGuiPanel( JSplitPane parent, JSplitPane subparent, String title, Color c1, Color c2, Boolean isRight) {
		super( parent, subparent, title, c1, c2, isRight);
	}
	
	// visualisation (called in constructor from super class)
	/**
	 * In this panel the layout of the {@code contentPanel} is set to {@code BoxLayout(contentPanel, BoxLayout.PAGE_AXIS)}.
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()
	 */
	@Override
	public void setContents(){
		// set content panel (it is inside the scroll)
		super.setContents();
		contentPanel.setLayout( new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
	}
	
	// called on ast node selection
	/**
	 * Visualise the node data in this panel. by calling {@code abstractSyntaxNodeData.getVisualizer().showContents(...)}.
	 * @param abstractSyntaxNodeData the node which the data is shown in this panel
	 */
	public void visualizeNodeData( AbstractNodeData<?> abstractSyntaxNodeData){
		if( abstractSyntaxNodeData != null){
			removeContents(); // clear this gui tab
			// create visualisation tree and show on windows
			abstractSyntaxNodeData.getVisualizer().showContents( abstractSyntaxNodeData, contentPanel);//.addDataToGui( contentPanel);
			updateContents(); // update this gui tab 
			UILog.info( "parsing tree pannel refreshed in GUI.");
		} else UILog.error( "cannot visualize abstract syntax node (null data) !!!");
	}

	// called on ast node selection
	/**
	 * Visualise the node data in this panel. by calling {@code abstractSyntaxNodeData.getVisualizer().showContents(...)}.
	 * @param abstractSyntaxNodeData the node which the data is shown in this panel
	 */
	public void visualizeNodeData( AbstractNodeDoubleData<?,?> abstractSyntaxNodeData){
		if( abstractSyntaxNodeData != null){
			removeContents(); // clear this gui tab	
			// create visualisation tree and show on windows
			abstractSyntaxNodeData.getVisualizer().showContents( abstractSyntaxNodeData, contentPanel);//addDataToGui( contentPanel);
			updateContents(); // update this gui tab 
			UILog.info( "parsing tree pannel refreshed in GUI.");
		} else UILog.error( "cannot visualize abstract syntax node (null data) !!!");
	}
}

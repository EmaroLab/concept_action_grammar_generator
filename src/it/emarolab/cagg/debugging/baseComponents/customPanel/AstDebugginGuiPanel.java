package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.core.language.parser.ANTLRInterface.TreeParserListener;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;

import java.awt.Color;

import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel}
 * to shown the Abstract Syntax Tree (AST) obtained from the parsing listening.<br>
 * Moreover, if the node of the AST are selected their contents are displayed in the {@code dataPanel} give on
 * construction for easy parsed data debugging and visualisations.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 * @see it.emarolab.cagg.core.language.parser.ANTLRInterface.TreeParserListener
 **/
@SuppressWarnings("serial")
public class AstDebugginGuiPanel  extends SubSplitDebuggingGuiPanel {

	// fields
	private AsnDebugginGuiPanel dataPanel; 
	
	// default constructors
	/**
	 * Calls its super class constructor:
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel#SubSplitDebuggingGuiPanel( JSplitPane, JSplitPane, String, Color, Color)}.
	 * Moreover, it gets the dataPanel in which the node contents should be visualised on click.
	 * @param dataPanel the panel in which visualise the sub contents of this panel by components click
	 * @param parent the {@link javax.swing.JSplitPane} containing the split view of this object.
	 * @param subparent the the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object. 
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public AstDebugginGuiPanel( AsnDebugginGuiPanel dataPanel, JSplitPane parent, JSplitPane subparent, String title, Color c1, Color c2) {
		super( parent, subparent, title, c1, c2);
		this.dataPanel = dataPanel;
	}
	/**
	 * Just calls its super class constructor:
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel#SubSplitDebuggingGuiPanel( JSplitPane, JSplitPane, String, Color, Color, Boolean)}.
	 * Moreover, it gets the dataPanel in which the node contents should be visualised on click.
	 * @param dataPanel the panel in which visualise the sub contents of this panel by components click
	 * @param parent the {@link javax.swing.JSplitPane} containing the split view of this object.
	 * @param subparent the the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object. 
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 * @param isRight set to {@code true} if this panel should be placed as a right child of the parent {@code JSplitPane}. 
	 * {@code false} otherwise.
	 */
	public AstDebugginGuiPanel( AsnDebugginGuiPanel dataPanel, JSplitPane parent, JSplitPane subparent, String title, Color c1, Color c2, Boolean isRight) {
		super( parent, subparent, title, c1, c2, isRight);
		this.dataPanel = dataPanel;
	}

	// visualisation (called in constructor from super class)
	/**
	 * In this pane implementation it does not call it super method {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()} 
	 * since the contentPanel should not be further set.
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()
	 */
	@Override
	public void setContents(){
		// do not call super methods (the content panel should not be set!!)
	}

	// called on button click
	/**
	 * Update and visualise the AST. This method also add the node listener used to update the
	 * {@code dataPanel} on node selection.
	 * @param parserListener the object that computes the AST during parsing listerner. Obtained by calling:
	 * {@link it.emarolab.cagg.core.language.parser.ANTLRInterface.TreeParserListener#getAbstractSyntaxTree()}.
	 */
	public void visualizeTree( TreeParserListener parserListener){
			JTree ast = parserListener.getAbstractSyntaxTree();
			visualiseTree( ast);
	}
	/**
	 * Update and visualise the AST. This method also add the node listener used to update the
	 * {@code dataPanel} on node selection.
	 * @param ast the root of the AST (so, a {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode} node) 
	 * to be visualised given as {@code new JTree( root)}.
	 */
	public void visualiseTree( JTree ast){
		if( ast != null){
			removeContents(); // clear this gui tab
			// create visualisation tree and show on windows
			expandAllNodes( ast);
			contentPanel.add( ast);
			updateContents(); // update this gui tab 
			UILog.info( "parsing tree pannel refreshed in GUI.");
			// add selection listener for node data visualization
			ast.addTreeSelectionListener(createSelectionListener());
		} else UILog.error( "cannot visualize abstract syntax tree (null root) !!!");
	}
	

	// create AST node selection listener in order to show the node contents in the dataPanel components
	private TreeSelectionListener createSelectionListener() {
		return new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent ev) {
				try{
					// get selected node
					AbstractSyntaxNode<?> selectedNode = ( AbstractSyntaxNode<?>) ev.getPath().getPathComponent( ev.getPath().getPathCount()-1);
					UILog.info( "Selected node of AST: " + selectedNode.toString());
					
					if(selectedNode.getData() instanceof AbstractNodeData<?>)
						dataPanel.visualizeNodeData( (AbstractNodeData<?>) selectedNode.getData()); // update the view with selected node data
					else if(selectedNode.getData() instanceof AbstractNodeDoubleData<?,?>)
						dataPanel.visualizeNodeData( (AbstractNodeDoubleData<?,?>) selectedNode.getData()); // update the view with selected node data
					else UILog.error("Abstract Syntax Tree node data cannot be visualized in GUI !");
					updateContents();
				} catch(Exception ex){
					UILog.error( "The node of the Abstract Syntax Tree must be classes that extends AbstractSyntaxNode<?> !");
					UILog.error( ex);
				}
			}
		};
	}

}

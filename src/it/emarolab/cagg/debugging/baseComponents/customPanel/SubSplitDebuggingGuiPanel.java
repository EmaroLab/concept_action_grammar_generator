package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel}.
 * Its main purpose is to have a panel that can be added to another {@link javax.swing.JSplitPane}. 
 * </p>
 *
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.DebuggingDefaults
 */
@SuppressWarnings("serial")
public class SubSplitDebuggingGuiPanel extends BaseDebugginGuiPanel {
	
	// default constructors + sub split tile click managing
	/**
	 * Calls its super class constructor with the relative input parameters (by using {@code subparent} as {@code parent}):
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}.
	 * Moreover, add it into a sub split view with a title and a one more collapsing/expanding behavior using double click on title.  
	 * @param parent the {@link javax.swing.JSplitPane} containing the split view of this object.
	 * @param subparent the the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object. 
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public SubSplitDebuggingGuiPanel( JSplitPane parent, JSplitPane subparent, String title, Color c1, Color c2) {
		super( subparent, title, c1, c2);
		subViewExpandeCollapse( this.getTitleBtn(), parent);
	}
	/**
	 * Calls its super class constructor with the relative input parameters (by using {@code subparent} as {@code parent}):
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color, Boolean)}.
	 * Moreover, add it into a sub split view with a title and a one more collapsing/expanding behavior using double click on title.  
	 * @param parent the {@link javax.swing.JSplitPane} containing the split view of this object.
	 * @param subparent the the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object. 
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 * @param isRight set to {@code true} if this panel should be placed as a right child of the parent {@code JSplitPane}. 
	 * {@code false} otherwise.
	 */
	public SubSplitDebuggingGuiPanel( JSplitPane parent, JSplitPane subparent, String title, Color c1, Color c2, Boolean isRight) {
		super( subparent, title, c1, c2, isRight);
		subViewExpandeCollapse( this.getTitleBtn(), parent);
	}
	
	// add double click listener for sub view collapse
	private void subViewExpandeCollapse( JButton btn, final JSplitPane parent){
		btn.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		            collapseExpand( parent);
		        }
		    }
		});
	}
	
	// function to expand all nodes of a jtree
	/**
	 * Expand all nodes of the input tree
	 * @param tree the structure to be expanded.
	 */
	public static void expandAllNodes(JTree tree){
		expandNodes( tree, 0, tree.getRowCount(), true, false); // if true expand // no smart value manipulate all node
	}
	/**
	 * Expand or collapse all tree nodes.
	 * @param tree the structure to be expanded.
	 * @param expandAll set to {@code true} to expand or {@code false} for collapse.
	 */
	public static void expandAllNodes(JTree tree, Boolean expandAll) {
		expandNodes( tree, 0, tree.getRowCount(), expandAll, false); // if false collapse. // no smart value manipulate all node
	}
	/**
	 * Expand all the tree node that have {@code true} value and collapse all the other node that have {@code null} or {@code false} value.
	 * @param tree the structure to be expanded.
	 * @param expandAll set to {@code true} to expand or {@code false} for collapse.
	 * @param smartValue set to {@code true} to consider the node value during expanding/collapsing. Set to {@code false}
	 * to perform the same operations as: {@link #expandAllNodes(JTree, Boolean)}.
	 */
	public static void expandAllNodes(JTree tree, Boolean expandAll, Boolean smartValue) {
		expandNodes( tree, 0, tree.getRowCount(), expandAll, smartValue); // if false collapse // smart value manipulate w.r.t. node value
	}
	private static void expandNodes( JTree tree, int startingIndex, int rowCount, Boolean expand, Boolean smartValue){
	    for(int i=startingIndex;i<rowCount;++i){
	    	if( ! smartValue){
		        if( expand)
		        	tree.expandRow(i);
		        else tree.collapseRow(i);
	    	} else {	// stop to true value   DA RIVEDERE !!!!!!!!!!!!!
	    		TreePath path = tree.getPathForRow(i);
	    		if( path != null){ 
	    			Object nodeTemptative = path.getLastPathComponent();
		    		if( nodeTemptative != null){
			    		if( nodeTemptative instanceof SemanticExpressionNodeBase){
			    			SemanticExpressionNodeBase node = ( SemanticExpressionNodeBase) nodeTemptative;
			    			Boolean value = node.getValue();
			    			if( value == null)
			    				value = false; // manipulate null value
			    			if( expand && value)
			    				tree.expandRow(i);
			    			if( !expand && !value)
			    				tree.collapseRow(i);
			    		}
	    			}
	    		}
	    	}
	    }
	    if(tree.getRowCount()!=rowCount){
	    	expandNodes(tree, rowCount, tree.getRowCount(), expand, smartValue);
	    }
	}

}

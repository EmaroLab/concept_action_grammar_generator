package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.core.BaseNode;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.CompleteTreeGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel}
 * to show the main complete grammar rule tree. This tree is obtained from all the expression tree nested with respect to the
 * main rule and sub rules. Particularly, this panel shown by tabs a different copied tree for each found solutions.<br>
 * Moreover, this class change the default title click of its super class by adding a method to easily collapse/expand only the
 * node evaluated as true.<br>
 * Finally, it also implement the {@link SemanticTreeGuiPanel.MyTreeCellRenderer} in order to change the color of the 
 * complete tree node with respect to their evaluating values.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 * @see SemanticTreeGuiPanel.MyTreeCellRenderer
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
 * @see it.emarolab.cagg.core.evaluatorOLD.InputFormatter
 * @see it.emarolab.cagg.core.evaluatorOLD.GUISemanticEvaluator
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.DeserialisationGui
 */
@SuppressWarnings("serial")
public class SemanticTreeGuiPanel  extends BaseDebugginGuiPanel  {

	// private fields
	private List< JTree> visualisedTree = new ArrayList< JTree>();
	private JTabbedPane tabs;
	private static Boolean expandAll = true;
		
	// default constructors
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public SemanticTreeGuiPanel(JPanel parent, String title, Color c1, Color c2) {
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
	public SemanticTreeGuiPanel(JSplitPane parent, String title, Color c1, Color c2, Boolean isRight) {
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
	public SemanticTreeGuiPanel(JSplitPane parent, String title, Color c1, Color c2) {
		super(parent, title, c1, c2);
	}

	// add title click behavior in order to expand/collapse only the true node (smart value=true)
	/** 
	 * It implements the smart tree expanding collapsing defined in 
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel#expandAllNodes(JTree, Boolean, Boolean)}
	 * for the complete main tree in the selected tabled. This action is triggered
	 * when the user click on the title of this panel.
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#perfromOnTitleClick(java.awt.event.ActionEvent)
	 **/
	@Override
	protected void perfromOnTitleClick(ActionEvent e){
		int selectedIdx = tabs.getSelectedIndex();
		if( visualisedTree != null){
			if( selectedIdx >= 0 && selectedIdx < visualisedTree.size()){
				JTree selectedTree = visualisedTree.get(selectedIdx);
				SubSplitDebuggingGuiPanel.expandAllNodes( selectedTree, expandAll, true); // (smart value = true)
				expandAll = ! expandAll;
				this.updateContents();
			}
		}
	}
	
	// visualisation
	/**
	 * It is initialise the default contentPanel setting by calling 
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()}.
	 * Moreover, it also create the {@link javax.swing.JTabbedPane} that will be used to show the complete main rule tree
	 * and their results with respect to possible input combinations.
	 **/
	@Override
	public void setContents(){  // called in the constructor
		super.setContents();	// do not add nothing special as long as button is clicked
		JPanel panel = new JPanel( new BorderLayout(0, 0));
		contentPanel.add( panel);
		
		// add search panel
		panel.add( setFindPanle(), BorderLayout.SOUTH);
		
		// add tab for more grammars (bottom level tab panel)
		tabs = new JTabbedPane();	// add new tab container
		tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		panel.add( tabs);
		
	}
	/// find implementation
	private List< TreePath> findPaths;
	private int actualFindIdx;
	private JPanel setFindPanle(){
		JPanel findPanel = new JPanel( new BorderLayout(0, 0));
		// add text field
		final JTextField textField = new JTextField();
		findPanel.add( textField);
		// add enter listener to the find text field
		textField.addKeyListener(new KeyListener() {
			@Override
		    public void keyReleased(KeyEvent e) {
				if( e.getKeyCode() == KeyEvent.VK_ENTER){	
					// if press enter search
					findOnSemanticExpression( textField.getText());
				} else if( e.getKeyCode() == KeyEvent.VK_UP | e.getKeyCode() == KeyEvent.VK_RIGHT){
					// if press up or right arrow select next find item
					getNextItemFinded();
				} else if( e.getKeyCode() == KeyEvent.VK_DOWN | e.getKeyCode() == KeyEvent.VK_LEFT){
					// if press up or right arrow select previous find item
					getPreviousItemFinded();
				}
		    }
			@Override public void keyTyped(KeyEvent e) {} // nothing
			@Override public void keyPressed(KeyEvent e) {} // nothing
		});
		// add buttons
		JPanel btnsPanel = new JPanel( new FlowLayout());
		// find btn
		JButton findBtn = new JButton(  DebuggingDefaults.BTN_FIND_TITLE);								
		btnsPanel.add( findBtn);
		// add click listener to find button
		findBtn.addActionListener( new ActionListener(){
		    public void actionPerformed(ActionEvent e){
		    	findOnSemanticExpression( textField.getText());
		    }
		});
		// next btn
		JButton nextBtn = new JButton(  DebuggingDefaults.BTN_FIND_NEXT_TITLE);								
		btnsPanel.add( nextBtn);
		// add click listener to find button
		nextBtn.addActionListener( new ActionListener(){
		    public void actionPerformed(ActionEvent e){
		    	getNextItemFinded();
		    }
		});
		// previous btn
		JButton previousBtn = new JButton(  DebuggingDefaults.BTN_FIND_PREVIOUS_TITLE);								
		btnsPanel.add( previousBtn);
		// add click listener to find button
		previousBtn.addActionListener( new ActionListener(){
		    public void actionPerformed(ActionEvent e){
		    	getPreviousItemFinded();
		    }
		});
		findPanel.add( btnsPanel, BorderLayout.EAST);
		return findPanel;
	}
	private void getNextItemFinded(){
		if( findPaths == null){
			UILog.warning( "no semantic expression item found !!!");
			return;
		}
		// implement circular array
		actualFindIdx++;
		if( actualFindIdx >= findPaths.size()) 
			actualFindIdx = 0;
		// select path
		if( actualFindIdx < findPaths.size()){
			TreePath path = findPaths.get( actualFindIdx);
			JTree selectedTree = visualisedTree.get( tabs.getSelectedIndex());
			selectedTree.setSelectionPath(path);
			selectedTree.scrollPathToVisible(path);
			UILog.info( "Show next (" + actualFindIdx + "/" + findPaths.size() + ") find item: " + path);
		} UILog.info( "item not found on the semantic tree !!!");
	}
	private void getPreviousItemFinded(){
		if( findPaths == null){
			UILog.warning( "no semantic expression item found !!!");
			return;
		}
		// implement cyrcular array
		actualFindIdx--;
		if( actualFindIdx < 0) 
			actualFindIdx = findPaths.size() - 1;
		// select path
		TreePath path = findPaths.get( actualFindIdx);
		JTree selectedTree = visualisedTree.get( tabs.getSelectedIndex());
		selectedTree.setSelectionPath(path);
		selectedTree.scrollPathToVisible(path);
		UILog.info( "Show previous (" + actualFindIdx + "/" + findPaths.size() + ") find item: " + path);
	}
	private void findOnSemanticExpression( String input){
		// get selected tree root
		if( tabs.getSelectedIndex() != -1){
			JTree selectedTree = visualisedTree.get( tabs.getSelectedIndex());
			actualFindIdx = -1;
			if( selectedTree != null){
				BaseNode root = (BaseNode) selectedTree.getModel().getRoot();
				UILog.info( "Search for: \"" + input + "\" on root node: " + root);
				findPaths = find( root, input);
				getNextItemFinded();
			} else {
				UILog.error( "cannot search on empty expressions !!");
				findPaths = null;
			}
		} else {
			UILog.error( "select an expression tree to search on it !!");
			findPaths = null;
		}
	}
	private List< TreePath> find( DefaultMutableTreeNode root, String str) {
	    List< TreePath> paths = new ArrayList<>();
	    @SuppressWarnings("unchecked")
		Enumeration< DefaultMutableTreeNode> e = root.breadthFirstEnumeration();
	    while (e.hasMoreElements()) {
	        DefaultMutableTreeNode node = e.nextElement();
	        if ( node.toString().toLowerCase().contains( str.toLowerCase()))
	        	paths.add( new TreePath(node.getPath()));
	    }
	    return paths;
	}
	
	
	/**
	 * It just create a list with a single element (given as input) and calls:
	 * {@link #visualizeTree(List)}
	 * @param node the complete expression tree to be shown.
	 */
	public void visualizeTree( SemanticExpressionNodeBase node){
		ArrayList< SemanticExpressionNodeBase> list = new ArrayList<>();
		list.add( node);
		visualizeTree( list);
	}
	
	private int treesSolutionId = 0;
	public void clearTrees(){
		synchronized( tabs){
			visualisedTree.clear();
			tabs.removeAll();
			treesSolutionId = 0;
		}
	}
	public void addTree( SemanticExpressionNodeBase node){
		synchronized( tabs){
			JTree tree = new JTree( node);
			// create visualisation tree and show on windows
			//SubSplitDebuggingGuiPanel.expandAllNodes( tree); // makes GUI not working on MAC !!!
			tree.setCellRenderer(new MyTreeCellRenderer());
			tabs.add( DebuggingDefaults.TESTTAB_TITLE_PREFIX + treesSolutionId++, tree);
			visualisedTree.add( tree);
			// update this GUI tab
			tabs.revalidate();
			updateContents(); 
			UILog.info( "complete rule tree pannel refreshed in GUI.");
		}
	}
	/**
	 * Update and show the contents of this panel by adding complete evaluated expression trees.
	 * @param list the list of complete expression trees to be shown in different tabs.
	 */
	public void visualizeTree( List< SemanticExpressionNodeBase> list){ // it clear previous trees !!!!!
		clearTrees();
		synchronized( tabs){
			for( SemanticExpressionNodeBase node : list){
				JTree tree = new JTree( node);
				// create visualisation tree and show on windows
				//SubSplitDebuggingGuiPanel.expandAllNodes( tree); // makes GUI not working on MAC !!!
				tree.setCellRenderer(new MyTreeCellRenderer());
				tabs.add( DebuggingDefaults.TESTTAB_TITLE_PREFIX + treesSolutionId++, tree);
				visualisedTree.add( tree);
			}
			tabs.revalidate();
			updateContents(); // update this GUI tab
			UILog.info( "complete rule tree pannel refreshed in GUI.");
		}
	}

	// render to change tree node text color base on their value
	/**
	 * @version SemanticGrammarParsing 1.0 <br>
	 * File: src:dotVocal.nuanceGoogleIntegration.semanticParser.debugginGui.baseComponents.customPanel.CompleteTreeGuiPanel.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS emaroLab,<br> 
	 * University of Genoa. <br>
	 * Dec 2, 2015 <br>
	 *  
	 * <p>
	 * This class extends {@link  javax.swing.tree.DefaultTreeCellRenderer} in order to set the node color with respect to 
	 * their value {@link  it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld#getValue()} 
	 * that can be: {@code null}, {@code true} or {@code false}. <br>
	 * This particular behavior is add to all (as much as all the possible solutions) the complete main expression tree shown in this panel
	 * </p>
	 *
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 * @see javax.swing.tree.DefaultTreeCellRenderer
	 * @see SemanticTreeGuiPanel
	 * @see it.emarolab.cagg.core.evaluatorOLD.InputFormatter
	 * @see it.emarolab.cagg.core.evaluatorOLD.GUISemanticEvaluator
	 */
	class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		private Boolean nodeValue = null;
		
		/**
		 * Given a tree node this methods retrieve its value through: 
		 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld#getValue()}.
		 * Then it calls {@link #setBackgroundSelectionColor(Color)} and {@link #setForeground(Color)}.
		 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
		 **/
		@Override
	    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
	        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);
	        // get expression node value
	        nodeValue = (( SemanticExpressionNodeBase) value).getValue();   
	        this.setForeground( getEvaluationColor());
	        this.setBackgroundSelectionColor( getSelectionColor());
	        return this;
	    }

		private Color getEvaluationColor(){
			if ( nodeValue == null)
	        	return DebuggingDefaults.COLOR_TREE_NULL_VALUE; // set color if value is null
	        if( nodeValue)
	        	return DebuggingDefaults.COLOR_TREE_TRUE_VALUE;	// set color if value is true
	        return DebuggingDefaults.COLOR_TREE_FALSE_VALUE;	// set color if value is false 	    
		}
		private Color getSelectionColor(){
			return DebuggingDefaults.COLOR_TREE_SELECTED_VALUE;
		}
		
		/**
		 * Returns the background selection Color for this node, set to 
		 * {@link it.emarolab.cagg.debugging.DebuggingDefaults#COLOR_TREE_SELECTED_VALUE}.
		 * @param newColor is not used
		 * @see javax.swing.tree.DefaultTreeCellRenderer#setBackgroundSelectionColor(java.awt.Color)
		 **/
		@Override
		public void setBackgroundSelectionColor(Color newColor) {
			super.setBackgroundSelectionColor( getSelectionColor());
		}

		/**
		 * Returns the foreground selection Color for this node set to: 
		 * {@link it.emarolab.cagg.debugging.DebuggingDefaults#COLOR_TREE_NULL_VALUE} 
		 * if its value is {@code null}.
		 * Or, {@link it.emarolab.cagg.debugging.DebuggingDefaults#COLOR_TREE_FALSE_VALUE} 
		 * if its value is {@code false}.
		 * Or, {@link it.emarolab.cagg.debugging.DebuggingDefaults#COLOR_TREE_TRUE_VALUE} 
		 * if its value is {@code true}.
		 * @param newColor is not used
		 * @see javax.swing.JComponent#setForeground(java.awt.Color)
		 **/
		@Override
		public void setForeground( Color newColor) {
			super.setForeground( getEvaluationColor());
		}
	}
}

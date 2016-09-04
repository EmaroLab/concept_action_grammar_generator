package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.language.SyntaxNode;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.MapsDebugginGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel}
 * to show the map stored in the data structure after grammars parsing and merging. To note that those are the main
 * data stored during serialisation and used for results evaluation.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 * @see it.emarolab.cagg.core.evaluation.semanticGrammar.Grammar
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.DeserialisationGui
 */
@SuppressWarnings("serial")
public class MapsDebugginGuiPanel extends BaseDebugginGuiPanel {

	// default constructors
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JPanel, String, Color, Color)}.
	 * @param parent the {@code JPanel} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public MapsDebugginGuiPanel(JPanel parent, String title, Color c1,	Color c2) {
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
	public MapsDebugginGuiPanel(JSplitPane parent, String title, Color c1, Color c2, Boolean isRight) {
		super(parent, title, c1, c2, isRight);
	}
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public MapsDebugginGuiPanel(JSplitPane parent, String title, Color c1,	Color c2) {
		super(parent, title, c1, c2);
	}

	// visualisation
	/**
	 * In this panel the layout of the {@code contentPanel} is set to {@code BoxLayout(contentPanel, BoxLayout.PAGE_AXIS)}.
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()
	 **/
	@Override
	public void setContents(){  // called in the constructor
		super.setContents();
		contentPanel.setLayout( new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
	}
	
	/**
	 * This methods update the contents of this panel by showing the grammar creator maps in this order: 
	 * {@link it.emarolab.cagg.core.evaluation.semanticGrammar.Grammar#getCompletePreambleMap()}, 
	 * {@link it.emarolab.cagg.core.evaluation.semanticGrammar.Grammar#getCompleteRuleMap()}, 
	 * {@link it.emarolab.cagg.core.evaluation.semanticGrammar.Grammar#getCompleteTermMap()},
	 * {@link it.emarolab.cagg.core.evaluation.semanticGrammar.Grammar#getCompleteTagMap()} and
	 * {@link it.emarolab.cagg.core.evaluation.semanticGrammar.Grammar#getComposedExpressionMap()}.
	 * @param merger the grammars data structure to be visualised .
	 */
	public void visualizeMaps( GrammarBase<?> merger){
		removeContents(); // clear this gui tab
		// create visualization to show grammar maps
		// preamble map
		this.addLabel( DebuggingDefaults.PANEL_PREAMBLE_MAP_SUBTITLE);
	    JTable preambletable =  visualiseMap( merger.getPreambleMap());
	    contentPanel.add( preambletable);
		this.addSeparator();
	    // rule map
		this.addLabel( DebuggingDefaults.PANEL_RULE_MAP_SUBTITLE);
		JTable ruleTable =  visualiseMap( merger.getBodyMap());
		contentPanel.add( ruleTable);
	/*	this.addSeparator();
		// term map
		this.addLabel( DebuggingDefaults.PANEL_TERM_MAP_SUBTITLE);
		JTable termTable =  visualiseMap( merger.getCompleteTermMap());
		contentPanel.add( termTable);
		this.addSeparator();
		// tag map
		this.addLabel( DebuggingDefaults.PANEL_TAG_MAP_SUBTITLE);
		JTable tagTable =  visualiseTagMap( merger.getCompleteTagMap());
		contentPanel.add( tagTable);
		//updateContents(); // update this gui tab 
		// rule node map
		this.addLabel( DebuggingDefaults.PANEL_RULENODE_MAP_SUBTITLE);
		JTable rulenodeTable =  visualiseRuleNodeMap( merger.getComposedExpressionMap());
		contentPanel.add( rulenodeTable);
		updateContents(); // update this gui tab
	*/
		UILog.info( "complete map tab refreshed in GUI.");
	}

	// visualise a generic node map
	private JTable visualiseMap(  Map<Long, ?> map){
		DefaultTableModel model = new DefaultTableModel( new Object[] { "Node Id", "Type", "Data" }, 0);
		model.addRow( new Object[] { "Id", "Node.Type", "Node.Data" }); // add map coloumn name
	    for ( Entry<Long, ?> entry : map.entrySet())
	        model.addRow( new Object[] { entry.getKey(), ((SyntaxNode<?,?>) entry.getValue()).getType(), ((SyntaxNode<?,?>) entry.getValue()).getData()});
	    return new JTable( model);
	}
	// visualise a tag map
/*	private JTable visualiseTagMap(Map<Long, List<String>> map) {
		DefaultTableModel model = new DefaultTableModel( new Object[] { "Node Id", "Tags"}, 0);
		Boolean addColumnName = true;
		for ( Entry<Long, ?> entry : map.entrySet()){
			if( addColumnName){
				model.addRow( new Object[] { "Id", "Tags" });
				addColumnName = false;
			}
	        model.addRow( new Object[] { entry.getKey(), entry.getValue()});
		}
		if( addColumnName)
			model.addRow( new Object[] { "Empty", "..." });
	    return new JTable( model);
	}

	private JTable visualiseRuleNodeMap(Map<Long, ExpressionNode<?>> map) {
		DefaultTableModel model = new DefaultTableModel( new Object[] { "Node Id", "Node"}, 0);
		Boolean addColumnName = true;
		for ( Entry<Long, ?> entry : map.entrySet()){
			if( addColumnName){
				model.addRow( new Object[] { "Id", "Node" });
				addColumnName = false;
			}
	        model.addRow( new Object[] { entry.getKey(), entry.getValue()});
		}
		if( addColumnName)
			model.addRow( new Object[] { "Empty", "..." });
	    return new JTable( model);
	}*/
}

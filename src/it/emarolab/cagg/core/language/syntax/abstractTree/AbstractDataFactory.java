package it.emarolab.cagg.core.language.syntax.abstractTree;

import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel;

import java.awt.Component;
import java.io.Serializable;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractDataFactory.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class contains useful implementation for the {@code data} related to each nodes (ASN) of the Abstract Syntax Tree (AST)
 * ({@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode}).
 * Particularly, this class gives easy cunstructors for objects to be instantiated and returned in
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode#assignData()}.
 * Moreover, all the classes that uses this factory are extension of the {@code AbstractSyntaxNode} 
 * and they should be collected in the package:
 * {@literal dotVocal.nuanceGoogleIntegration.semanticParser.parser.abstractSyntaxGenerator.nuanceSyntaxNode.syntaxNodeType}.<br>
 * 
 * More in details, this class implements classes as extensions of 
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData}
 * and {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData}.
 * Particularly, the node data managed by this factory are:
 * {@link ASNodeEmptyData}, {@link ASNodeStrData}, {@link ASNodeStrStrData} and {@link ASNodeStrTreeData}.<br> 
 * 
 * The purpose of the classes implemented in this file is to give a flexible interface for AST node modifications and extensions. 
 * In particular, each classes that are extending {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode}
 * must define the type of data by using those templates or by extending them for extra features.
 * Possible example of this mechanism is shown in class 
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.syntaxNodeType.RuleSyntaxNode};
 * which uses a custom node data class: the {@code ASNodeStrTreeTagData}.
 * </p>
 *
 * @see DataFactoryVisualizer
 * @see ASNodeEmptyData
 * @see ASNodeStrData
 * @see ASNodeStrStrData
 * @see ASNodeStrTreeData
 * @see it.emarolab.cagg.core.language.SyntaxNode
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode
 */
@SuppressWarnings("serial")
public class AbstractDataFactory implements Serializable{
		
	////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////  methods to instanciate and return the private classes below /////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * It creates (and returns) a new instance of the data factory visualiser 
	 * ({@link DataFactoryVisualizer})
	 * @return a new data factory visualiser.
	 */
	public DataFactoryVisualizer getNewVisualizer(){
		return new DataFactoryVisualizer();
	}
	
	
	/**
	 * It creates (and returns) a new AST node data by attaching no informations by calling:
	 * {@link AbstractDataFactory.ASNodeEmptyData#AbstractDataFactory.ASNodeEmptyData(String)},
	 * with {@code info} string as input parameter.
	 * @param genericInfo for display generic info in the GUI (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getGenericInfo()}.
	 * @return a new empty data for a AST node.
	 */
 	public ASNodeEmptyData getNewEmptyData( String genericInfo){
		return new ASNodeEmptyData( genericInfo);
	}
 	
 	
	/**
	 * It creates (and returns) a new AST node data by attaching a string object (to be set) to it.
	 * This is done by calling {@link AbstractDataFactory.ASNodeStrData#AbstractDataFactory.ASNodeStrData(String, String)},
	 * with {@code genericInfo} and {@code info} strings as input parameters respectively.
	 * @param genericInfo for display generic info in the GUI (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getGenericInfo()}.
	 * @param info for display information about the string data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getInfo()}.
	 * @return an abstract syntax node data which contains a string (to be set).
	 */
	public ASNodeStrData getNewStringData( String genericInfo, String info){
		return new ASNodeStrData( genericInfo, info);
	}
	
	/**
	 * It creates (and returns) a new AST node data by attaching a string object to it.
	 * This is done by calling {@link AbstractDataFactory.ASNodeStrData#AbstractDataFactory.ASNodeStrData(String, String, String)}, 
	 * with {@code instance}, {@code genericInfo} and {@code info} strings as input parameters respectively.
	 * @param instance the actual value to assign to the data.
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getInstance()}.
	 * @param genericInfo for display generic info in the GUI (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getGenericInfo()}.
	 * @param info for display information about the string data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getInfo()}.
	 * @return an AST node data which contains a string (initialised using the input parameter: {@code instance}).
	 */
	public ASNodeStrData getNewStringData( String instance, String genericInfo, String info){
		return new ASNodeStrData( instance, genericInfo, info);
	}
	
	
	/**
	 * It creates (and returns) a new AST node data by attaching two strings objects to it (both to be set).
	 * This is done by calling {@link AbstractDataFactory.ASNodeStrStrData#AbstractDataFactory.ASNodeStrStrData(String, String, String)},
	 * with {@code genericInfo}, {@code info1} and {@code info2} strings as input parameters respectively.
	 * @param genericInfo for display generic info in the GUI (set to {@code null} for default value).
	 * @param info1 for display information about the first string ({@code instance1}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo1()} 
	 * @param info2 for display information about the second string ({@code instance2}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo2()}.
	 * @return an AST node data which contains two Strings (both to be set).
	 */
	public ASNodeStrStrData getNewDoubleStringData( String genericInfo, String info1, String info2){
		return new ASNodeStrStrData( genericInfo, info1, info2);
	}
	
	/**
	 * It creates (and returns) a new AST node data by attaching two strings objects to it.
	 * This is done by calling {@link AbstractDataFactory.ASNodeStrStrData#AbstractDataFactory.ASNodeStrStrData(String, String, String, String, String)},
	 * with {@code instance1}, {@code instance2}, {@code genericInfo}, {@code info1} and {@code info2}  
	 * strings as input parameters respectively.
	 * @param instance1 the actual value to assign to the first data.
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance()}
	 * @param instance2 the actual value to assign to the second data.
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance2()}
	 * @param genericInfo for display generic info in the GUI (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getGenericInfo()}.
	 * @param info1 for display information about the first string ({@code instance1}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo1()} 
	 * @param info2 for display information about the second string ({@code instance2}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo2()}.
	 * @return an AST node data which contains two Strings (set with the input parameters {@code instance1} and {@code instance2}).
	 */
	public ASNodeStrStrData getNewDoubleStringData( String instance1, String instance2, String genericInfo, String info1, String info2){
		return new ASNodeStrStrData( instance1, instance2, genericInfo, info1, info2);
	}
	
	
	/**
	 * It creates (and returns) a new AST node data by attaching a String and an 
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode}
	 * objects (to be set). This is done by calling: {@link AbstractDataFactory.ASNodeStrTreeData#AbstractDataFactory.ASNodeStrTreeData(String, String, String)},
	 * with {@code genericInfo}, {@code info1} and {@code info2} strings as input parameters respectively.
	 * @param genericInfo for display generic info in the GUI (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getGenericInfo()}.
	 * @param info1 for display information about the string ({@code instance1}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo1()} 
	 * @param info2 for display information about the Expression Tree (ET) ({@code instance2}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo2()}.
	 * @return an AST node data which contains a String and an Expression Tree (to be set).
	 */
	public ASNodeStrTreeData getNewStringTreeData( String genericInfo, String info1, String info2){
		return new ASNodeStrTreeData( genericInfo, info1, info2); 
	}
	
	/**
	 * It creates (and returns) a new AST node data by attaching a String and an 
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode}
	 * objects. This is done by calling: 
	 * {@link AbstractDataFactory.ASNodeStrTreeData#AbstractDataFactory.ASNodeStrTreeData(String, ExpressionNode, String, String, String)},
	 * with {@code instance1}, {@code instance2}, {@code genericInfo}, 
	 * {@code info1} and {@code info2} as input parameters respectively.
	 * @param instance1 the actual string value to assign to the first data.
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance()}
	 * @param instance2 the actual ET to assign to the second data.
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance2()}
	 * @param genericInfo for display generic info in the GUI (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getGenericInfo()}
	 * @param info1 for display information about the string ({@code instance1}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo1()} 
	 * @param info2 for display information about the Expression Tree (ET) ({@code instance2}) data (set to {@code null} for default value).
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo2()}.
	 * @return an AST node data which contains a String and an Expression Tree (initialised with the input parameters 
	 * {@code instance1}and {@code instance2}).
	 */
	public ASNodeStrTreeData getNewStringTreeData( String instance1, ExpressionNode<?> instance2, String genericInfo, String info1, String info2){
		return new ASNodeStrTreeData( instance1, instance2, genericInfo, info1, info2); 
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////  Classes to implement specific AST node data  ///////////////////////// 
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class extends {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData}
	 * with {@code D} parameter equals to {@code <Void>}.<br>
	 * It purpose is to describe an empty data for a node in the AST by assuring stable system behavior, 
	 * easy debugging and visualisation on the GUI.
	 * </p>
	 *
	 * @see AbstractDataFactory.DataFactoryVisualizer
	 * @see AbstractDataFactory
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
	 */
	public class ASNodeEmptyData extends AbstractNodeData< Void>{	// empty node data class interface (can be visualised in GUI)
		/**
		 * Default {@code genericInfo} for this class. By default set to:
		 * {@literal "This expression tree node does not contains any data elements"}. 
		 */
		public static final String GENERIC_INFO = "This expression tree node does not contains any data elements";
		
		/**
		 * Create a new empty node data by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#AbstractNodeData(String)}
		 * with {@link #GENERIC_INFO} as input parameter.
		 */
		public ASNodeEmptyData(){
			super( GENERIC_INFO);
		}
		
		/**
		 * Create a new empty node data by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#AbstractNodeData(String)}
		 * with {@code info} as input parameter.
		 * @param info the string defining the {@code generiInfo} of this data.
		 */
		public ASNodeEmptyData( String info){
			super( info);
		}
		
		/**
		 * This method does not do nothing since in an empty
		 * data nothing should be shown in the GUI.
		 * @param contentPanel not used.
		 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#addDataToGui(javax.swing.JPanel)
		 **/
		public void addDataToGui( JPanel contentPanel) {
			// does not show nothing (else except for the info string)
		}
	} 
	
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class extends {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData}
	 * with {@code D} parameter equals to {@code <String>}.<br>
	 * It purpose is to describe a data for a node in the AST that contains a String. By assuring stable system behavior, 
	 * easy debugging and visualisation on the GUI.
	 * </p>
	 *
	 * @see AbstractDataFactory
	 * @see AbstractDataFactory.DataFactoryVisualizer
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
	 */
	public class ASNodeStrData extends AbstractNodeData< String>{
		
		/**
		 * Create a new AST node data contains a initialised string by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#AbstractNodeData(Object, String, String)};
		 * with {@code instance}, {@code genericInfo} and {@code info1} parameters respectively.
		 * @param instance the actual value of the data string.
		 * @param genericInfo the generic node data info for debugging.
		 * @param info1 the debugging info relate to the string.
		 */
		public ASNodeStrData(String instance, String genericInfo, String info1) {
			super(instance, genericInfo, info1);
		}

		/**
		 * Create a new AST node data contains a not initialised string by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#AbstractNodeData(String, String)};
		 * with {@code genericInfo} and {@code info1} parameters respectively.
		 * @param genericInfo the generic node data info for debugging.
		 * @param info1 the debugging info relate to the string.
		 */
		public ASNodeStrData(String genericInfo, String info1) {
			super( genericInfo, info1);
		}
		
		/**
		 * Create a new AST node data contains a not initialised string by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#AbstractNodeData(String)};
		 * with {@code info1} parameters respectively.
		 * @param genericInfo the generic node data info for debugging.
		 */
		public ASNodeStrData(String genericInfo) {
			super(genericInfo);
		}
		
		/**
		 * Create a new AST node data contains a not initialised string by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#AbstractNodeData()}.
		 */
		public ASNodeStrData() {
			super();
		}

		/**
		 * Visualise this data in the GUi by adding a {@link javax.swing.JSeparator}
		 * and a {@link javax.swing.JLabel} containing:
		 * {@code info + " " + instance}. Where, {@code info} is given from:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getInfo()}
		 * while, {@code instance} from:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getInstance()}.
		 * @param contentPanel the GUI panel in which show this information.
		 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#addDataToGui(javax.swing.JPanel)
		 **/
		@Override
		public void addDataToGui(JPanel contentPanel){
			this.getVisualizer().addSeparator( contentPanel);
			this.getVisualizer().addLabel( this.getInfo() + " " + this.getInstance(), contentPanel); 
		}
	}
	
	
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class extends {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData}
	 * with {@code I1} and {@code I2} both parameter equals to {@code <String>}.<br>
	 * It purpose is to describe a data for a node in the AST that contains two Strings. By assuring stable system behavior, 
	 * easy debugging and visualisation on the GUI.
	 * </p>
	 *
	 * @see AbstractDataFactory.DataFactoryVisualizer
	 * @see AbstractDataFactory
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
	 */
	public class ASNodeStrStrData extends AbstractNodeDoubleData< String, String>{	// str+str node data class interface (can be visualised in GUI)

		/**
		 * Create a new AST node data contains two not initialised strings by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#AbstractNodeDoubleData()}.
		 */
		public ASNodeStrStrData() {
			super();
		}
			
		/**
		 * Create a new AST node data contains two initialised strings by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#AbstractNodeDoubleData(Object, Object, String, String, String)};
		 * with {@code instance1}, {@code instance2}, {@code genericInfo}, {@code info1}, {@code info2} parameters respectively.
		 * @param instance1 the actual value of the first data string.
		 * @param instance2 the actual value of the second data string.
		 * @param genericInfo the generic node data info for debugging.
		 * @param info1 the debugging info relate to the first string.
		 * @param info2 the debugging info relate to the second string.
		 */
		public ASNodeStrStrData(String instance1, String instance2, String genericInfo, String info1, String info2) {
			super(instance1, instance2, genericInfo, info1, info2);
		}

		/**
		 * Create a new AST node data contains two not initialised strings by calling:;
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#AbstractNodeDoubleData(String, String, String)}
		 * with {@code genericInfo}, {@code info1}, {@code info2} parameters respectively.
		 * @param genericInfo the generic node data info for debugging.
		 * @param info1 the debugging info relate to the first string.
		 * @param info2 the debugging info relate to the second string.
		 */
		public ASNodeStrStrData(String genericInfo, String info1, String info2) {
			super(genericInfo, info1, info2);
		}

		/**
		 * Visualise the two string divided with {@link javax.swing.JSeparator}.
		 * particularly, the first string is shown in a {@link javax.swing.JLabel} as:
		 * {@code info + " " + instance}; where {@code info} is given by:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo1()};
		 * while, {@code instance} is given by:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance()}.<br>
		 * On the other hand, the second string is similarly shown by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo2()}
		 * for {@code info}, and 
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance2()}
		 * for {@code instance}
		 * @param contentPanel the GUI panel in which show this information.
		 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#addDataToGui(javax.swing.JPanel)
		 **/
		@Override
		public void addDataToGui(JPanel contentPanel) {
			this.getVisualizer().addSeparator( contentPanel);
			this.getVisualizer().addLabel( this.getInfo1() + " " + this.getInstance(), contentPanel);
			this.getVisualizer().addSeparator( contentPanel);
			this.getVisualizer().addLabel( this.getInfo2() + " " + this.getInstance2(), contentPanel);
		}	
	}
	
	
	
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class extends {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData}
	 * with {@code I1} parameter equals to {@code <String>} and {@code I2 = <ExpressionNode<?>>}.<br>
	 * It purpose is to describe a data for a node in the AST that contains a Strings and an Expression Tree (ET). 
	 * By assuring stable system behavior, easy debugging and visualisation on the GUI.
	 * </p>
	 *
	 * @see AbstractDataFactory.DataFactoryVisualizer
	 * @see AbstractDataFactory
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode
	 */
	public class ASNodeStrTreeData extends AbstractNodeDoubleData< String, ExpressionNode<?>>{
		
		/**
		 * Create a new AST node data contains a String and an ET (not initialised), by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#AbstractNodeDoubleData()}.
		 */
		public ASNodeStrTreeData() {
			super();
		}

		/**
		 * Create a new AST node data contains a String and an ET (initialised), by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#AbstractNodeDoubleData(Object, Object, String, String, String)};
		 * with {@code instance1}, {@code instance2}, {@code genericInfo}, {@code info1}, {@code info2} parameters respectively.
		 * @param instance1 the actual value of the first data (String).
		 * @param instance2 the actual value of the second data (ET).
		 * @param genericInfo the generic node data info for debugging.
		 * @param info1 the debugging info relate to the first data string.
		 * @param info2 the debugging info relate to the second data (ET).
		 */
		public ASNodeStrTreeData(String instance1, ExpressionNode<?> instance2, String genericInfo, String info1, String info2) {
			super(instance1, instance2, genericInfo, info1, info2);
		}

		/**
		 * Create a new AST node data contains a String and an ET (not initialised), by calling:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#AbstractNodeDoubleData(String, String, String)};
		 * with {@code genericInfo}, {@code info1}, {@code info2} parameters respectively.
		 * @param genericInfo the generic node data info for debugging.
		 * @param info1 the debugging info relate to the first data string.
		 * @param info2 the debugging info relate to the second data (ET).
		 */
		public ASNodeStrTreeData(String genericInfo, String info1, String info2) {
			super(genericInfo, info1, info2);
		}

		/**
		 * Visualise the two data divided with {@link javax.swing.JSeparator}.
		 * particularly, the first string is shown in a {@link javax.swing.JLabel} as:
		 * {@code info + " " + instance}; where {@code info} is given by:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo1()};
		 * while, {@code instance} is given by:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance()}.<br>
		 * On the other hand, the ET is shown by adding a {@link javax.swing.JLabel} containing:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo2()};
		 * and a {@link javax.swing.JTree} given from:
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance2()}.
		 * @param contentPanel the GUI panel in which show this information.
		 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#addDataToGui(javax.swing.JPanel)
		 **/
		@Override
		public void addDataToGui(JPanel contentPanel) {
			this.getVisualizer().addSeparator( contentPanel);
			this.getVisualizer().addLabel( this.getInfo1() + " " + this.getInstance(), contentPanel);
			this.getVisualizer().addSeparator( contentPanel);
			this.getVisualizer().addLabel( this.getInfo2(), contentPanel);
			this.getVisualizer().addTree( this.getInstance2(), contentPanel);
		}			
	}


	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////  Class used for easy content visualisation on GUI  /////////////////////// 
	////////////////////////////////////////////////////////////////////////////////////////////////
	
		
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class is used to easy visualise node data on GUI.<br>
	 * Particularly, it is used in: {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData},
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData} 
	 * and their extending classes.<br>
	 * Finally, to get a new instance of this object you should use:
	 * {@link AbstractDataFactory#getNewVisualizer()}
	 * </p>
	 *
	 * @see AbstractDataFactory
	 * @see AbstractDataFactory.ASNodeStrData
	 * @see AbstractDataFactory.ASNodeStrStrData
	 * @see AbstractDataFactory.ASNodeStrTreeData
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData
	 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData
	 */
	public class DataFactoryVisualizer implements Serializable{
		/**
		 * Empty constructor, it create this object but it does not do nothing more.
		 */
		public DataFactoryVisualizer(){											// empty construcor
		}										
		
		/**
		 * Add a separator to the panel (given as input parameter) and return it.
		 * @param toAddPanel the panel where components should be added.
		 * @return the component added to the given panel
		 */
		public JSeparator addSeparator( JPanel toAddPanel){						// for adding easily debuggingGuiComponents (separator)
			JSeparator separator = new JSeparator();
			toAddPanel.add(separator);
			return separator;
		}
		
		/**
		 * Add a label with a text (given as input parameter) to the panel 
		 * (given as input parameter as well) and return it.
		 * @param toAddPanel the panel where components should be added.
		 * @param txt the text to be shown in the label.
		 * @return the label with the related text added to the given panel.
		 */
		public JLabel addLabel( String txt, JPanel toAddPanel){					// for adding easily debuggingGuiComponents (label) 
			JLabel label = new JLabel( txt);
			label.setAlignmentX( JLabel.LEFT);
			addWithAuxiliaryPanel( label, toAddPanel);
			return label;
		}
		
		/**
		 * Add a tree (given as input parameter) to the panel 
		 * (given as input parameter as well) and return it.
		 * @param root the root node of the tree to be visualised.
		 * @param toAddPanel the panel where components should be added.
		 * @return the tree added to the panel.
		 */
		public JTree addTree( ExpressionNode<?> root, JPanel toAddPanel){	// for adding easily debuggingGuiComponents (tree)
			JTree tree = new JTree( root);
			SubSplitDebuggingGuiPanel.expandAllNodes(tree);
			addWithAuxiliaryPanel( tree, toAddPanel);
			return tree;
		}
		
		/**
		 * Add a not editable TextArea, with a text (given as input parameter),
		 * to the panel (given as input parameter as well) and return it.
		 * @param contents the string to be shown in the text area.
		 * @param toAddPanel the panel where components should be added.
		 * @return the text area initialised and added to the panel.
		 */
		public JTextArea addTextArea( String contents, JPanel toAddPanel){		// for adding easily debuggingGuiComponents (text area)
			JTextArea text = new JTextArea();
			text.setBackground( DebuggingDefaults.COLOR_TEST_FIELD);
			text.setEditable(false);
			text.setLineWrap( true);
			text.setWrapStyleWord(true);
			text.setText( contents);
			toAddPanel.add( text);
			return text;
		}
		
		/**
		 * Add a map (given as input parameter) as a table to the panel 
		 * (given as input parameter as well) and return it.
		 * @param map the map to be shown as a table.
		 * @param toAddPanel the panel where the table should be added.
		 * @return the table added to the given panel.
		 */
		public JTable addMap( Map<?,?> map, JPanel toAddPanel) {
		    DefaultTableModel model = new DefaultTableModel( new Object[] { "Node Id", "Tags" }, 0);
		    model.addRow( new Object[] { "Id", "Tags" }); // add map coloumn name
		    for ( Map.Entry<?,?> entry : map.entrySet())
		        model.addRow(new Object[] { entry.getKey(), entry.getValue() });
		    JTable table = new JTable( model);
		    addWithAuxiliaryPanel( table, toAddPanel);
		    return table;
		}
		
		/**
		 * This method is called from the GUI in order to visualise the {@code parent} data object.
		 * It calls {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#addDataToGui(JPanel)}
		 * in order to add:
		 * the data visualisations into the given panel  
		 * ({@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getInstance()})
		 * and the data information ({@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getInfo()}).<br>
		 * Than, it automatically shows the {@code genericInfo} of the {@code parent} by calling: {@link #showContents(AbstractNodeData, JPanel)}
		 * @param parent the node data objects that from which retrieve the instances and this object.
		 * @param contentPanel the panel where components should be added.
		 */
		public void showContents( AbstractNodeData<?> parent, JPanel contentPanel){
			parent.addDataToGui( contentPanel);
			// display info
			showInfo( parent, contentPanel);
		}
		
		/**
		 * This method is called from the GUI in order to visualise the {@code parent} data object.
		 * It calls {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#addDataToGui(JPanel)}
		 * in order to add:
		 * the data visualisations into the given panel  
		 * ({@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance()}
		 * and {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInstance2()})
		 * and the data information ({@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo1()}
		 * and {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getInfo2()}).<br>
		 * Than, it automatically shows the {@code genericInfo} of the {@code parent} by calling: {@link #showContents(AbstractNodeDoubleData, JPanel)}
		 * @param parent the node data objects that from which retrieve the instances and this object.
		 * @param contentPanel the panel where components should be added.
		 */
		public void showContents( AbstractNodeDoubleData<?,?> parent, JPanel contentPanel){
			parent.addDataToGui( contentPanel);
			showInfo( parent, contentPanel);
		}
	
		/**
		 * Add {@link javax.swing.JSeparator} and call {@link #addTextArea(String, JPanel)}
		 * to display the {@code genericInfo} by calling: 
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData#getGenericInfo()}.
		 * The purpose of this method is to have a common way to shown {@code genericInfo} for all the
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData}.
		 * @param parent the node data objects that contains this {@code genericInfo}.
		 * @param contentPanel the panel where components should be added.
		 */
		public void showInfo( AbstractNodeDoubleData<?,?> parent, JPanel contentPanel) {
			this.addSeparator( contentPanel);
			this.addSeparator( contentPanel);
			this.addTextArea( "[INFO]" + DebuggingDefaults.SYS_LINE_SEPARATOR + parent.getGenericInfo(), contentPanel);
		}
		
		/**
		 * Add {@link javax.swing.JSeparator} and call {@link #addTextArea(String, JPanel)}
		 * to display the {@code genericInfo} by calling: 
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData#getGenericInfo()}.
		 * The purpose of this method is to have a common way to shown {@code genericInfo} for all the
		 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData}.
		 * @param parent the node data objects that contains this {@code genericInfo}.
		 * @param contentPanel the panel where components should be added.
		 */
		public void showInfo( AbstractNodeData<?> parent, JPanel contentPanel) {
			this.addSeparator( contentPanel);
			this.addSeparator( contentPanel);
			this.addTextArea( "[INFO]" + DebuggingDefaults.SYS_LINE_SEPARATOR + parent.getGenericInfo(), contentPanel);
		}
		
		private void addWithAuxiliaryPanel( Component comp, JPanel toAddPanel){
			JPanel auxiliaryPanel = new JPanel();
			auxiliaryPanel.add( comp);
			toAddPanel.add( auxiliaryPanel);
		}
	}

}




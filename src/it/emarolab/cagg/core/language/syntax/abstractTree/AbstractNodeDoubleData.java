package it.emarolab.cagg.core.language.syntax.abstractTree;

import it.emarolab.cagg.core.language.BaseDoubleData;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.DataFactoryVisualizer;

import javax.swing.JPanel;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractNodeDoubleData.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.core.language.BaseDoubleData}
 * and inherit from it the {@code I1} and {@code I2} parameters.<br>
 * The purpose of this class is to implement the standard features of an object containing two node data for an Abstract Syntax Tree (AST)
 * node; implemented in: {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode}.
 * Where, those main feature are focused on debugging and GUI visualisation.
 * </p>
 *
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 * @see it.emarolab.cagg.core.language.BaseDoubleData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.DataFactoryVisualizer
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrTreeData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrStrData
 *
 * @param <I1> the first ({@code instance1}) type of this node data. Inherit from the {@code I1} parameter of 
 * {@link it.emarolab.cagg.core.language.BaseDoubleData}
 * @param <I2> the second ({@code instance2}) type of this node data. Inherit from the {@code I2} parameter of 
 * {@link it.emarolab.cagg.core.language.BaseDoubleData}
 */
@SuppressWarnings("serial")
public abstract class AbstractNodeDoubleData< I1, I2> extends BaseDoubleData< I1, I2>{
	/////////////////////////////////////// constants
	/**
	 * The default node description shown in the GUI. Set to:
	 * {@literal "No generic information given! "}.
	 */
	public static final String GENERIC_INFO = "No generic information given! ";
	/**
	 * The default description of the first node data ({@code instance1}) with respect to AST node shown in the GUI. Set to:
	 * {@literal "No data instance 1 information given! "}.
	 */
	public static final String INFO1 = "No data instance 1 information given! ";
	/**
	 * The default description of the second node data ({@code instance2}) with respect to AST node shown in the GUI. Set to:
	 * {@literal "No data instance 2 information given! "}.
	 */
	public static final String INFO2 = "No data instance 2 information given! ";
	
	/////////////////////////////////////// class fields
	private DataFactoryVisualizer visualizer; // an object that implements methods to easy add GUI components in panels
	/**
	 * The custom node description shown in the GUI. Settable on constructor. 
	 * If this field is {@code null} the {@link #GENERIC_INFO} will be used.
	 */
	protected String genericInfo; 
	/**
	 * The default description of the first node data {@code instance1} with respect to AST node shown in the GUI. 
	 * Settable on constructor.
	 * If this field is {@code null} the {@link #INFO1} will be used.
	 */
	protected String info1;
	/**
	 * The default description of the second node data {@code instance2} with respect to AST node shown in the GUI. 
	 * Settable on constructor.
	 * If this field is {@code null} the {@link #INFO2} will be used.
	 */
	protected String info2;
	
	/////////////////////////////////////// class constructor
	/**
	 * Initialise this data by calling:
	 * {@link it.emarolab.cagg.core.language.BaseDoubleData#BaseDoubleData()}.
	 * Moreover, it sets {@link #genericInfo} to the default {@link #GENERIC_INFO},
	 * {@link #info1} to the default {@link #INFO1} and {@link #info2} to the default {@link #INFO2}.<br>
	 * Note that this constructor does not initialise the node data {@code I1} and {@code I2} values themselves.
	 */
	public AbstractNodeDoubleData() {
		super();
		initialise( GENERIC_INFO, INFO1, INFO2);
	}
	/**
	 * Initialise this data by calling:
	 * {@link it.emarolab.cagg.core.language.BaseDoubleData#BaseDoubleData()}
	 * and sets custom {@link #genericInfo}, {@link #info1} and {@link #info2} with respect to input parameters.<br>
	 * Note that this constructor does not initialise the node data {@code I1} and {@code I2} values themselves.
	 * @param genericInfo the description of this ASN node to be shown in the GUI.
	 * @param info1 the description of the first node data ({@code instance1}) within a certain ASN node context.
	 * @param info2 the description of the second node data ({@code instance2}) within a certain ASN node context.
	 */
	public AbstractNodeDoubleData( String genericInfo, String info1, String info2) {
		super();
		initialise( genericInfo, info1, info2);
	}
	/**
	 * Initialise this data by calling:
	 * {@link it.emarolab.cagg.core.language.BaseDoubleData#BaseDoubleData(Object, Object)}
	 * and sets custom {@link #genericInfo}, {@link #info1} and {@link #info2} with respect to input parameters.<br>
	 * Note that this constructor is the only that initialise the node data {@code I1} and {@code I2} values.
	 * @param instance1 the actual data value to be set to the first instance of this object.
	 * @param instance2 the actual data value to be set to the second instance of this object.
	 * @param genericInfo the description of this ASN node to be shown in the GUI.
	 * @param info1 the description of the first node data ({@code instance1}) within a certain ASN node context.
	 * @param info2 the description of the second node data ({@code instance2}) within a certain ASN node context.
	 */
	public AbstractNodeDoubleData(I1 instance1, I2 instance2, String genericInfo, String info1, String info2) {
		super(instance1, instance2);
		initialise( genericInfo, info1, info2);
	}
	// initialising methods call from all constructors
	protected void initialise( String genericInfo, String info1, String info2){
		visualizer = new AbstractDataFactory().getNewVisualizer();
		if( info1 != null)
			this.info1 = info1;
		else this.info1 = INFO1;
		if( info2 != null)
			this.info2 = info2;
		else this.info2 = INFO2;
		if( genericInfo != null)
			this.genericInfo = genericInfo;
		else this.genericInfo = GENERIC_INFO;
	}
	
	///////////////////////////////////// abstract methods
	/**
	 * This method is used to format the data into the GUI.
	 * It is invoked when the GUI refreshes the node data visualisation by calling:
	 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.DataFactoryVisualizer#showContents(AbstractNodeData, JPanel)}
	 * or {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.DataFactoryVisualizer#showContents(AbstractNodeDoubleData, JPanel)}.<br>
	 * This method should use {@link #getVisualizer()} in order to add to the {@code contentPanel}
	 * the visualisation of the data (through {@link #getInstance()} and {@link #getInstance2()}) 
	 * and the information ({@link #getInfo1()} and {@link #getInfo2()}).
	 * The implementation automatically care about visualising {@link #genericInfo}.
	 * @param contentPanel the GUI panel in which the data and informations should be added.
	 */
	abstract public void addDataToGui( JPanel contentPanel);			// add contents to the gui
	
	///////////////////////////////////// getters methods
	/**
	 * @return the {@link #info1}
	 */
	protected String getInfo1(){		 								// explains how this comand is used
		return info1;
	}
	/**
	 * @return the {@link #info2}
	 */
	protected String getInfo2(){		 								// explains how this comand is used
		return info2;
	}
	
	/**
	 * @return the {@link #genericInfo}
	 */
	protected String getGenericInfo(){		 							// explains how this comand is used
		return genericInfo;
	}
	
	/**
	 * @return the object used for easy node data visualisation on GUI.
	 */
	public DataFactoryVisualizer getVisualizer(){
		return visualizer;
	}
	
	/**
	 * Particularly, it returns {@code "(nodeData:" + dataStr1 + ";" + dataStr2 + ")"}. Where, {@code dataStr1}
	 * is {@literal "empty"} if {@link #getInstance()} is {@code null}; or contains the string 
	 * description of the {@code instance} otherwise. The same occurs for {@code dataStr2} where 
	 * {@link #getInstance2()} is used.
	 * @return description of this node data as a String.
	 * @see it.emarolab.cagg.core.language.BaseData#toString()
	 **/
	@Override
	public String toString() {
		String in1, in2;
		if( this.getInstance() == null)
			in1 = "empty";
		else in1 = this.getInstance().toString();
		if( this.getInstance2() == null)
			in2 = "empty";
		else in2 = this.getInstance2().toString();
		return "(nodeData:" + in1 + ";" + in2 + ")";
	}
}
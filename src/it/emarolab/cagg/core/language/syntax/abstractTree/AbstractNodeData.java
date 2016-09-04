package it.emarolab.cagg.core.language.syntax.abstractTree;

import it.emarolab.cagg.core.language.BaseData;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.DataFactoryVisualizer;

import javax.swing.JPanel;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.grammar.abstractSyntaxTree.AbstractNodeData.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.core.language.BaseData}
 * and inherit from it the {@code D} parameter.<br>
 * The purpose of this class is to implement the standard features of an object containing a data for an Abstract Syntax Tree (AST)
 * node; implemented in: {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode}.
 * Where, those main feature are focused on debugging and GUI visualisation.
 * </p>
 *
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 * @see it.emarolab.cagg.core.language.BaseData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.DataFactoryVisualizer
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeEmptyData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory.ASNodeStrData
 * 
 * @param <D> the main object type of this node data. Inherit from the {@code D} parameter of 
 * {@link it.emarolab.cagg.core.language.BaseData}   
 */
@SuppressWarnings("serial")
public abstract class AbstractNodeData<D> extends BaseData< D>{	// main node data class interface
	/////////////////////////////////////// constants
	/**
	 * The default node description shown in the GUI. Set to:
	 * {@literal "No generic information given! "}.
	 */
	public static final String GENERIC_INFO = "No generic information given! ";
	/**
	 * The default description of this node data with respect to AST node shown in the GUI. Set to:
	 * {@literal "No data instance information given! "}.
	 */
	public static final String INFO = "No data instance information given! ";
	
	/////////////////////////////////////// class fields
	private DataFactoryVisualizer visualizer; // an object that implements methods to easy add GUI components in panels
	/**
	 * The custom node description shown in the GUI. Settable on constructor. 
	 * If this field is {@code null} the {@link #GENERIC_INFO} will be used.
	 */
	protected String genericInfo;
	/**
	 * The default description of this data with respect to AST node shown in the GUI. Settable on constructor.
	 * If this field is {@code null} the {@link #INFO} will be used.
	 */
	protected String info; 
		
	/////////////////////////////////////// class constructor
	/**
	 * Initialise this data by calling:
	 * {@link it.emarolab.cagg.core.language.BaseData#BaseData()}.
	 * Moreover, it sets {@link #genericInfo} to the default {@link #GENERIC_INFO} and
	 * {@link #info} to the default {@link #INFO}.<br>
	 * Note that this constructor does not initialise the data {@code D} value itself.
	 */
	public AbstractNodeData(){
		super();
		initialise( GENERIC_INFO, INFO);
	}
	/**
	 * Initialise this data by calling:
	 * {@link it.emarolab.cagg.core.language.BaseData#BaseData()}
	 * and sets {@link #info} to the default {@link #INFO}.<br>
	 * Note that this constructor does not initialise the data {@code D} value itself.
	 * @param genericInfo the description of this ASN node to be shown in the GUI.
	 */
	public AbstractNodeData( String genericInfo) {
		super();
		initialise( genericInfo, INFO);
	}
	/**
	 * Initialise this data by calling:
	 * {@link it.emarolab.cagg.core.language.BaseData#BaseData()}
	 * and sets custom {@link #genericInfo} and {@link #info} with respect to input parameters.<br>
	 * Note that this constructor does not initialise the data {@code D} value itself.
	 * @param genericInfo the description of this ASN node to be shown in the GUI.
	 * @param info the description of this data instance within a certain ASN node context.
	 */
	public AbstractNodeData( String genericInfo, String info) {
		super();
		initialise( genericInfo, info);
	}
	/**
	 * Initialise this data by calling:
	 * {@link it.emarolab.cagg.core.language.BaseData#BaseData(Object)}
	 * and sets custom {@link #genericInfo} and {@link #info} with respect to input parameters.<br>
	 * Note that this constructor is the only that initialise the data {@code D} value.
	 * @param instance the actual data value to be set to this object.
	 * @param genericInfo the description of this ASN node to be shown in the GUI.
	 * @param info the description of this data instance within a certain ASN node context.
	 */
	public AbstractNodeData(D instance, String genericInfo, String info) {
		super(instance);
		initialise( genericInfo, info);
	}
	// initialising methods call from all constructors
	protected void initialise( String genericInfo, String info){
		visualizer = new AbstractDataFactory().getNewVisualizer();
		if( info != null)
			this.info = info;
		else this.info = INFO;
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
	 * the visualisation of the data (through {@link #getInstance()}) and the information ({@link #getInfo()}).
	 * The implementation automatically care about visualising {@link #genericInfo}.
	 * @param contentPanel the GUI panel in which the data and informations should be added.
	 */
	abstract public void addDataToGui( JPanel contentPanel);			// add contents to the gui
	
	///////////////////////////////////// getters methods
	/**
	 * @return the {@link #genericInfo}.
	 */
	public String getGenericInfo(){
		return this.genericInfo;
	}
	
	/**
	 * @return the {@link #info}.
	 */
	public String getInfo(){
		return this.info;
	}
	
	/**
	 * @return the object used for easy node data visualisation on GUI.
	 */
	public DataFactoryVisualizer getVisualizer(){
		return visualizer;
	}
	
	/**
	 * Particularly, it returns {@code "(nodeData:" + dataStr + ")"}. Where, {@code dataStr}
	 * is {@literal "empty"} if {@link #getInstance()} is {@code null}; or contains the string 
	 * description of the {@code instance} otherwise.
	 * @return description of this node data as a String.
	 * @see it.emarolab.cagg.core.language.BaseData#toString()
	 **/
	@Override
	public String toString() {
		String in1;
		if( this.getInstance() == null)
			in1 = "empty";
		else in1 = this.getInstance().toString();
		return "(nodeData:" + in1 + ")";
	};
}

package it.emarolab.cagg.core.language;
 
/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.BaseDoubleData.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class implements the basic interface for some of the data given to a tree nodes.
 * It contains, as a node data, only two objects (of type {@code I1} and {@code I2} respectively)
 * that can be initialised in constructor {@link #BaseDoubleData(Object, Object)}, or 
 * through the methods setInstance() and {@link #setInstances(Object, Object)} 
 * (or even separately using {@link #setInstance(Object)} (for {@code I1}) and {@link #setInstance2(Object)} (for {@code I2})).
 * Finally, the data can be retrieved by using the methods: {@link #getInstance()} (for {@code I1}) 
 * and {@link #getInstance2()} (for {@code I2}).
 * </p>
 *
 * @see it.emarolab.cagg.core.language.SyntaxNode
 * @see it.emarolab.cagg.core.language.BaseDoubleData
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionDataFactory
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeDoubleData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractDataFactory
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractNodeData
 * 
 * @param <I1> the first actual data {@code instance} type.
 * @param <I2> the second actual data {@code instance2} type.
 */
@SuppressWarnings("serial")
public abstract class BaseDoubleData< I1, I2> extends BaseData< I1> {
	
	// ####################################################  MAIN FIELDS
	private I2 instance2;

	// ####################################################  CONSTRUCTORS
	/**
	 * Instantiate and completely initialise this data with both the actual values.
	 * @param instance1 the first value to be assigned to the data of this node
	 * @param instance2 the second value to be assigned to the data of this node
	 */
	public BaseDoubleData(I1 instance1, I2 instance2) {
		super( instance1);
		this.instance2 = instance2;
	}
	/**
	 * Create a new base Data without any data attached to it. 
	 * Be sure to call {@link #setInstances(Object, Object)} (or separately by using:
	 * {@link #setInstance(Object)} and {@link #setInstance2(Object)}) before to manipulate it.
	 */
	public BaseDoubleData() {
		super();
	}
	
	// ####################################################  GETTER AND SETTERS
	
	/**
	 * @return the first data instance attached to this object.
	 * @see it.emarolab.cagg.core.language.BaseData#getInstance()
	 **/
	@Override
	public I1 getInstance() {
		return super.getInstance();
	}

	/**
	 * @param data the first data instance value to set to this object.
	 * @see it.emarolab.cagg.core.language.BaseData#setInstance(Object)
	 **/
	@Override
	public void setInstance( I1 data) {
		super.setInstance( data);
	}	
	
	/**
	 * @return the second data instance ({@code instance2}) attached to this object.
	 */
	public I2 getInstance2() {
		return this.instance2;
	}
	
	/**
	 * @param data the second data ({@code instance2}) value to set to this object.
	 */
	public void setInstance2( I2 data) {
		this.instance2 = data;
	}
	
	/**
	 * unique call to set both instances data. Basically it just 
	 * call: {@link #setInstance(Object)} (using {@code i1}) and {@link #setInstance2(Object)} (using {@code i2}).
	 * @param i1 the first data ({@code instance}) value to set to this object.
	 * @param i2 the second data ({@code instance2}) value to set to this object.
	 */
	public void setInstances( I1 i1, I2 i2){
		this.setInstance(i1);
		this.setInstance2(i2);
	}

}
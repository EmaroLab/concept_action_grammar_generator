package it.emarolab.cagg.core.language;

import java.io.Serializable;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.BaseData.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class implements the basic interface for all the data given to a tree node along all the system.
 * It contains, as a node data, only an object of type {@code D}
 * that can be initialised both in constructor {@link #BaseData(Object)} or through the methods {@link #setInstance( Object)};
 * and it can be retrieved with {@link #getInstance()}
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
 * @param <D> the main object type of this data
 */
@SuppressWarnings("serial")
public abstract class BaseData< D> implements Serializable{ 
	// ####################################################  MAIN FIELDS
	private D instance = null;

	// ####################################################  CONSTRUCTORS
	/**
	 * Instantiate this data with an actual Data value.
	 * @param instance the actual value to set to this data instance.
	 */
	public BaseData(D instance) {
		super();
		this.instance = instance;
	}	
	/**
	 * Create a new base Data without any data attached to it. Be sure to call {@link #setInstance(Object)} before to manipulate it
	 * since Data is {@code null} with this constructor.
	 */
	public BaseData() {
		super();
		//ParserLog.warning( "New BaseData created without any data. be sure to call setInstance(D d)");
	}
	
	// ####################################################  GETTER AND SETTERS
	/**
	 * @return the data instance attached to this object.
	 */
	public D getInstance() {
		return this.instance;
	}
	/**
	 * @param data the data instance value to set to this object.
	 */
	public void setInstance(D data) {
		this.instance = data;
	}
	
	/**
	 * The implementing class must implement this method for easy debugging and visualisation in the GUI.
	 * @see java.lang.Object#toString()
	 **/
	@Override
	abstract public String toString();  // you must to implement it soon or later for debugging and GUI visualisation

}
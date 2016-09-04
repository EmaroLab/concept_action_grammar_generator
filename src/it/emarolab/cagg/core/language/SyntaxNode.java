package it.emarolab.cagg.core.language;

import it.emarolab.cagg.core.BaseNode;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;

// TODO : add walker parametrisable class 
// TODO : UPDATE DOCUMENTATION

@SuppressWarnings("serial")
public abstract class SyntaxNode< T extends Enum<?>, D extends BaseData<?>> extends BaseNode {

	// ####################################################  CONSTANTS (STATIC)
	public static final String DEFAULT_PREFIX_ID = "n"; // for debug purposes on printing

	// ####################################################  MAIN FIELDS
	private T type;	// this node type
	private D data; // this node data

	// ####################################################  CONSTRUCTORS
	public SyntaxNode() {
		super();
	}
	public SyntaxNode(String prefixId) {
		super(prefixId);
	}

	// ####################################################  INITIALISATION DELEGATED
	/**
	 * This method calls {@link #assignType()} and {@link #assignData()} in order to
	 * initialise the fields: {@code type} and {@code data}.<br>
	 * This method should be called in the constructor of the expanding class to 
	 * fully initialise the objects (by considering also possible node coping).  
	 */
	protected void assign(){
		this.type = this.assignType();		// retrieve data from abstracts methods
		this.data = this.assignData();
	}
	@Override
	public BaseNode copyContents(BaseNode newNode, Boolean generateCopyId) {
		if( newNode instanceof SyntaxNode< ?, ?>){
			@SuppressWarnings("unchecked")
			SyntaxNode< T, D> casted = (SyntaxNode< T, D>) newNode;
			casted.setType( this.getType());
			casted.setData( this.getData());
		}
		return super.copyContents(newNode, generateCopyId);
	}
	
	// ####################################################  ABSTRACT METHODS
	/**
	 * This method is used to delegate as far as is needed the definition of the {@literal Data type} of this node.
	 * Particularly, its purposes is to instanciate, initailise (possibly) and return the Data assigned
	 * to this node during parsing. For convention, the data should implement the interface:
	 * {@link it.emarolab.cagg.core.language.BaseData}.<br>
	 * Usually the node Data is set in an extending implementation of this class by calling {@link #assign()}
	 * as it possible to see also from constructor documentation: {@link #NodeManager()} and {@link #NodeManager(String)}.
	 * @return Data, the not {@code null} instance of the node data to set.
	 */
	abstract protected D assignData();

	/**
	 * This method is used to delegate as far as is needed the definition of the {@literal Type} of this node.
	 * Particularly, its purposes is to instanciate (possibly) and  return the Type assigned
	 * to this node during parsing. For convention, the Type is an enumerator of the types:
	 * {@link AbstractSyntaxNode.Type} or {@link Type.ExpressionNode}.<br>
	 * Usually the node Type is set in an extending implementation of this class by calling {@link #assign()}
	 * as it possible to see also from constructor documentation: {@link #NodeManager()} and {@link #NodeManager(String)}.
	 * @return Type, the not {@code null} node type to set.
	 */
	abstract protected T assignType();
	
	
	// ####################################################  GETTERS AND SETTERS
	/**
	 * @return the node Type, assigned when {@link #assign()} was called.
	 */
	public T getType() {
		return type;
	}
	// return the type assing() to this node
	private void setType(T t) {
		this.type = t;
	}


	/**
	 * @return the node Data, assigned when {@link #assign()} was called.
	 */
	public D getData() {
		return data;
	}
	// return the data assing() to this node
	private void setData(D d) {
		this.data = d;
	}
	
	
	
}

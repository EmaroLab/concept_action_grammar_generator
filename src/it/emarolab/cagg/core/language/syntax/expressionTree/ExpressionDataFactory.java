package it.emarolab.cagg.core.language.syntax.expressionTree;

import it.emarolab.cagg.core.language.BaseData;
import it.emarolab.cagg.core.language.BaseDoubleData;

import java.io.Serializable;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionDataFactory.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 *   
 * <p>
 * This class contains useful implementation for the {@code data} related to each nodes (EN) of an Expression Tree (ET)
 * ({@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld}).
 * Particularly, this class gives easy constructors for objects to be instantiated and returned in
 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld#assignData()}.
 * Moreover, all the classes that uses this factory are extension of the {@code ExpressionNode} 
 * and they should be collected in the package:
 * {@literal cagg.core.sourceInterface.grammar.expressionTree.expressionNodeType}.<br>
 * 
 * More in details, this class implements classes as extensions of 
 * {@link it.emarolab.cagg.core.language.BaseData}
 * and {@link it.emarolab.cagg.core.language.BaseDoubleData}.
 * Particularly, the node data managed by this factory are:
 * {@link ExpressionEmptyData}, {@link ExpressionIntegerData}, {@link ExpressionStringData},
 * {@link ExpressionRepeatData} and {@code ExpressionTermData}.<br> 
 * 
 * The purpose of the classes implemented in this file is to give a flexible interface for ET node modifications and extensions. 
 * In particular, each classes that are extending 
 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld}
 * must define the type of data by using those templates or by extending them for extra features.
 * Possible example of this mechanism is shown in class
 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.LabelExpressionNode} 
 * which uses a custom node data class: the {@code LabelExpressionData}.
 * </p>
 *
 * @see ExpressionEmptyData
 * @see ExpressionIntegerData
 * @see ExpressionStringData
 * @see ExpressionRepeatData
 * @see ExpressionTermData
 * @see it.emarolab.cagg.core.language.SyntaxNode
 * @see it.emarolab.cagg.core.language.BaseData
 * @see it.emarolab.cagg.core.language.BaseDoubleData
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
 */
@SuppressWarnings("serial")
public class ExpressionDataFactory  implements Serializable{	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////  methods to instanciate and return the private classes below /////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * It creates (and returns) a new ET node data by attaching no informations by calling:
	 * {@link ExpressionDataFactory.ExpressionEmptyData#ExpressionDataFactory.ExpressionEmptyData()}.
	 * @return a new empty node data for an expression tree.
	 */
	public ExpressionEmptyData getNewExpEmpty(){	
		return new ExpressionEmptyData();
	}
	
	/**
	 * It creates (and returns) a new ET node data by attaching a String information (to be set).
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionStringData#ExpressionDataFactory.ExpressionStringData()}.
	 * @return a new string node data for an expression tree (not initialised).
	 */
	public ExpressionStringData getNewExpString(){
		return new ExpressionStringData();
	}
	/**
	 * It creates (and returns) a new ET node data by attaching an initialised String information.
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionStringData#ExpressionDataFactory.ExpressionStringData(String)}
	 * with {@code literal} as input parameter.
	 * @param literal the string value of this node data.
	 * @return a new string node data for an expression tree (initialised to {@code literal} value).
	 */
	public ExpressionStringData getNewExpString( String literal){
		return new ExpressionStringData( literal);
	}
	
	/**
	 * It creates (and returns) a new ET node data by attaching an Integer information (to be set).
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionIntegerData#ExpressionDataFactory.ExpressionIntegerData()}.
	 * @return a new integer node data for an expression tree (not initialised).
	 */
	public ExpressionIntegerData getNewExpInteger(){
		return new ExpressionIntegerData();
	}
	/**
	 * It creates (and returns) a new ET node data by attaching an initialised Integer information.
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionIntegerData#ExpressionDataFactory.ExpressionIntegerData(Integer)}
	 * with {@code value} as input parameter.
	 * @param value the integer value of this node data.
	 * @return a new integer node data for an expression tree (initialised to {@code value}).
	 */
	public ExpressionIntegerData getNewExpInteger( Integer value){
		return new ExpressionIntegerData( value);
	}
	
	/**
	 * It creates (and returns) a new ET node data by attaching a String and a Boolean information (to be set).
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionTermData#ExpressionDataFactory.ExpressionTermData()}.
	 * @return a new Term (string and boolean variables) node data for an expression tree (not initialised).
	 */
	public ExpressionTermData getNewExpTerm(){
		return new ExpressionTermData();
	}
	/**
	 * It creates (and returns) a new ET node data by attaching an initialised String and a Boolean information.
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionTermData#ExpressionDataFactory.ExpressionTermData(String, Boolean)}
	 * with {@code literal} and {@code isDeclaration} as input parameters.
	 * @param literal the string value of this node data.
	 * @param isDeclaration the boolean value of this node data.
	 * @return a new Term (string and boolean variables) node data for an expression tree 
	 * (initialised to {@code literal} and {@code isDeclaration}).
	 */
	public ExpressionTermData getNewExpTerm( String literal, Boolean isDeclaration){
		return new ExpressionTermData( literal, isDeclaration);
	}
	
	/**
	 * It creates (and returns) a new ET node data by attaching two Integers information (to be set).
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionRepeatData#ExpressionDataFactory.ExpressionRepeatData()}.
	 * @return a new Repeat (two integer variables) node data for an expression tree (not initialised).
	 */
	public ExpressionRepeatData getNewExpRepeat(){
		return new ExpressionRepeatData();
	}
	/**
	 * It creates (and returns) a new ET node data by attaching two initialised Integers information.
	 * This is done by calling:
	 * {@link ExpressionDataFactory.ExpressionRepeatData#ExpressionDataFactory.ExpressionRepeatData(Integer, Integer)}
	 * with {@code minLoop} and {@code maxLoop} as input parameters.
	 * @param minLoop the first integer value of this node data.
	 * @param maxLoop the second integer value of this node data.
	 * @return a new Repeat (two integers) node data for an expression tree
	 * (initialised to {@code minLoop} and {@code maxLoop} respectively).
	 * 
	 */
	public ExpressionRepeatData getNewExpRepeat( Integer minLoop, Integer maxLoop){
		return new ExpressionRepeatData( minLoop, maxLoop);
	}

	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////  Classes to implement specific ET node data  ////////////////////////// 
	////////////////////////////////////////////////////////////////////////////////////////////////
		
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class implements a node data that does not contains any information.
	 * This is used for the nodes that are meaningful just for their position on the expression tree.
	 * Particularly, it extends:
	 * {@link it.emarolab.cagg.core.language.BaseData};
	 * with {@code <Void>} parameter.<br>
	 * This node data is used in:
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.AndExpressionNode},
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OptionalExpressionNode},
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OrExpressionNode} and
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.PronounceExpressionNode}.
	 * </p>
	 *
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 * @see it.emarolab.cagg.core.language.BaseData
	 * @see ExpressionDataFactory
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.AndExpressionNode
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OptionalExpressionNode
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.PronounceExpressionNode
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.OrExpressionNode
	 */
	public class ExpressionEmptyData extends BaseData< Void> {
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseData#BaseData()}.
		 */
		public ExpressionEmptyData(){
			super();
		}
	
		/**
		 * This method describes the node data a static information.
		 * In fact, it returns always {@code "(.)"}.
		 */
		@Override
		public String toString() {
			return ".";
		}
	}
	
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class implements a node data that contains a String information.
	 * Particularly, it extends:
	 * {@link it.emarolab.cagg.core.language.BaseData},
	 * with {@code <String>} parameter.<br>
	 * This node data is used in:
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.ActionExpressionNode}
	 * </p>
	 *
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 * @see it.emarolab.cagg.core.language.BaseData
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.ActionExpressionNode
	 * @see ExpressionDataFactory
	 */
	public class ExpressionStringData extends BaseData< String>{
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseData#BaseData()}.
		 * Be aware that this constructor does not initialise the String value itself.
		 */
		public ExpressionStringData(){
			super();
		}
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseData#BaseData(Object)}.
		 * So, this constructor initialise the String value to {@code literal}  (instance1).
		 * @param literal the string literal to be set to this node data.
		 */
		public ExpressionStringData( String literal){
			super( literal);
		}
		
		/**
		 * This method returns the instance value attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseData#getInstance()}.
		 * Its purpose is just to make the getter method name more self-explaining
		 * by explicitly say that it returns a literal String, rather than a generic instance.
		 * @return the literal instance of this node data
		 */
		public String getLiteral(){
			return super.getInstance();
		}
		/**
		 * This method sets the instance attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseData#setInstance(Object)}.
		 * Its purpose is just to make the setter method name more self-explaining
		 * by explicitly say that it sets a literal String, rather than a generic instance.
		 * @param literal the string literal to be assigned to this node data.
		 */
		public void setLiteral( String literal){
			super.setInstance( literal);
		}
		
		/**
		 * It describes this node data as a string formatted as:
		 * {@code "(" + literal + ")"}, 
		 * where {@code literal} is given from {@link #getLiteral()} 
		 * @see it.emarolab.cagg.core.language.BaseData#toString()
		 */
		@Override
		public String toString() {
			return this.getLiteral();
		}
	}
	
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class implements a node data that contains an Integer information.
	 * Particularly, it extends:
	 * {@link it.emarolab.cagg.core.language.BaseData},
	 * with {@code <Integer>} parameter.<br>
	 * This node data is used in:
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.IdExpressionNode}.
	 * </p>
	 *
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 * @see it.emarolab.cagg.core.language.BaseData
	 * @see ExpressionDataFactory
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.IdExpressionNode
	 */
	public class ExpressionIntegerData extends BaseData< Integer>{
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseData#BaseData()}.
		 * Be aware that this constructor does not initialise the String value itself.
		 */
		public ExpressionIntegerData(){
			super();
		}
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseData#BaseData(Object)}.
		 * So, this constructor initialise the String to {@code value} (instance1).
		 * @param value the integer value to be set to this node data.
		 */
		public ExpressionIntegerData( Integer value){
			super( value);
		}
		
		/**
		 * This method returns the instance value attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseData#getInstance()}.
		 * Its purpose is just to make the getter method name more self-explaining
		 * by explicitly say that it returns an Integer value, rather than a generic instance.
		 * @return the value instance of this node data
		 */
		public Integer getInteger(){
			return super.getInstance();
		}
		/**
		 * This method sets the instance attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseData#setInstance(Object)}.
		 * Its purpose is just to make the setter method name more self-explaining
		 * by explicitly say that it sets an Integer value, rather than a generic instance.
		 * @param value the Integer value to be assigned to this node data.
		 */
		public void setInteger( Integer value){
			super.setInstance( value);
		}
		
		/**
		 * It describes this node data as a string formatted as:
		 * {@code "(" + value + ")"}, 
		 * where {@code value} is given from {@link #getInteger()}
		 * @see it.emarolab.cagg.core.language.BaseData#toString()
		 */
		@Override
		public String toString() {
			return this.getInteger().toString();
		}
	}
		
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 * 
	 * <p>
	 * This class implements a node data that contains a String and a Boolean information.
	 * Particularly, it extends:
	 * {@link it.emarolab.cagg.core.language.BaseDoubleData},
	 * with {@code <String, Boolean>} parameters.<br>
	 * 
	 * In particular, it is used to describe the information related to a expression term node (EN leaves)
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.TermExpressionNode}. 
	 * In this case, the intance1 (string) contains the name of the term while the instance2 (boolean)
	 * describes if it is a term name or a sub rule declaration. 
	 * More in details, the string literal {@code name} contains the value of the Term that can be of the type:
	 * {@link #FROM_IDENTIFIER}, {@link #FROM_STRING} or {@link #FROM_DECLARATION}.
	 * While the boolean flag {@code isDeclaration} is {@code true} if the type of this term is {@code FROM_DECLARATION};
	 * {@code false} otherwise. See more specifications on the type of term from 
	 * {@link ExpressionDataFactory.ExpressionTermData#getTokenType(String)}. 
	 * </p>
	 * 
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 * @see it.emarolab.cagg.core.language.BaseDoubleData
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.TermExpressionNode
	 * @see ExpressionDataFactory
	 */
	public class ExpressionTermData extends BaseDoubleData<String, Boolean> {
		
		/**
		 *  Default value returned from {@link #getTokenType(String)},
		 *  where a not mapped case occurs.
		 *  Value set to {@literal -1}.
		 */
		public static final int FROM_ERROR = -1;		
		/**
		 *  Default value returned from {@link #getTokenType(String)}
		 *  if it identifies a term given through an identifier (grammar e.g. Hello).
		 *  Value set to {@literal 0}.
		 */
		public static final int FROM_IDENTIFIER = 0;	
		/**
		 *  Default value returned from {@link #getTokenType(String)}
		 *  if it identifies a term given through an identifier (grammar e.g. "Hello").
		 *  Value set to {@literal 1}.
		 */
		public static final int FROM_STRING = 1;		
		/**
		 *  Default value returned from {@link #getTokenType(String)}
		 *  if it identifies a term given through rule declaration (eg. &lt;Hello&gt;).
		 *  Value set to {@literal 2}.
		 */
		public static final int FROM_DECLARATION = 2;	
		
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseDoubleData#BaseDoubleData()}.
		 * Be aware that this constructor does not initialise the String and Boolean values themselves.
		 */
		public ExpressionTermData() {
			super();
		}
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseDoubleData#BaseDoubleData(Object, Object)}.
		 * So, this constructor initialise the String to {@code literal} (instance1)
		 * and the Boolean to {@code isDeclaration} (instance2).
		 * @param name the String value to be set to this expression term node data name.
		 * (the String literal of the Term, (instance1)).
		 * @param isDeclaration the flag to discriminate if Term is a rule declaration ({@code true}) or not ({@code false}).
		 * (the Boolean flag of the Term, (instance2)).
		 */
		public ExpressionTermData(String name, Boolean isDeclaration) {
			super( name, isDeclaration);
		}
		
		/**
		 * This method returns an instance value attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#getInstance()}.
		 * Its purpose is just to make the getter method name more self-explaining
		 * by explicitly say that it returns a String literal, rather than a generic instance.
		 * @return the String literal of the Term (instance1).
		 * (the term node data name).
		 */
		public String getTermLiteral(){
			return super.getInstance();
		}
		/**
		 * This method sets an instance attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#setInstance(Object)}.
		 * Its purpose is just to make the setter method name more self-explaining
		 * by explicitly say that it sets a String literal, rather than a generic instance.
		 * @param name the String literal of the Term (instance1) to be set.
		 * (the literal to be set to this expression term node data name).
		 */
		public void setTermLiteral( String name){
			super.setInstance( name);
		}
		
		/**
		 * This method returns an instance value attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#getInstance2()}.
		 * Its purpose is just to make the getter method name more self-explaining
		 * by explicitly say that it returns a Boolean flag, rather than a generic instance.
		 * @return the Boolean flag of the Term (instance2).
		 * ({@code true} if this Term type is {@link #FROM_DECLARATION}, {@code false} otherwise).
		 */
		public Boolean getTermFlag(){
			return this.getInstance2();
		}
		/**
		 * This method sets an instance attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#setInstance2(Object)}.
		 * Its purpose is just to make the setter method name more self-explaining
		 * by explicitly say that it sets a Boolean flag, rather than a generic instance.
		 * Moreover, for calculating the type of the term in order to set coherently this data see:
		 * {@link #getTokenType(String)} and {@link #isDeclaration(String)}.
		 * @param isDeclaration the Boolean of the Term (instance1) to be set.
		 * ({@code true} if this Term type is {@link #FROM_DECLARATION}, {@code false} otherwise).
		 */		
		public void setTermFlag( Boolean isDeclaration){
			this.setInstance2( isDeclaration);
		}
		
		/**
		 * It returns an integer tag to discriminate if this term is composed 
		 * by a rule declaration, a string or identifier. The returning value is in the set:
		 * {@link #FROM_ERROR} (if {@code name} is {@code null} or empty),
		 * {@link #FROM_IDENTIFIER}, {@link #FROM_STRING} or {@link #FROM_DECLARATION}.
		 * The literal should not contains any tagging directive (e.g. {@code "{@=...}"})
		 * @param name the String literal of the Term (instance1).
		 * (the expression term node data name).
		 * @return the type of this Term that.
		 */
		public int getTokenType( String name){
			if( name == null)
				return FROM_ERROR;													// error if null
			if( name.isEmpty())
				return FROM_ERROR;													// error if empty
			if( name.contains( "<") && name.contains( ">"))		
				return FROM_DECLARATION;											// declation if contains < and >
			else if( name.contains( "\""))
				return FROM_STRING;													// from string if contains one "
			else return FROM_IDENTIFIER;											// from identifiers in any other cases
		}
		
		/**
		 * It calls {@link #getTokenType(String)} and returns 
		 * {@code true} if the Term is of the type {@link #FROM_DECLARATION}.
		 * {@code false} otherwise.
		 * @param name the String literal of the Term (instance1).
		 * (the expression term node data name).
		 * @return {@code true} if this term identify another rule definition (sub-ET).
		 * {@code false} otherwise.
		 */
		public Boolean isDeclaration( String name){
			if( getTokenType( name) == FROM_DECLARATION)
				return true;
			else return false;
		}
		
		/**
		 * It describes the node data with a string formatted as:
		 * {@code "(" + literal + flag + ")";}.
		 * Where, {@code literal} is got from {@code #getTermLiteral()}.
		 * While, {@code flag} is an empty string if {@link #getTermFlag()} returns {@code null} or {@code false},
		 * otherwise: {@code flag = ":<T>"}. 
		 * @see it.emarolab.cagg.core.language.BaseData#toString()
		 */
		@Override
		public String toString() {
			String ruleDeclFlag = "";
			if( this.getTermFlag() != null)
				if( this.getTermFlag())
					ruleDeclFlag = ":<T>";
			return this.getTermLiteral() + ruleDeclFlag;
		}
	}
	
		
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionDataFactory.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class implements a node data that contains two integer information.
	 * Particularly, it extends:
	 * {@link it.emarolab.cagg.core.language.BaseDoubleData},
	 * with {@code <Integer, Integer>} parameters.<br>
	 * 
	 * In particular, it is used to describe the information related to a repeat operator
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.RepeatExpressionNode}. 
	 * In this case, the intance1 contains the minimum looping times while, the instance2 the maximum number of times.  
	 * </p>
	 * 
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 * @see it.emarolab.cagg.core.language.BaseDoubleData
	 * @see ExpressionDataFactory
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.expressionNodeType.RepeatExpressionNode
	 */
	public class ExpressionRepeatData extends BaseDoubleData< Integer, Integer> {
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseDoubleData#BaseDoubleData()}.
		 * Be aware that this constructor does not initialise the Integer values themselves.
		 */
		public ExpressionRepeatData() {
			super();
		}
		/**
		 * Initialise this class by calling the super constructor: 
		 * {@link it.emarolab.cagg.core.language.BaseDoubleData#BaseDoubleData(Object, Object)}.
		 * So, this constructor initialise the Integers {@code minLoop} (instance1)
		 * and {@code maxLoop} (instance2).
		 * @param minLoop the minimum number of repeat looping.
		 * @param maxLoop the maximum number of repeat looping.
		 */
		public ExpressionRepeatData(Integer minLoop, Integer maxLoop) {
			super( minLoop, maxLoop);
		}
			
		/**
		 * This method returns an instance value attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#getInstance()}.
		 * Its purpose is just to make the getter method name more self-explaining
		 * by explicitly say that it returns an Integer value, rather than a generic instance.
		 * @return the minimum number of repeat looping (instance1).
		 */
		public Integer getMinLoopCount(){
			return super.getInstance();
		}
		/**
		 * This method sets an instance attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#setInstance(Object)}.
		 * Its purpose is just to make the setter method name more self-explaining
		 * by explicitly say that it sets an Integer value, rather than a generic instance.
		 * @param minLoopCnt the minimum number of repeat looping (instace1) to be set.
		 */
		public void setMinLoopCount( Integer minLoopCnt){
			super.setInstance( minLoopCnt);
		}
		
		/**
		 * This method returns an instance value attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#getInstance2()}.
		 * Its purpose is just to make the getter method name more self-explaining
		 * by explicitly say that it returns an Integer value, rather than a generic instance.
		 * @return the maximum number of repeat looping (instance2).
		 */
		public Integer getMaxLoopCount(){
			return this.getInstance2();
		}
		/**
		 * This method sets an instance attached to this node data
		 * by calling {@link it.emarolab.cagg.core.language.BaseDoubleData#setInstance2(Object)}.
		 * Its purpose is just to make the setter method name more self-explaining
		 * by explicitly say that it sets an Integer value, rather than a generic instance.
		 * @param maxLoopCnt the maximum number of repeat looping (instace2) to be set.
		 */
		public void setMaxLoopCount( Integer maxLoopCnt){
			this.setInstance2( maxLoopCnt);
		}
		
		/**
		 * It describes the node data with a string formatted as:
		 * {@code "(" + minLoopCount + ";" + maxLoopCount + ")"}.
		 * Where, {@code minLoopCount} is given from {@link #getMinLoopCount()}.
		 * While, {@code maxLoopCount} is given from {@link #getMaxLoopCount()}.
		 * @see it.emarolab.cagg.core.language.BaseData#toString()
		 */
		@Override
		public String toString() {
			return this.getMinLoopCount() + ";" + this.getMaxLoopCount();
		}
		
	}
}

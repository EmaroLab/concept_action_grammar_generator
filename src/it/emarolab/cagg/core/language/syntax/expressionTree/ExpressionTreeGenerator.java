package it.emarolab.cagg.core.language.syntax.expressionTree;

import it.emarolab.cagg.core.language.ParserLog;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionTreeGenerator.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class is used from {@link it.emarolab.cagg.core.language.parser.ANTLRInterface.TreeParserListener}
 * in order to create Expression Tree (ET) from a Parsing Tree (PT). 
 * Moreover, the created ET is suppose to be the node {@code data} of a Abstract Syntax Tree (AST).<br>
 * 
 * The purpose of this class is to create an ET during parsing. In particular, it is possible
 * to add operation when they are intercepted by the parser (enter rule) and than update the ET
 * in order to generate it.
 * More in details, by adding operation this class manage a set of {@code Queues} 
 * (implemented in {@link ExpressionQueues}). On the other hand, by
 * updating the tree it pull informations for the queues in order to add them in the tree correctly.
 * </p>
 *
 * @see ExpressionQueues
 * @see it.emarolab.cagg.core.language.parser.ANTLRInterface.TreeParserListener
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 */
public class ExpressionTreeGenerator {	// it generates Expression Tree (ET) from Parsing Tree (PT). ET it is suppose to be the data of a rule node in the Abstract Syntax Tree (AST) 

	// ########################################################################################################################
	// Configurations Constants ###############################################################################################
	// queue pending values (returned from ExpressionQueue.hasPending)
	/**
	 * The constant tag value given from {@link ExpressionQueues#hasPending()} in case of error.
	 * Default value: {@code -1}.
	 */
	public static final Integer PENDING_ERROR = -1;			// not considered cases
	/**
	 * The constant tag value given from {@link ExpressionQueues#hasPending()} in case of no action to be performed
	 * on tree creation. Default value: {@code 0}.
	 */
	public static final Integer PENDING_NO = 0;				// no operation or term pending (if ExpressionQueue.hasPending() > PENDING_NO => something is pending)
	/**
	 * The constant tag value given from {@link ExpressionQueues#hasPending()} in case in which a operation node
	 * should be considered on tree creation. Default value: {@code 1}.
	 */
	public static final Integer PENDING_OPERATION = 1;		// at least operation pending 
	/**
	 * The constant tag value given from {@link ExpressionQueues#hasPending()} in case in which a term node
	 * should be considered on tree creation. Default value: {@code 2}.
	 */
	public static final Integer PENDING_TERM = 2;			// at least term pending 
	
	// ########################################################################################################################
	// Class Fields ###########################################################################################################
	private ExpressionNode<?> expressionRoot; 			// the root to the generated expression tree 
	private ExpressionNode<?> expressionNodeState;		// the node to be manipulated for building the tree
	private ExpressionQueues queues;						// the queue of traversed operations and terms  
	
	// ########################################################################################################################
	// Initialise (Constructor) ###############################################################################################
	/**
	 * Initialise this class by creating a new ET root, internal state variables and queues ({@link ExpressionQueues}).
	 */
	public ExpressionTreeGenerator(){
		expressionRoot = null;
		expressionNodeState = null;
		queues = new ExpressionQueues();
		ParserLog.info( "Expression Tree Generator initalised. New queues create!");
	}
	
	// ########################################################################################################################
	// Getters  ###############################################################################################################
	/**
	 * @return the root of the generated ET.
	 */
	public ExpressionNode<?> getExpressionTree(){
		return expressionRoot;
	}
	/**
	 * @return the queues of the node to be added to the ET.
	 */
	public ExpressionQueues getExpressionQueses(){
		return queues;
	}
	
	// ########################################################################################################################
	// Method to create the tree ##############################################################################################	
	/**
	 * This method introduce a new parsed operation node to the {@link ExpressionQueues}. 
	 * Finally, it updates the ET with this new node by pulling data from the queues. 
	 * @param e the operation node to be added to the ET.
	 */
	public void addOperation(ExpressionNode<?> e){
		queues.operationAdd(e);
		updateExpressionTree( queues);
	}
	/**
	 * This method introduce a new parsed term node to the {@link ExpressionQueues}. 
	 * Finally, it updates the ET with this new node by pulling data from the queues. 
	 * @param e the term node to be added to the ET.
	 */
	public void addTerm(ExpressionNode<?> e){
		queues.termAdd(e);
		updateExpressionTree( queues);
	}
		
	// check the state of the queues and build the tree. It will create a root in the first iteration and manage max number of children constrains for specific nodes.
	// it base expression precedences based on parsing tree and it is suggested to call it as soon as queues modifications are performed.
	private void updateExpressionTree( ExpressionQueues queues){
		ParserLog.info( "update expression tree (state:" + expressionNodeState + ") with queues:" + queues);		
		if( expressionRoot == null && queues.hasPending() > PENDING_NO){										// CREATE new expression tree 
			expressionRoot = getExpressionTreeRoot( queues);	
			expressionNodeState = expressionRoot;
			ParserLog.info( "create new expression tree !!!");
		} else{
			Boolean didSomething = false;
			if( queues.hasPending() == PENDING_OPERATION){														// add OPERATION node
				addExpressionOperationNode( expressionNodeState, queues.operationPull());	// it updates expressionNodeState with queues.operationPop()
				ParserLog.info( "adding operation node to the tree. Steaks:" + queues + ". (New tree state node:" + expressionNodeState +")");
				didSomething = true;
			}
			if( queues.hasPending() == PENDING_TERM){															// add TERM node
				addExpressionTermNode( expressionNodeState, queues.termPull());				// it updates expressionNodeState with searched tree state
				ParserLog.info( "adding terms node to the tree. Steaks:" + queues + ". (New tree state node:" + expressionNodeState +")");
				didSomething = true;
			} 
			if( ! didSomething) 
				ParserLog.warning( "ExpressionTree updating did not change nothing !!!");
		}
	}
	
	// create the tree by returning its root computed on base of the queues values.
	// It would return null if there are no pending term or operation on the queues.
	private ExpressionNode<?> getExpressionTreeRoot( ExpressionQueues queues){
		if( queues.hasOperatorPending())
			return queues.operationPull();
		if( queues.hasTermPending())
			return queues.termPull();
		ParserLog.error("There are no pendings elements on the Expression queues. Tree root cannod be created");
		return null;
	}
	
	// Try to add an operation node to the state of the tree (by considering the maximum number of children it can contains).
	// If the node can be added it will locks for the first parent that can accommodate the new operation.
	private void addExpressionOperationNode( ExpressionNode<?> treeState, ExpressionNode<?> toAdd){
		ParserLog.info( "attempt to add expression OPERATOR \"" + toAdd + "\" to state:" + treeState);
		if( treeState != null && toAdd != null){
			if( treeState.isComplite())
				addExpressionOperationNode( (ExpressionNode<?>) treeState.getParent(), toAdd);		// recursive call back
			else {
				treeState.addChild( toAdd);															// add new node to the expression tree
				expressionNodeState = toAdd;														// update tree state
				ParserLog.info( "attempt add operator successful !!! returning state: " + toAdd);
				return;
			}
		} else ParserLog.warning( "NULL tree state on expression operation adding");
	}
	
	// Try to add a term node to the state of the tree (by considering the maximum number of children it can contains).
	// It would never update the tree state with a term node since it cannot have any children by definition. 
	// This is why the maximum number of children for the TermExpressionNode (=0) does not affect tree generation
	private void addExpressionTermNode( ExpressionNode<?> treeState, ExpressionNode<?> toAdd){	
		ParserLog.info( "attempt to add expression TERM \"" + toAdd + "\" to state: " + treeState);
		if( treeState != null && toAdd != null){
			if( treeState.isComplite())
				addExpressionTermNode( (ExpressionNode<?>) treeState.getParent(), toAdd);			// recursive call back
			else {
				treeState.addChild( toAdd);															// add new node to the expression tree
				expressionNodeState = treeState;													// update tree state
				ParserLog.info( "attempt add term successful !!! returning state: " + treeState);
				return;
			}
		}else ParserLog.warning( "NULL tree state on expression term adding");
	}
	
	
	
	
	// ########################################################################################################################
	// Class to manage Operators and Terms queues #############################################################################		
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.grammar.expressionTree.ExpressionTreeGenerator.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class implements and manage two different {@link java.util.Queue} of 
	 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld}.
	 * Particularly, one queue contains only the Operation nodes, while the other only the Term node.<br>
	 * 
	 * Moreover this class monitor the state of the queues by considering the number of elements and,
	 * based on that, it returns the type of pending action to be performed in order to build the ET 
	 * through the methods: {@link #hasPending()}, {@link #hasOperatorPending()} and {@link #hasTermPending()}.
	 * </p>
	 *
	 * @see java.util.Queue
	 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
	 */
	public class ExpressionQueues{
		////////////////////////	CLASS FIELDS
		private Queue< ExpressionNode<?>> operationQueue,termQueue;	// the manages and implemented Queues
		
		////////////////////////	CONSTRUCTOR
		/**
		 * Initialise this class by creating new Term and Operation queues.
		 */
		public ExpressionQueues(){										// initialise (constructor)
			operationQueue = new LinkedList< ExpressionNode<?>>();
			termQueue = new LinkedList< ExpressionNode<?>>();
		}
		
		////////////////////////	GETTERS
		/**
		 * @return the queue containing all the Operation nodes not yet added to the ET.
		 */
		public Queue<ExpressionNode<?>> getOperationQueue(){			
			return operationQueue;										// get the operations
		}
		/**
		 * @return the queue containing all the Term nodes not yet added to the ET.
		 */
		public Queue<ExpressionNode<?>> getTermQueue(){				
			return termQueue;											// get the terms
		}
		
		////////////////////////	QUEUES OPERATION IMPLEMENTATION
		/** 
		 * This method add a node to the operation queue by calling:
		 * {@link java.util.Queue#offer(Object)}.
		 * @param e the node to be added to the Operation queue.
		 */
		public void operationAdd(ExpressionNode<?> e){
			operationQueue.offer(e);									//  add a value in the operation queue 
		}
		/**
		 * This method add a node to the term queue by calling:
		 * {@link java.util.Queue#offer(Object)}.
		 * @param e the node to be added to the Term queue.
		 */
		public void termAdd(ExpressionNode<?> e){
			termQueue.offer(e);											//  add a value in the term queue 
		}	
		
		/**
		 * This method returns and remove an operation node from the operation queue.
		 * @return the result of {@link java.util.Queue#poll()} applied to the Operation queue.
		 */
		public ExpressionNode<?> operationPull(){					
			return operationQueue.poll();								//  return and remove the first value in the operation queue 
		}
		/**
		 * This method returns and remove a term node from the term queue.
		 * @return the result of {@link java.util.Queue#poll()} applied to the Term queue.
		 */
		public ExpressionNode<?> termPull(){
			return termQueue.poll();									//  return and remove return the first value in the term queue 
		}
		
		/**
		 * This method returns (but not remove) an operation node from the operation queue.
		 * @return the result of {@link java.util.Queue#peek()} applied to the Operation queue.
		 */
		public ExpressionNode<?> operationPeek(){
			return operationQueue.peek();								//  return and not remove a value in the operation queue 
		}	
		/**
		 * This method returns (but not remove) a term node from the term queue.
		 * @return the result of {@link java.util.Queue#peek()} applied to the Term queue.
		 */
		public ExpressionNode<?> termPeek(){
			return termQueue.peek();									//  return and not remove a value in the term queue
		}
		
		////////////////////////	METHODS TO CHECK THE STATE OF THE QUEUES
		/**
		 * It check the size of the Operation queue in order to check if 
		 * there are at least one operation node to be processed during ET generation.
		 * @return {@code true} if there are at least one operation in the queues. {@code false} otherwise.
		 */
		public Boolean hasOperatorPending(){							
			return ! operationQueue.isEmpty();							// there is at least one operation in its queue? 
		}
		/**
		 * It check the size of the Term queue in order to check if 
		 * there are at least one term node to be processed during ET generation.
		 * @return {@code true} if there are at least one term in the queues. {@code false} otherwise.
		 */
		public Boolean hasTermPending(){								
			return ! termQueue.isEmpty(); 								// there is at least one temr in its queue?
		}
		/**
		 * This method check the state of the Operation and Term queues.
		 * Particolarly, it returns: 
		 * {@link ExpressionTreeGenerator#PENDING_NO} if both queues are empty.
		 * Or: {@link ExpressionTreeGenerator#PENDING_OPERATION} if the Operation queue is not empty.
		 * Or: {@link ExpressionTreeGenerator#PENDING_TERM} if the Operation queue is empty but the Term queue not.
		 * Or: {@link ExpressionTreeGenerator#PENDING_ERROR} otherwise.
		 * @return the actual pending node in queues tag.
		 */
		public Integer hasPending(){ 									// check what value are pending on the queue, , 1:at least operation pending, 2:at least term pending, 3: at leas one of them pernding
			Integer returnTag = PENDING_ERROR;							// -1:error (not considered case)
			if( ! hasOperatorPending() && ! hasTermPending())
				returnTag = PENDING_NO;									// 0:pending_no (nothing is in both queues) [if resturns > PENDING_NO => something is pending]
			else if( hasOperatorPending())
				returnTag = PENDING_OPERATION;							// 1:pending_op (at least one operation is in its queue)
			else if( hasTermPending())
				returnTag = PENDING_TERM;								// 2:pending_term (at least one term is in its queue)
			return returnTag;
		}
		
		
		/**
		 * This method describe this class with a string formatted as:
		 * {@code "{[OPERATORS:" + operationQueue + " ];[ TEMRS:" + termQueue + "]}";}.
		 * Where, {@code operationQueue} is given from {@link #getOperationQueue()}.
		 * While, {@code termQueue} is given from {@link #getTermQueue()}.
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString(){										// for debugging and visualisation
			return "{[OPERATORS:" + operationQueue + " ];[ TEMRS:" + termQueue + "]}";
		}
	}
	
}

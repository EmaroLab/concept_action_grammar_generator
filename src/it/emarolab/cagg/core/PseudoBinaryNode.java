package it.emarolab.cagg.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.MutableTreeNode;

import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.SyntaxNode;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;
import it.emarolab.cagg.debugging.DebuggingText;

@SuppressWarnings("serial")
abstract public class PseudoBinaryNode extends BaseNode{

	/* ##################################################################################
	   ############################## FIELDS ############################################
	   */
	private Boolean fromChildrenMethod = false; // internally used only to create a binary tree.
	private Integer order; // it can have 2 children (and,or), 1 child (optional...), 0 child (terms)
	private Boolean isLeft = null; // true = is left, false = is right, null = unknown
	
	/* ##################################################################################
	   ###################### DEFAULT CONSTRUCTOR #######################################
	   */
	public PseudoBinaryNode() {
		super(); // use defualt prefix id
		initialise( null);
	}
	public PseudoBinaryNode( String prefixId) {
		super( prefixId);
		if( prefixId != null) // call null on copy
			initialise( null);
	}
	public PseudoBinaryNode( Integer maxChildrenCount) {
		super();
		if( maxChildrenCount != null) // call null on copy
			initialise( maxChildrenCount);
	}
	public PseudoBinaryNode( Integer maxChildrenCount, String prefixId) {
		super(prefixId);
		if( maxChildrenCount != null && prefixId != null) // call null,null on copy
			initialise( maxChildrenCount);
	}
	private void initialise(Integer maxChildrenCount) {
		if( maxChildrenCount != null)
			setMaxChildrenCount( maxChildrenCount);
		else ParserLog.warning( this.getClass().getSimpleName() + " instanciated withoud a required field. Remember to call setMaxChildrenCount()");
	}
		
	/* ##################################################################################
	   ############################## ABSTRACT #########################################
	   */
	@Override
	abstract public PseudoBinaryNode copy( Boolean createNewCopyID);
	
	/* ##################################################################################
	   ############################# GETTERS ############################################
	   */
	@Override
	public PseudoBinaryNode getChild( int i){
		return ( PseudoBinaryNode) super.getChild( i);
	}
	public PseudoBinaryNode getLeftChild() {
		if( hasLeftChild())
			return (PseudoBinaryNode) this.getChild( 0);
		else return null;
	}
	public PseudoBinaryNode getRightChild() {
		if( hasRightChild())
			return (PseudoBinaryNode) this.getChild( 1);
		else return null;
	}
	/**
	 * @return the maximum number of children that this node can accepts. 
	 * This is set on constructor or by {@link #copyContents(SyntaxNode)}
	 * by calling {@link #getMaxChildrenCount()}.
	 */
	public Integer getMaxChildrenCount(){
		return order;
	}
	
	/* ##################################################################################
	   ############################# SETTERS ############################################
	   */
	private void setMaxChildrenCount( Integer maxChildrenCount){
		if( maxChildrenCount > 2 || maxChildrenCount < 0)
			ParserLog.error("The maximum number of children for a SemanticTree must be between 0 and 2. Example: 2(and,or node), 1(optional node...), 0(terms node).");
		this.order = maxChildrenCount;
	}
	protected void setLeft(){
		isLeft = true;
	}
	protected void setRight(){
		isLeft = false;
	}
	protected void resetPosition(){
		isLeft = null;
	}
	private void setPosition( PseudoBinaryNode newChild){  // works befaure to actually add child
		if( this.getChildCount() == 0){
			newChild.setLeft();
			return;
		}else if( this.getChildCount() == 1){
			newChild.setRight();
			return;
		}
		ParserLog.error( "Something where wrong. Position set on node " + this + " failed!");
	}
	
	/* ##################################################################################
	   ################## MANAGE LEFT RIGTH PROPERTY ####################################
	   */
	/** 
	 * @return {@code true} if the node has as much children as it maximum number defines ({@link #getMaxChildrenCount()}}). 
	 * {@code false} otherwise
	 */
	public Boolean isComplite(){
		if( this.getChildCount() == this.getMaxChildrenCount())
			return true;
		return false;
	}
	public Boolean isLeft(){
		return isLeft;
	}
	public boolean hasLeftChild() {
		try{
			this.getChild( 0);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public boolean hasRightChild() {
		try{
			this.getChild( 1);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/* ##################################################################################
	   ##################### CHILDREN ADD/REMOVE METHODS ################################
	   */
	/**
	 * add a new child to this node. Do not call directly this function in order to use 
	 * max child managing mechanism. Use {@link #addChild(ExpressionNode)} or 
	 * {@link #addChildren(ExpressionNode, ExpressionNode)} instead.
	 * @param newChild the new node to be added as a child to this parent.
	 */
	@Override
	public void add(MutableTreeNode newChild) {
		if( newChild instanceof PseudoBinaryNode){
			if( ! fromChildrenMethod){
				DebuggingText.printStackTrace();
				ParserLog.error( "do not use add() function on expression binary tree. Use addChild/ren() instead." );
			}
			PseudoBinaryNode child = ( PseudoBinaryNode) newChild;
			fromChildrenMethod = false;
			this.setPosition( child);
		}
		super.add( newChild);
	}
	@Override
	protected void add(MutableTreeNode newChild, Boolean suppressWarning) {
		fromChildrenMethod = suppressWarning;
		this.add( newChild);
	}
	/**
	 * add two new children to this node by managing the fact that this tree should 
	 * satisfy {@link #getMaxChildrenCount()}.
	 * Be aware that it does not do nothing if the {@code newChild1 = newChild2}, 
	 * since it calls {@link #add(MutableTreeNode)}.
	 * @param newChild1 a new node to be added as a child of this node.
	 * @param newChild2 another new node to be added as a child of this node.
	 */
	public void addChildren( PseudoBinaryNode leftChild, PseudoBinaryNode rightChild){
		fromChildrenMethod = true;
		this.addChild( leftChild);
		this.addChild( rightChild);
	}
	/**
	 * add a new children to this node by managing the fact that this tree should 
	 * satisfy {@link #getMaxChildrenCount()}.
	 * Be aware that it does not do nothing if the {@code newChild1} is equal to another 
	 * child of this node, since it calls {@link #add(MutableTreeNode)}.
	 * @param newChild a new node to be added as a child of this node.
	 */
	public void addChild( PseudoBinaryNode newChild){
		fromChildrenMethod = true;
		if( this.getChildCount() >= 2)
			ParserLog.error( "The ExpressionNode [" + this + "] has already two children. Node [" + this + "] cannot be added to the tree!");
		else {
			this.add( newChild);
		}
	}
	public void addLeftChild( PseudoBinaryNode newChild){
		if( this.getChildCount() < 1)
			this.addChild( newChild); // create new
		else this.add( 0, newChild); // replace
	}
	public void addRightChild( PseudoBinaryNode newChild){
		if( this.getChildCount() < 1){
			ParserLog.error( "Cannot add a right node (" + newChild + ") to a node that is a left node. This=" + this);
		} else if( this.getChildCount() < 2)
			this.addChild( newChild); // create new
		else this.add( 1, newChild); // replace
	}
	@Override
	public void add(int position, BaseNode newChild) {
		fromChildrenMethod = true;
		if( this.getChildCount() >= 2)
			ParserLog.error( "The ExpressionNode [" + this + "] has already two children. Node [" + this + "] cannot be added to the tree!");
		else {
			this.setPosition( (PseudoBinaryNode) newChild); // also here because calls base node add method
			// do not call super.add(position, newChild); it must use the addChildren function defined here
			if( position < this.getChildCount()){
				// replace the position on a new copied children array
				List< PseudoBinaryNode> newChildren = new ArrayList<>();
				for( int i = 0; i < this.getChildCount(); i++){
					if( i == position)
						newChildren.add( (PseudoBinaryNode) newChild);
					newChildren.add( this.getChild( i));
				}
				// remove all old children
				this.removeAllChildren();
				// add new ordered children
				for( PseudoBinaryNode en : newChildren)
					this.addChild( en);
			} else ParserLog.error( "Cannot add node " + newChild + " at the position " + position + " of the children array (size" + this.getChildrenArray().size() + "): " + this.getChildrenArray());
		}
	}
	
	@Override
	public void remove(MutableTreeNode aChild) {
		if( aChild instanceof PseudoBinaryNode)
			( ( PseudoBinaryNode) aChild).resetPosition();
		super.remove(aChild);
	}
	@Override
	public void remove(int childIndex) {
		this.getChild(childIndex).resetPosition();
		super.remove( childIndex);
	}
	@Override
	public void removeFromParent() {
		this.resetPosition();
		super.removeFromParent();
	}
	@Override
	public void removeAllChildren() {
		for( int i = 0; i < this.getChildCount(); i++)
			this.getChild( i).resetPosition();
		super.removeAllChildren();
	}
		
	public void replaceLeftChild( PseudoBinaryNode newChild){
		if( this.getChildCount() == 1)
			this.addChild( newChild);
		else this.add( 0, newChild);
	}
	public void replaceRightChild( PseudoBinaryNode newChild){
		if( this.getChildCount() == 0){
			this.addChild( null); // empty left node
			this.addChild( newChild);
		}else if( this.getChildCount() == 1)
			this.addChild( newChild);
		else
			this.add( 1, newChild);
	}
	
	/* ##################################################################################
	   ########################## BASE NODE INTERFACE ###################################
	   */
	@Override
	public String getDebuggingId() {
		String positionStr = "";
		if( this.isLeft() == null)
			positionStr = "?";
		else if( this.isLeft())
			positionStr = "L";
		else positionStr = "R";
		return super.getDebuggingId() + " (" + positionStr + ")";
	}
	@Override
	public String toTreeString(){ 
		StringBinaryWalker wk = new StringBinaryWalker( this);
		wk.walkLeftFirst();
		return wk.getTreeToString();
	}
	class StringBinaryWalker extends PseudoBinaryWalker{
		// field
		private String tree2str = "";
		// constructor
		public StringBinaryWalker(PseudoBinaryNode root) {
			super(root);
		}
		// work interface
		@Override
		public Boolean enterNode(BaseNode node) {
			tree2str += "{";
			tree2str += node.toString();
			return true;
		}
		@Override
		public void exitNode(BaseNode node) {
			tree2str += "}";
		}
		// getter
		public String getTreeToString(){
			return tree2str;
		}
	}
	
	/* 	##################################################################################
   		########################### IMPOSE ORDERING COPY #################################
   	*/	
	// copy tree walking though (left first), if updateCopyId it changes the copyID
	@Override
	public PseudoBinaryNode copyTree(){
		return copyTree( false);
	}
	@Override
	public PseudoBinaryNode copyTree( Boolean updateCopyId){
		CopyBinaryWalker wk = new CopyBinaryWalker( this, updateCopyId);
		wk.walkLeftFirst();
		return wk.getNewRoot();
	}
	@Override
	public PseudoBinaryNode copyTreeWithNewID( Map< Long, List<String>> tagMap) {
		return copyTreeWithNewID( tagMap, false);
	}
	public PseudoBinaryNode copyTreeWithNewID( Map< Long, List<String>> tagMap, Boolean resetOriginalId) {
		CopyBinaryWalker wk = new CopyBinaryWalker( this, false, tagMap, resetOriginalId);
		wk.walkLeftFirst();
		return wk.getNewRoot();
	}
	public class CopyBinaryWalker extends CopyWalker{
		// fields
		private Boolean useLeft;
		// default constructors
		protected CopyBinaryWalker(PseudoBinaryNode root, Boolean updateCopyId, Map<Long, List<String>> tags, Boolean resetOriginalId) {
			super(root, updateCopyId, tags, resetOriginalId);
		}
		protected CopyBinaryWalker(PseudoBinaryNode root, Boolean updateCopyId) {
			super(root, updateCopyId);
		}
		protected CopyBinaryWalker(PseudoBinaryNode root, Boolean updateCopyId, Map<Long, List<String>> tags, Boolean resetOriginalId, Boolean useLeft) {
			super(root, updateCopyId, tags, resetOriginalId);
			this.useLeft = useLeft;
		}
		protected CopyBinaryWalker(PseudoBinaryNode root, Boolean updateCopyId, Boolean useLeft) {
			super(root, updateCopyId);
			this.useLeft = useLeft;
		}
		// getter & setter
		public void setPolicy( Boolean useLeft){ // true for left, false for right
			this.useLeft = useLeft;
		}
		public Boolean getPolicy(){ // true for left, false for right
			return this.useLeft;
		}
		@Override
		public PseudoBinaryNode getNewRoot(){
			return ( PseudoBinaryNode) super.getNewRoot();
		}
		// running procedures
		public void walkLeftFirst(){
			this.useLeft = true; // initialise it before to call walk()
			traversalTime = log( "starting left first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		public void walkRightFirst(){
			this.useLeft = false; // initialise it before to call walk()
			traversalTime = log( "starting right first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		public void walkPseudoBynary( Boolean useLeft){
			this.useLeft = useLeft; // initialise it before to call walk()
			traversalTime = log( "starting right first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		@Override
		public void walkDepthFirst(){
			this.useLeft = null; // use default walker
			traversalTime = log( "starting depth first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		/////////// walker interface
		@Override
		public PseudoBinaryNode getRoot(){
			return (PseudoBinaryNode) super.getRoot();
		}
		// update copy walker to assure left/right priority
		@Override
		protected void walkForAll( BaseNode node){ // true: left first. false right first
			if( node instanceof PseudoBinaryNode){
				PseudoBinaryNode binaryNode = ( PseudoBinaryNode) node;
				if( useLeft == null){
					super.walkForAll( binaryNode); // default walker
				} else if( useLeft){
					// iterate over all the children (left first) (search for terms first)
					if( binaryNode.hasLeftChild())
						this.walk( binaryNode.getLeftChild());
					if( binaryNode.hasRightChild())
						this.walk( binaryNode.getRightChild());
				} else { // right first
					if( binaryNode.hasRightChild())
						this.walk( binaryNode.getRightChild());
					if( binaryNode.hasLeftChild())
						this.walk( binaryNode.getLeftChild());
				}
			} else {
				super.walkForAll( node);
			}
		}
	}
		
	@Override
	public Map<Long, BaseNode> toMap() {
		return this.toMap( false);
	}
	@Override
	public Map<Long, BaseNode> toMap(Boolean useCopyId) {
		MapBinaryCreator wk = new MapBinaryCreator( this, useCopyId, false);
		wk.walkLeftFirst();
		return wk.getMap();
	}
	public Map<Long, BaseNode> toTermMap() {
		return this.toTermMap( false);
	}
	public Map<Long, BaseNode> toTermMap(Boolean useCopyId) {
		MapBinaryCreator wk = new MapBinaryCreator( this, useCopyId, true);
		wk.walkLeftFirst();
		return wk.getMap();
	}
	class MapBinaryCreator extends PseudoBinaryWalker{
		//////// fields
		private Boolean useCopyId, onlyTerms; // parameter
		private Map<Long, BaseNode> map = new LinkedHashMap<>(); // output
		/////// default constructor
		public MapBinaryCreator(PseudoBinaryNode root, Boolean useCopyId, Boolean onlyTerms) {
			super(root);
			this.useCopyId = useCopyId;
			this.onlyTerms = onlyTerms;
		}
		////// walker implementation
		@Override
		public Boolean enterNode(BaseNode node) {
			return true; // go up to the end
		}
		@Override
		public void exitNode(BaseNode node) {
			if( onlyTerms){ 
				if( node.isLeaf())
					addToMap( node);
			} else addToMap( node);
		}
		private void addToMap(BaseNode node){
			if( useCopyId)
				map.put( node.getCopyId(), node);
			else map.put( node.getId(), node);
		}
		/////// getter
		public Map< Long, BaseNode> getMap(){
			return map;
		}
		
	}
	
	/* 	##################################################################################
	   	############################### UTILITY CLASS ####################################
	   	given a vector of leaf node and a vector of nodes, it creates
		a left binary Expression Tree with the specified leafs and all operation node of the same type.
		Given the size of the input vector leafs (called order) it returns the first element if it is 1.
		An optional representation if 0 (used only by OR_OPERATOR see other method (polymorphism)).
		otherwise it returns the root tho the binary tree created
		NOTE: leafs may be also sub-trees!	*/	
	public abstract class PseudoBinaryTreeGenerator< N extends PseudoBinaryNode>{
		//////// CONSTANTS
		public final Boolean AND_OPERATOR = true;
		public final Boolean OR_OPERATOR = false;
		public final Boolean LEFT_PRIORIITY = true; // add first leaf and than operator (right deep)
		public final Boolean RIGHT_PRIORIITY = false; // add first operator and than leaf (left deep)
		public final Boolean DEFAULT_OPERATOR = AND_OPERATOR; 
		public final Boolean DEFAULT_PRIORITY = LEFT_PRIORIITY; // is not the algebriac priority but sequential (temporal) priority
		
		/////// FIELDS
		private List< N> leafNodes; // or sub rules to be added in the generated tree
		private List< N> operationNodes; // the operator node to be added in the gerenated tree
		private Integer order; // of the tree to be generated. If: order=0 => (leafs=[:],operators=[opt]); order=1 => (leafs=[:],operators=[]); order>1 => (leafs=[:1,:2,...:k],operators=[:1,:2,...:k-1]);
		private Boolean useLeftPriority; // specify if generated tree with right or left depth 
		
		/////// CONSTRUCOR
		// if order = 0; dim leaf = 1 & dim operator = 0;
		// if order > 1; dim leaf = order & dim operator = order - 1 where order = max - min
		public PseudoBinaryTreeGenerator( List< N> leafs, List< N> operations, int order, Boolean priorityLeft){
			// initialise generic field (no error or warking mangments)
			this.leafNodes = leafs;
			this.order = order;
			this.operationNodes = operations;
			this.useLeftPriority = priorityLeft;
		}
		// automatically create oretaionNodes use AND (true) or OR (false) operator
		// if order is equal to 1 operator is empty
		public PseudoBinaryTreeGenerator( List< N> leafs){
			initialise( leafs, DEFAULT_OPERATOR, DEFAULT_PRIORITY);
		}
		public PseudoBinaryTreeGenerator( List< N> leafs, Boolean useAndOperator, Boolean priorityLeft){
			initialise( leafs, useAndOperator, priorityLeft);
		}
		// common initialiser for order > 0
		private void initialise(  List< N> leafs, Boolean useAndOperator, Boolean priorityLeft){
			// initialise configuration fields
			this.order = leafs.size();
			this.useLeftPriority = priorityLeft;
			// initialise leaf fields 
			this.leafNodes = leafs;
			// create operation node list
			operationNodes = new ArrayList< N>();
			for( int i = 0; i < order - 1; i++)
				operationNodes.add( getOperator( useAndOperator)); // if order == 1 => operator=[]
		}
		// used only if order=0; dim leafs = 1 & dim operatpr = 1
		// in this case the operationNodes contains only on Optional node
		public PseudoBinaryTreeGenerator( N leafs){
			initialise( leafs, DEFAULT_OPERATOR, DEFAULT_PRIORITY);
		}
		public PseudoBinaryTreeGenerator( N leafs, Boolean useAndOperator, Boolean priorityLeft){
			initialise( leafs, useAndOperator, priorityLeft);
		}
		// common initialiser for order > 0
		private void initialise( N leafs, Boolean useAndOperator, Boolean priorityLeft){
			// initialise leaf fields
			leafNodes = new ArrayList< N>();
			leafNodes.add( leafs);
			// initialise operation fields
			operationNodes = new ArrayList< N>();
			operationNodes.add( getNewOptionalNode());
			// initialise configuration fields
			this.order = 0;
			this.useLeftPriority = priorityLeft;
		}
		
		/////// ABSTRACT
		// all those should call and return "new"
		protected abstract N getNewAndNode();
		protected abstract N getNewOrNode();
		protected abstract N getNewOptionalNode();
		
		/////// PRIVATE METHOD USED ON CONSTRUCOR
		// easy way to get operator. Set to true to use AND or to false to use OR
		private N getOperator( Boolean useAndOperator){
			if( useAndOperator) 
				return getNewAndNode();  
			else return getNewOrNode(); 
		}
		
		//////// CREATE TREE
		public N buildTree(){
			// if order is 1 returns just the given leaf. (order = 0 managed through polymorphism)
			if( getOrder() == 1)
				return getLeafNode( 0);
			// otherwise create binary tree of the specified order
			N root = getOperatorNode( 0);//getOperator( useAndOperator); // initialise the root to be returned
			N actualNode = root; // initialise the current node used for binary tree creation
			if( getOrder() == 0){
				actualNode.addChild( getLeafNode( 0)); // add the child of optional node (only one child no left or right)
			} else { // order > 1
				for( int k = 0; k < getOrder() - 1; k++) // for the number of operation node to be created - 1 (since root is already an operator).
					if( this.useLeftPriority)
						actualNode = buildLeftTree( actualNode, k); // add first leaf and than operator
					else actualNode = buildRightTree( actualNode, k); // add first operator and than leaf
				actualNode.addChild( getLeafNode( getOrder() - 1)); // add the last right leaf
			}
			return root; // returns the root of the created binary tree,
		}
		private N buildLeftTree( N actualNode, int k){
			// add a left leaf 
			actualNode.addChild( getLeafNode( k)); 
			// add operator an eventual right operator
			actualNode = (N) addOperator( actualNode, k);
			return actualNode;
		}
		private N buildRightTree( N actualNode, int k){
			// add operator an eventual left operator
			N eventualOperator = addOperator( actualNode, k);
			// add a right leaf 
			actualNode.addChild( getLeafNode( k)); 
			return eventualOperator; // set the new operator as the current node used for binary tree creation
		}
		private N addOperator( N actualNode, int k){
			if( k < getOrder() - 2){ // if this iteration is not the last of the for loop 
				N eventualOperator = (N) getOperatorNode( k + 1); // the root operator is already used
				actualNode.addChild( eventualOperator); // add a new left operator
				return (N) eventualOperator;
			}
			return actualNode;
		}
		
		//////// GETTERS
		public Integer getOrder(){
			return this.order;
		}
		public Boolean hasLeftPriotiyu(){
			return this.useLeftPriority;
		}
		
		public List< N> getLeafNodes(){
			return this.leafNodes;
		}
		public N getLeafNode( int orderIdx){
			return this.leafNodes.get( orderIdx);
		}
		
		public List< N> getOperatorNodes(){
			return this.operationNodes;
		}
		public N getOperatorNode( int orderIdx){
			return this.operationNodes.get( orderIdx);
		}
	}
	
	/* ##################################################################################
	   ########################## UTILITY CLASS #########################################
	   to do a work while traverse the tree analyzer */
	public abstract class PseudoBinaryWalker extends TreeWalker{
		//////////// private states fields
		private Boolean useLeft;
		//////////// default constructor
		public PseudoBinaryWalker( PseudoBinaryNode root){
			super( root, DEFAULT_VERBOSE, DEFAULT_WALKER_NAME);
		}
		public PseudoBinaryWalker( PseudoBinaryNode root, Boolean verbose){
			super( root, verbose, DEFAULT_WALKER_NAME);
		}
		public PseudoBinaryWalker( PseudoBinaryNode root, Boolean verbose, String walkerName){
			super( root, verbose, walkerName);
		}
		//////////// running procedures
		public void walkLeftFirst(){
			this.useLeft = true; // initialise it before to call walk()
			traversalTime = log( "starting left first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		public void walkRightFirst(){
			this.useLeft = false; // initialise it before to call walk()
			traversalTime = log( "starting right first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		public void walkPseudoBynary( Boolean useLeft){
			this.useLeft = useLeft; // initialise it before to call walk()
			traversalTime = log( "starting right first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		@Override
		public void walkDepthFirst(){
			this.useLeft = null; // use default walker
			traversalTime = log( "starting depth first wolk on " + getRoot() + " ...");
			walk( this.getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		/////////// walker interface
		@Override
		public PseudoBinaryNode getRoot(){
			return (PseudoBinaryNode) super.getRoot();
		}
		////////// traversal general procedures
		@Override
		protected void walkForAll( BaseNode node){ // true: left first. false right first
			if( node instanceof PseudoBinaryNode){
				PseudoBinaryNode binaryNode = ( PseudoBinaryNode) node;
				if( useLeft == null){
					super.walkForAll( binaryNode); // default walker
				} else if( useLeft){
					// iterate over all the children (left first) (search for terms first)
					if( binaryNode.hasLeftChild())
						this.walk( binaryNode.getLeftChild());
					if( binaryNode.hasRightChild())
						this.walk( binaryNode.getRightChild());
				} else { // right first
					if( binaryNode.hasRightChild())
						this.walk( binaryNode.getRightChild());
					if( binaryNode.hasLeftChild())
						this.walk( binaryNode.getLeftChild());
				}
			} else {
				super.walkForAll( node);
			}
		}
		//////////// abstract (propagated)
		@Override
		public abstract Boolean enterNode(BaseNode node);
		@Override
		public abstract void exitNode(BaseNode node);
	}
}

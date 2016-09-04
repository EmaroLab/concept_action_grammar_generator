package it.emarolab.cagg.core;

import it.emarolab.cagg.core.evaluation.CaggThread;
import it.emarolab.cagg.core.language.ParserLog;
import it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode;
import it.emarolab.cagg.debugging.DebuggingDefaults;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;


// TODO : UPDATE DOCUMENTATION

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.core.sourceInterface.BaseNode.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This is the base class for all the nodes in all the trees of the system
 * in order to assure a unique identifier along all the structure 
 * (where, the ID is implemented as an incremental {@code Long} number.<br>
 * 
 * Each nodes are described in terms of {@code Type T}, {@code Data D}.
 * Where, {@code T} is usually an enumerator for easy node type definition, 
 * while {@code D} is a generic Object templated in the system classes structures
 * for visualisation as well as maintenance purposes.<br> 
 * 
 * At the same time, this class implements the procedures to print iteratively the tree
 * and to copy it with the possibility to have different node ID.<br>
 *
 * Last but not less important, the main classes that implements this object are:
 * {@link it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode} and
 * {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld}
 *
 * </p>
 * 
 * @param <T> the node Type.
 * @param <D> the node Data.
 * 
 * @see AbstractSyntaxNode.Type
 * @see Type.ExpressionNodeTypeEnum
 * @see it.emarolab.cagg.core.language.BaseData
 * @see it.emarolab.cagg.core.language.BaseDoubleData
 * @see it.emarolab.cagg.core.language.syntax.abstractTree.AbstractSyntaxNode
 * @see it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld
 */	
@SuppressWarnings("serial")
public abstract class BaseNode extends DefaultMutableTreeNode {

	// TODO : add/use walker parametrisable class 

	// ####################################################  CONSTANTS (STATIC)
	public static final String DEFAULT_PREFIX_ID = "m"; // for debug purposes on printing

	// ####################################################  CLASS FOR NODE REFERENCING and ID MANAGMENT (STATIC)
	public static class BaseNodeCollector{
		// ####################################################  PRIVATE METHODs TO MANAGE SERIAL ID
		private static Long serialId = 0L; // static ID counter for all the node of this system
		// create a new serial id for this node. Returns -1 if long overflow happen 
		private static Long getNewId(){
			if( serialId++ > Long.MAX_VALUE){
				ParserLog.error( "Out of indexing memory, too much nodes! (Base Node Id overflow: " + serialId + ").");
				return -1l;
			}
			return serialId++;
		}		

		protected static void initialiseId( BaseNode node){ // used on constructor (not for copies)
			initialiseId( null, node, false); // assigned an ID (and not a copyId) to this node
		}
		protected static void initialiseId( BaseNode node, Boolean withCopyId){ // used on construcit (false) or in composition (true)
			initialiseId( null, node, withCopyId); // assign an ID (and possibly a copyId) to this node
		}
		protected static void initialiseId( BaseNode copy, BaseNode node, Boolean createCopyId){ // used on copy (createCopyId:true => new copy ID, fasle => old copyId)
			if( copy == null) // is not a copy (condition of the above methods)
				node.setId( getNewId()); // create a new ID
			else{
				copy.setId( node.getId());
				if( createCopyId)
					copy.setCopyId( getNewId());
				else copy.setCopyId( node.getCopyId()); // null if it is not a copy
			}
		}
	}

	// ####################################################  MAIN FIELDS
	transient private Long originalId; // the id given during parsing (do not serialised. All the information should be stored back w.r.t. id after post-processing)
	private Long id; // this node id (it may change during grammar generation)
	private Long copyId = null; // it is different from 0 if the tree does not comes from a copy. Otherwise it contains a unique id
	private boolean isACopy = false; // true->is a copy. false->is not
	private String prefixId; // for debugging purposes (indicate if it a copy and the meaning of it printed id)

	// ####################################################  CONSTRUCTORS
	/**
	 * Create a new tree node. <br>
	 * It does not call {@link #assign()} so, it does not instantiate the {@code type} and
	 * {@code data} fields. That is why the non abstract implementation of this class
	 * must implement the abstract interfaces: {@link #assignData()} and {@link #assignType()}.
	 * Moreover, in order to fully initialise the node the non abstract implementation should call {@link #assign()} 
	 * in the constructor (considering also possible node coping).<br> 
	 * It also initialises the node ID and sets {@link #DEFAULT_PREFIX_ID} for printing it on debugging. 
	 */
	public BaseNode(){
		this.initialise( DEFAULT_PREFIX_ID);
	}
	/**
	 * Create a new tree node.
	 * It does not call {@link #assign()} so, it does not instantiate the {@code type} and
	 * {@code data} fields. That is why the non abstract implementation of this class
	 * must implement the abstract interfaces: {@link #assignData()} and {@link #assignType()}.
	 * Moreover, in order to fully initialise the node the non abstract implementation should call {@link #assign()} 
	 * in the constructor (considering also possible node coping).<br> 
	 * It also initialises the node ID and sets the input parameter as {@code prefixId} for printing it on debugging. 
	 * 
	 * @param prefixId the prefix to be visualised for node ID debugging.
	 */
	public BaseNode( String prefixId){
		if( prefixId != null)
			this.initialise( prefixId);
		else this.prefixId = DEFAULT_PREFIX_ID;
	}
	// common initialiser for all the constructors (it manages node id generation and toString() formatting)
	private void initialise( String prefixId){
		this.prefixId = prefixId;			
		BaseNodeCollector.initialiseId( this);
		this.originalId = this.getId();
	}

	// ####################################################  ABSTRACT
	/** 
	 * This method should be implemented in the extending class in order to have a easy debugging information,
	 * used also in the GUI.
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 **/
	@Override
	abstract public String toString();	// it must be implemented somewhere for GUI visualisation
	/**
	 * This method is used to delegate as far as is needed the creation of a new node that should be also
	 * copied with the same contents of this object by calling: {@link #copyContents(BaseNode)}.<br>
	 * @return a new node instance that can contains the same contents and children of this node.
	 */
	abstract protected BaseNode copy( Boolean createNewCopyID); 

	// ####################################################  GETTER AND SETTERS
	public Long getOriginalId(){
		return this.originalId;
	}
	protected void setOriginalId( Long originalId) {
		this.originalId = originalId;
	}
	// set the id of this done (use it carefully!!!!) just used on node copyContents()	
	protected void setId(Long id) { // you should not call this (id are internally managed)
		this.id = id;
	}
	/**
	 * This method returns the unique ID assign to this node on constructor. 
	 * This value does not change when node is copied.
	 * @return the serial ID of this node
	 */
	public Long getId() {
		return id;
	}
	@Override
	public BaseNode getParent(){
		return ( BaseNode) super.getParent();
	}
	
	// set the copy to the given value (you must assure by yourself that the id is unique)  
	private void setCopyId( Long id){
		this.copyId = id;
	}
	/**
	 * This method returns the copyID assign to this node on constructor. 
	 * This value does may not change when node is copied and it is {@code null} when the node is not copied.
	 * @return the copy id of this node. It can be null if this node is not a copy, or if it has been copied 
	 * using {@link #copyTree(Boolean)} with {@code false} input parameter and it was not a copy before.
	 * @see #isCopy()
	 */
	public Long getCopyId(){
		return copyId;
	}
	/**
	 * @return {@code true} if the contents of this node as been filled by calling {@link #copyContents(BaseNode)}.
	 */
	public Boolean isCopy(){
		return isACopy;
	}


	/**
	 * @param prefixId a prefix to be set. Used in {@link #getDebuggingId()}.
	 */
	public void setPrefixId(String prefixId) {
		this.prefixId = prefixId;
	}
	/**
	 * @return the prefixId assigned to this object
	 */
	public String getPrefixId() {
		return this.prefixId;
	}	
	/**
	 * @return the the node ID in human readable form. The returned value is {@code prefixId + I}.
	 * Where {@code I = this.getId()} if the node is not a copy or, 
	 * {@code I = this.getCopyId + "-ex:" + this.getId()} otherwise. 
	 */
	public String getDebuggingId() {
		String idStr =  this.getId().toString();
		if( this.getCopyId() != null) 
			idStr = this.getCopyId() + "-ex:" + idStr; // suppress error message if not copied
		return prefixId + idStr;
	}

	/**
	 * @return all the children of this node as an array
	 */
	public List< BaseNode> getChildrenArray(){
		List< BaseNode> childrens = new ArrayList< BaseNode>();
		for( int i = 0; i < this.getChildCount(); i++)
			childrens.add( this.getChild( i));
		return childrens;
	}

	/**
	 * This method add a child to the indicated position in the children array.
	 * It cannot create new position on the children array so
	 * it logs an error if {@code position < this.getChildCount()}.
	 * It would replace the old child at that position.
	 * @param position the position of the new child in the children array of this node
	 * @param newChild the new child to be set at the given position.
	 */
	public void add( int position, BaseNode newChild){
		if( position < this.getChildCount()){
			// replace the position on a new copied children array
			List< BaseNode> newChildren = new ArrayList< BaseNode>();
			for( int i = 0; i < this.getChildCount(); i++){
				if( i == position)
					newChildren.add( newChild);
				newChildren.add( this.getChild( i));
			}
			// remove all old children
			this.removeAllChildren();
			// add new ordered children
			for( BaseNode en : newChildren)
				this.add( en);
		} else ParserLog.error( "Cannot add node " + newChild + " at the position " + position + " of the children array (size" + this.getChildrenArray().size() + "): " + this.getChildrenArray());
	}

	/**
	 * returns the child of a node by index.
	 * It overlay the function {@link javax.swing.tree.DefaultMutableTreeNode#getChildAt(int)} 
	 * by returning a {@code BaseNode< T, D>} object.
	 * @param index the number of the child to be retrieved
	 * @return a node which is the child of this node by index.
	 */
	public BaseNode getChild( int index) { // not override getChildAt(int) !!!!
		return ( BaseNode) this.getChildAt(index);
	}	

	/**
	 * This method can be used from in order to add a node to this tree by calling {@link #add(MutableTreeNode)} 
	 * and by giving the possibility to show or suppress warning messages.
	 * Specially, in case in which the implementing tree controls the number of children.<br>
	 * Node that the extending tree that want to log messages on its {@link #add(MutableTreeNode)} method must
	 * override this function (as for example happen in: {@link it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNodeOld}).
	 * @param newChild the new node to be added as a child to this parent.
	 * @param suppressWarning {@code true} to suppress or {@code false} to show warning 
	 * messages if the tree controls the number of children.
	 */
	protected void add(MutableTreeNode newChild, Boolean suppressWarning) {
		this.add( newChild);
	}

	// ####################################################  ITERATIVELY MANAGEMENT OF THIS NODE (ROOT) TREE
	// string to contain the tree description
	/**
	 * Prints on the {@code System.out} stream the results of {@link #toTreeString()}.
	 */
	public void printTree(){
		ParserLog.info( toTreeString());
	}
	/**
	 * @return the tree described as a string starting from this node as the root.
	 */
	public String toTreeString(){
		StringWalker wk = new StringWalker( this);
		wk.walkDepthFirst();
		return wk.getTreeToString();
	}
	class StringWalker extends TreeWalker{
		// field
		private String tree2str = "";
		// constructor
		public StringWalker(BaseNode root) {
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

	/**
	 * This method copy the contents of this node in the node given as input parameters.
	 * Particularly, this implementation set this node to be a copy
	 * and it also transfers to the new node the actual values of: 
	 * {@code prefixId} (by appending another prefix {@literal "cp-"}), {@code type}, {@code data} and {@code ID}.
	 * @param n the node in which the contents of this node should be copied.
	 * @return the input {@code n} node with the contents of this object copied.
	 */
	public BaseNode copyContents( BaseNode newNode, Boolean generateCopyId) {
		newNode.setPrefixId( this.getPrefixId());
		newNode.isACopy = true;
		BaseNodeCollector.initialiseId( newNode, this, generateCopyId);
		return newNode;
	}	
	/**
	 * Copy iteratively the tree considering this node as the root. This method
	 * maintains both the original ID ({@link #getId()}) and copyID ({@link #getCopyId()}).
	 * Be careful since the copyId is null if this node is not a copy. 
	 * @return the result of {@link #copyTree(Boolean)} where the input parameter is {@code false}.
	 */
	public BaseNode copyTree(){ 
		return copyTree( false);
	}
	/**
	 * Copy iteratively the tree considering this node as the root. This method
	 * maintains the original ID ({@link #getId()}) and the input parameter specifies if
	 * the copyID should be maintained or created ({@link #getCopyId()}).
	 * Be careful since the copyId is null if this node is not a copy. 
	 * @param createNewCopyId set to {@code true} if the new copied nodes should have a unique copyId. 
	 * Set to {@code false} if the new copied nodes should maintain the original copyId.
	 * @return A new root node contains a copy of all the tree starting from this node as a root. 
	 */
	public BaseNode copyTree( final Boolean updateCopyId){
		CopyWalker wk = new CopyWalker( this, updateCopyId);
		wk.walkDepthFirst();
		return wk.getNewRoot();
	}
	public BaseNode copyTreeWithNewID( Map<Long, List<String>> tagMap) { // update tag map if it is not null
		CopyWalker wk = new CopyWalker( this, false, tagMap);
		wk.walkDepthFirst();
		return wk.getNewRoot();
	}
	class CopyWalker extends TreeWalker{
		////// field
		private Map<Long, List< String>> tagMap; // if null do not update main id 
		private Boolean updateCopyId, resetOriginalId; // if true generate new copyId
		private BaseNode newRoot; // the root of the copied tree
		private Stack<BaseNode> stack = new Stack<>();
		////// constructor
		public CopyWalker( BaseNode root, Boolean updateCopyId){
			super( root);
			initialise( root, updateCopyId, null, false);
		}
		// get a tags map (not null) to update ID !!!!
		public CopyWalker( BaseNode root, Boolean updateCopyId, Map<Long, List< String>> tags){
			super( root);
			initialise( root, updateCopyId, tags, false);
		}
		public CopyWalker( BaseNode root, Boolean updateCopyId, Map<Long, List< String>> tags, Boolean resetOriginalId){
			super( root);
			initialise( root, updateCopyId, tags, resetOriginalId);
		}
		private void initialise(BaseNode root, Boolean updateCopyId, Map<Long, List<String>> tags, Boolean resetOriginalId){
			this.tagMap = tags;
			this.updateCopyId = updateCopyId;
			this.resetOriginalId = resetOriginalId;
		}
		@Override
		public Boolean enterNode(BaseNode node) {
			// copy node
			BaseNode copy = node.copy( updateCopyId);
			// update base id
			if( tagMap != null){
				changeId( copy);
				if( ! resetOriginalId)
					copy.originalId  = node.getOriginalId();
			}
			// add this node to the stack (used by exit method)
			stack.push( copy);
			return true;
		}
		protected void changeId(BaseNode copy){
			Long oldId = copy.getId();
			List<String> thisTags = tagMap.get( oldId);
			copy.setId( BaseNodeCollector.getNewId());
			copy.isACopy = true;
			copy.setCopyId( null);
			if( thisTags != null){
				tagMap.remove( oldId);
				tagMap.put( copy.getId(), thisTags);
			}
		}
		@Override
		public void exitNode(BaseNode node) {
			// take the last node from the stack
			BaseNode stacked = stack.pop();
			// add it has a child of the previous node in the stack
			if( ! stack.isEmpty())
				stack.peek().add( stacked, true);
			else // if it is empty is the end of the search (depth first)
				newRoot = stacked;
			log( " copy walking procedure state. {newRoot=" + newRoot + "} stack:" + stack);
		}
		////// getters & setters
		public BaseNode getNewRoot(){
			return newRoot;
		}
		public Map<Long, List<String>> getTagMap() {
			return tagMap;
		}
		public Boolean getUpdateCopyId() {
			return updateCopyId;
		}
		protected Stack<BaseNode> getStack() {
			return stack;
		}
	}
	
	// map to collect all the tree starting from this node as root
	/**
	 * This method returns a map relating all the nodes (starting from this object as root) of the tree
	 * with respect to their ID.
	 * @return the result of {@link #toMap(Boolean)} whit {@code false} as input parameter.
	 */
	public Map< Long,  BaseNode> toMap(){
		return toMap( false);
	}
	/**
	 * This method returns a map relating all the nodes (starting from this object as root) of the tree
	 * with respect to their copyID if input parameter is {@code true}; with respect to their ID otherwise.
	 * @param copyId set to {@code true} if the node should be map with respect to copyId value. Set to {@code false}
	 * if the node should be map with respect to ID value.
	 * @return a map relating all the node of the tree (starting from this object as root) identified through
	 * their copyId (if input parameter is {@code true}) or through their ID (if {@code true}).
	 */
	public Map< Long,  BaseNode> toMap( Boolean useCopyId){
		ToMapVisitor wk = new ToMapVisitor( this, useCopyId);
		wk.traverseBreadthFirst();
		return wk.getMap();
	}
	class ToMapVisitor extends TreeVisitor{
		// fields
		private Boolean useCopyId;
		private Map< Long, BaseNode> nodeMap = new LinkedHashMap<>();
		// constructor
		private ToMapVisitor(BaseNode root, Boolean useCopyId) {
			super(root);
			this.useCopyId = useCopyId;
		}
		// visitor work for node
		@Override
		public Boolean work(BaseNode node) {
			if( useCopyId)
				nodeMap.put( node.copyId, node); // put the node in the map with respect to the copyId
			else nodeMap.put( node.id, node);	// put the node in the map with respect to the ID
			return true; // continue up to the end
		}
		// getter
		public Map< Long, BaseNode> getMap(){
			return nodeMap;
		}
	}


	// ########################################################################################################################
	//    VISUALIZATION   #####################################################################################################
	/**
	 * Visualise a Tree (with this node as a root) through a dedicated Graphical User Interface.
	 * More in details, this basic GUI is implemented by: {@link BaseNode.TreeVisualizer}.
	 * By default the title of the visualising frame would be empty.
	 */
	public void showTree(){
		showTree( "");
	}
	/**
	 * Visualise a Tree (with this node as a root) through a dedicated Graphical User Interface.
	 * More in details, this basic GUI is implemented by: {@link BaseNode.TreeVisualizer}.
	 * @param frameTitle the name of the main windows in which the tree is visualised.
	 */
	public void showTree( final String frameTitle){
		final BaseNode tree = this;
		EventQueue.invokeLater(new CaggThread() {
			public void implement() {
				try {
					TreeVisualizer frame = new TreeVisualizer( frameTitle, tree);
					frame.setVisible(true);
				} catch (Exception e) {
					ParserLog.error( e);
				}
			}
		});
	}
	// simple visualisation of the parsing tree as a new independent frame
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.core.sourceInterface.BaseNode.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * Jan 26, 2016 <br>
	 *  
	 * <p>
	 * This class implements a basic GUI to visualise a Tree
	 * in a dedicated window. 
	 * </p>
	 *
	 * @see BaseNode
	 */
	private class TreeVisualizer extends JFrame{
		/**
		 * This constructor fully initialise all the visual components of this class. 
		 * @param title the title of the main window frame.
		 * @param tree the object containing the tree to be visualised.
		 */
		public TreeVisualizer( String title, BaseNode tree){
			// set frame
			setBounds(100, 100, 450, 300);
			setTitle( title);
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			// show tree
			if( tree != null){
				// create visualisation tree and show on windows
				JScrollPane scroll = new JScrollPane( new JTree(tree));
				contentPane.add(scroll);
			} else ParserLog.error( "BASE. Cannot visualize parsing tree (null tree) !!!");
		}
	}

	/* ##################################################################################
	   ######################### UTILITY CLASSES ########################################
	   to do a work while traverse the tree analyzer */
	//////////constants
	public static Boolean DEFAULT_VERBOSE = false;
	public static Boolean LOG_TOTAL_TIME_ANYTWAY = false;
	public static String DEFAULT_VISITOR_NAME = "visitor";
	public static String DEFAULT_WALKER_NAME = "walker";
	protected abstract class TreeVisitor{
		////////// field
		private BaseNode root;
		private Boolean verbose = DEFAULT_VERBOSE;
		protected String walkerName = DEFAULT_VISITOR_NAME;
		protected Long traversalTime;
		////////// constructor
		public TreeVisitor( BaseNode root){
			initialise( root, DEFAULT_VERBOSE, DEFAULT_VISITOR_NAME);
		}
		public TreeVisitor( BaseNode root, Boolean verbose){
			initialise( root, verbose, DEFAULT_VISITOR_NAME);
		}
		public TreeVisitor( BaseNode root, Boolean verbose, String walkerName){
			initialise( root, verbose, walkerName);
		}
		private void initialise(BaseNode root, Boolean verbose, String walkerName){
			this.root = root;
			this.verbose = verbose;
			this.walkerName = walkerName;
		}
		////////// running procedures
		public void traverseDepthFirst(){
			traversalTime = log( "visit deepth first on " + root + " ...");
			traverse( root.depthFirstEnumeration());
		}
		public void traverseBreadthFirst(){
			traversalTime = log( "visit breadth first on " + root + " ...");
			traverse( root.breadthFirstEnumeration());
		}
		public void traversePreorder(){
			traversalTime = log( "visit preorder first on " + root + " ...");
			traverse( root.preorderEnumeration());
		}
		public void traversePostorder(){
			traversalTime = log( "visit postorder first on " + root + " ...");
			traverse( root.postorderEnumeration());
		}
		//////////general traverse implementations
		protected void traverse( Enumeration<?> ordered){
			// walk through the enumeration
			boolean more = ordered.hasMoreElements();
			while ( more) { // iterate over the enumeration
				// get next element
				BaseNode node = ( BaseNode) ordered.nextElement();
				
				// call work() and log
				@SuppressWarnings("unused")
				Long workTime = log( node + "\t at work...");
				Boolean continues = work( node);
				//log( timeRange( exitTime));
				// check if it should stop
				if( !continues){
					// force stop and log
					log( node + "\t forced stop.");
					return;
				}
				// next enumerated value
				more = ordered.hasMoreElements();
			}
			log( "finish visiting on " + root + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		////////// abstract interface (minimal implementation, it does not do nothing. Override it fore more!!!)
		abstract public Boolean work( BaseNode node); // return continue flag (false to stop searching)
		////////// getter & setters
  		public BaseNode getRoot(){
			return this.root;
		}
  		public void setRoot( BaseNode root){
			this.root = root;
		}
  		////////// logger & configurations
  		public void setVerbose(Boolean verbose) {
			this.verbose = verbose;
		}
		public void setWalkerName(String walkerName) {
			this.walkerName = walkerName;
		}
  		protected Long log( String msg){
  			return log( msg, verbose);
  		}
		protected Long log( String msg, Boolean print){
  			if( print)  				
  				ParserLog.info( this.getClass().getSimpleName() + "\t" + walkerName + ": " + msg);
  			return System.nanoTime();
  		}
  		protected String timeRange( Long initialTime){
  			return "[" + ( Float.valueOf( System.nanoTime() - initialTime) / DebuggingDefaults.NANOSEC_2_SEC ) + "ms]";
  		}
	}
	protected abstract class TreeVisitorRecoursive<D extends Object> extends TreeVisitor{ // implements depth first
		///////// default constructors
		private TreeVisitorRecoursive(BaseNode root) {
			super(root, DEFAULT_VERBOSE, DEFAULT_VISITOR_NAME);
		}
		private TreeVisitorRecoursive(BaseNode root, Boolean verbose) {
			super(root, verbose, DEFAULT_VISITOR_NAME);
		}
		private TreeVisitorRecoursive(BaseNode root, Boolean verbose, String walkerName) {
			super(root, verbose, walkerName);
		}
		/////////// running procedures   (preorder false => leafs first)
		public void traverseRecoursive(){
			traverseRecoursive( true);
		}
		public void traverseRecoursive( Boolean preorder){
			String log = "(?)";
			if( preorder)
				log = "(preorder)";
			else log = "(postorder)";
			traversalTime = log( "starting " + log + " recorsive on " + getRoot() + " ...");
			D d = this.initialiseRecoursiveData();
			recoursiveWork( getRoot(), preorder, d);
		}
		//////////// general visiting implementation
		private void recoursiveWork( BaseNode node, Boolean preorder, D recorsiveData){ 
			// do on preorder
			if( node != null && preorder)
				if( ! doWork( node, recorsiveData))
					return;
			// recorsive visiting calls
			for( int i = 0; i < node.getChildCount(); i++){
				D d = this.getRecoursiveData();
				recoursiveWork( node.getChild(i), preorder, d);
			}
			// do on postorder
			if( node != null && !preorder)
				if( ! doWork( node, recorsiveData))
					return;
		}
		private Boolean doWork( BaseNode node, D d){
			@SuppressWarnings("unused")
			Long workTime = log( node + "\t at work...");
			Boolean continues = work( node, d);
			//log( timeRange( workTime));
			return continues;
		}
		////////// abstract interface
		@Override
		public Boolean work( BaseNode n){ return true;} // this get called only if visiting method of the TreeVisitor are called (you should not use this)
		abstract public Boolean work( BaseNode node, D data); // return continue flag (false to stop searching)
		abstract D getRecoursiveData();
		abstract D initialiseRecoursiveData();
	}
	protected abstract class TreeWalker extends TreeVisitor{
		////////// default constructor
		public TreeWalker( BaseNode root){
			super( root, DEFAULT_VERBOSE, DEFAULT_WALKER_NAME);
		}
		public TreeWalker( BaseNode root, Boolean verbose){
			super( root, verbose, DEFAULT_WALKER_NAME);
		}
		public TreeWalker( BaseNode root, Boolean verbose, String walkerName){
			super( root, verbose, walkerName);
		}
		////////// running procedures
		public void walkDepthFirst(){
			traversalTime = log( "walk deepth first on " + getRoot() + " ...");
			walk( getRoot());
			log( "finish walking though on " + getRoot() + " in " + timeRange( traversalTime), LOG_TOTAL_TIME_ANYTWAY);
		}
		////////// traversal general procedure
		@SuppressWarnings("unused")
		protected void walk( BaseNode node){
			// depth frist
			if( node != null){
				// entering node
				Long enterTime = log( node + "\t entering...");
				Boolean continues = enterNode( node);
				//log( timeRange( enterTime));
				if( continues)
					walkForAll( node);
				else log( "stop forced !!");
				// exit node
				Long exitTime = log( node + "\t exit.");
				exitNode( node);
				//log( timeRange( exitTime));
			} 
		}
		protected void walkForAll( BaseNode node){ // define the walking policy
			for( int i = 0; i < node.getChildCount(); i++)
				walk( node.getChild(i));
		}
		////////// abstract interface   (return continuous flags)
		@Override
		public Boolean work( BaseNode node){ return true;} //(minimal implementation, it does not do nothing. Override it fore more!!!)
		abstract public Boolean enterNode( BaseNode node); // it gets called only on walk() not on traversal()
		abstract public void exitNode( BaseNode node);  // it gets called only on walk() not on traversal()
	}
}

package it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler;

import it.emarolab.cagg.core.evaluation.semanticGrammar.GrammarLog;
import it.emarolab.cagg.core.language.syntax.expressionTree.ExpressionNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public abstract class ActionTagBase implements Serializable{
	
	/* 	##################################################################################
		########################## FIELDS & CONSTRUCTOR ##################################
	 */
	private Set< TagBase<?>> tagsCollector;
	// it does not do nothing, uses toSyntaxTag interface. It gets called on ExpressionNode.composeTree
	public ActionTagBase(){ 
		tagsCollector = initialise();
	}
	public ActionTagBase( Map< Long, List< String>> tagsMap, ExpressionNode<?> rootNode){
		tagsCollector = initialise();
		toSyntaxTag( tagsMap, rootNode);
	}
	public ActionTagBase( ExpressionNode<?>.ComposedRule composedExpression){
		tagsCollector = initialise();
		toSyntaxTag( composedExpression.getTagsMap(), composedExpression.getRoot());
	}
	
	/* 	##################################################################################
		########################### ABSTRACT INTERFACE ###################################
	 */
	// should call new for Set< TagBase> tagsCollector
	abstract protected Set< TagBase<?>> initialise();
	// used to instantiate this class for Map<Long,List<String>> given during parsing.
	// used on ExpressionNode composeTree(..)
	public abstract void toSyntaxTag( Map< Long, List< String>> tagsMap, ExpressionNode<?> rootNode);
	// process tag and return the value to be actually added. return NULL to not add.
	// those are given with the minimal implementation, override them for more!
	protected TagBase<?> willAdd( TagBase<?> tag) 		{		return tag;	} 
	protected TagBase<?> willRemove( TagBase<?> tag) 	{		return tag;	}
	protected void didAdd( TagBase<?> tag)				{	} 
	protected void didRemove( TagBase<?> tag)			{	}
	
	
	/* 	##################################################################################
		######################## STRUCTURE IMPLEMENTATION ################################
	 */
	// this getter should be use only for iterating ... use manipulator instead 
	public Set< TagBase<?>> getTagsCollector(){ 
		return this.tagsCollector;
	}
	// manipultaor
	public void clear(){
		this.tagsCollector.clear();
	}
	public void addAll( Collection<? extends TagBase<?>> tags){
		for( TagBase<?> t : tags)
			add( t);
	}
	public void addAll( ActionTagBase tags){
		tagsCollector.addAll( tags.tagsCollector);
	}
	public void add( TagBase<?> tags){
		TagBase<?> t = willAdd( tags);
		if( t != null)
			this.tagsCollector.add( t);
		else GrammarLog.warning( "tag: " + tags + " not added to the action tag set. (" + tagsCollector + ")");
		didAdd( t);
	}
	public void removeAll( Collection<? extends TagBase<?>> tags){
		for( TagBase<?> t : tags)
			remove( t);
	}
	public void remove( TagBase<?> tags){
		TagBase<?> t = willRemove( tags);
		if( t != null)
			this.tagsCollector.remove( t);
		else GrammarLog.warning( "tag: " + tags + " not removed from the action tag set. (" + tagsCollector + ")");
		didRemove( t);
	}
	// getters
	public int size(){
		return this.tagsCollector.size();
	}
	public Boolean isEmpty(){
		return this.tagsCollector.isEmpty();
	}
	public Boolean contains( TagBase<?> tag){
		return contains( tag.getId());
	}
	public Boolean contains( Long id){
		for( TagBase<?> t : tagsCollector)
			if( t.equals( id))
				return true;
		return false;
	}
	public TagBase<?> get( Long id){
		for( TagBase<?> t : tagsCollector)
			if( t.equals( id))
				return t;
		return null;
	}
	public String toString(){
		return this.tagsCollector.toString();
	}
	
	
	/* 	##################################################################################
		############################## UTILITY CLASSES ###################################
 	 	this class describe a generic and basic action tag data structure as a list of S 
 	 	(perhaps strings).						*/
	public class TagBase< T extends Object> implements Serializable{
		//////////  FIELDS
		private Long id;
		private List<T> tagsList = new ArrayList<>();
		//////////  CTNSTRUCTOR
		public TagBase( Long id){
			this.id = id;
		}
		public TagBase( Long id, T tag){
			this.id = id;
			add( tag);
		}
		public TagBase( Long id, List< T> tags){
			this.id = id;
			addAll( tags);
		}
		////////// MANIPULATORS
		public void clear(){
			this.tagsList.clear();
		}
		public void add( T tag){
			if( ! tagsList.contains( tag))
				this.tagsList.add( tag);
		}
		public void add( int idx, T tag){
			if( ! tagsList.contains( tag))
				this.tagsList.add( idx, tag);
		}
		public void addAll(Collection<? extends T> tags){
			for( T t : tags)
				add( t);
		}
		public void addAll( int idx, Collection<? extends T> tags){
			for( T t : tags){
				add( idx, t);
				idx += 1;
			}
		}
		public void remove( int idx){
			this.tagsList.remove( idx);
		}
		public void remove( T tag){
			this.tagsList.remove( tag);
		}
		public void removeAll(Collection<?> tags ){
			this.tagsList.removeAll( tags);
		}
		////////// GETTERS
		public Long getId(){
			return this.id;
		}
		public int size(){
			return this.tagsList.size();
		}
		@Override
		public int hashCode() {
			// force to call always equals() method
			return 1;
		}
		@Override 
		public boolean equals( Object o){
			if( o instanceof Long){
				if( this.id ==  ( Long) o)
					return true;
			}
			if( o instanceof Integer){
				if( this.id ==  Long.valueOf( (Integer) o))
					return true;
			}
			if( o instanceof TagBase){
				@SuppressWarnings("rawtypes")
				TagBase tag = ( TagBase) o;
				if( this.id == tag.id)
					return true;
			}
			return super.equals( o);
		}
		public Boolean isEmpty(){
			return this.tagsList.isEmpty();
		}
		public Boolean contains( T tag){
			return this.tagsList.contains( tag);
		}
		public T get( int idx){
			return this.tagsList.get( idx);
		}
		// less efficient than idx ?? returns null if not found
		public T get( Long id){ 
			for( T t : tagsList)
				if( t == id) // equal method overrided
					return t;
			return null;
		}
		public String toString(){
			return this.id + "->" + this.tagsList.toString();
		}
		public String toShortString(){
			return this.tagsList.toString();
		}
		public int indexOf( T tag){
			return this.tagsList.indexOf( tag);
		}
		// it should be used only for iterate
		public List<T> getTagList(){ 
			return this.tagsList;
		}
	}
}

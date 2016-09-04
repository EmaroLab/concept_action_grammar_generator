package it.emarolab.cagg.core.evaluation.inputFormatting;

import it.emarolab.cagg.core.evaluation.CaggThread;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.Facts;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ThreadedInputFormatter extends InputFormatterBase{

	/* ##################################################################################
	   ############################# CONSTANTS ##########################################
	 */
	public static final String DEFAULT_THREAD_NAME = "th-form";
	
	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	private FactsSearcher searcher = null;
	private Integer factsIdx;
	
	/* ##################################################################################
	   ######################## DEFAULT CONSTRUCTOR #####################################
	   The separate thread to format the input and compute the facts.
	   Particularly this constructor does not do nothing. Call searchFacts() to activate it */
	public ThreadedInputFormatter(GrammarBase<?> grammar,  String notAllowableCharPatern) {
		super(grammar, notAllowableCharPatern);
	}
	public ThreadedInputFormatter(GrammarBase<?> grammar) {
		super(grammar);
	}
	public ThreadedInputFormatter(String loggetName, GrammarBase<?> grammar, String notAllowableCharPatern) {
		super(loggetName, grammar, notAllowableCharPatern);
	}
	public ThreadedInputFormatter(String logName, GrammarBase<?> grammar) {
		super(logName, grammar);
	}
	
	/* ##################################################################################
	   ###################### INPUT FORMATTER BASE INTERFACE ############################
	 */
	@Override
	public void start() {
		start( DEFAULT_THREAD_NAME);
	}
	public void start( String threadName) {
		if( searcher == null)
			searcher = new FactsSearcher( threadName, getLoggerName(), this.getTokenisedInputs());
		else if( ! searcher.isRunning())
			searcher = new FactsSearcher( threadName, getLoggerName(), this.getTokenisedInputs());
		else this.logError( "Cannot start a FactsSearcher. Be sure to stop the previous thread frist!");
	}
	@Override
	public void stop() {
		if( searcher != null)
			if( searcher.isRunning())
				searcher.terminate();
			else this.logWarning( "The FactsSearcher thread is no more alive !");
		else this.logError( "Cannot stop a dead FactsSearcher. Be sure to call start() before.");
	}
	@Override
	public void join() {
		if( searcher != null)
			if( searcher.isRunning())
				try {
					searcher.join();
				} catch ( Exception e) {
					this.logError( "error");
					this.logError( e);
				}
			else this.logWarning( "The FactsSearcher thread is no more alive !");
		else this.logError( "Cannot join a dead FactsSearcher. Be sure to call start() before.");
	}
	@Override
	public boolean isRunning() {
		if( searcher != null)
			return searcher.isRunning();
		return false;
	}
	@Override
	public Integer getFactsCount() {
		if( searcher != null)
			return searcher.getSolutionDiscoveredCount();
		return null;
	}
	public Integer getUnfeasibleFactsCount() {
		if( searcher != null)
			return searcher.getUnfeasibleSolutionDiscoveredCount();
		return null;
	}
	@Override
	public Facts<?> getNextFacts() {
		if( searcher != null){
			Facts<Long> next = searcher.getTrueFact( factsIdx++);
			return next;
		}return null;
	}
	public List< Facts<Long>> getUnfeasibleFacts() {
		if( searcher != null)
			return searcher.getAllUnfeasibleFacts();
		return null;
	}
	// this call is not synchronized. it rely on the same index of facts list
	public Set< SkippedWord> getSkippedWords( int idx){ 
		try{
			for( Set< SkippedWord> sks : searcher.getSkippedWords())
				for( SkippedWord sk : sks){
					if( sk.isComingFromSolution( idx))
						return sks;
					break; // all coming from the same solution id
				}			
		}catch ( Exception e){
			this.logError( e);
		}
		return null;
	}
	// this call is not synchronized. it rely on the same index of facts list
	public List< String> getUsedWords( int idx){ 
		try{
			return searcher.getUsedWords( idx);
		}catch ( Exception e){
			this.logError( e);
			return null;
		}
	}
	
	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public FactsSearcher getSearcher() {
		return searcher;
	}
	public Integer getActualFactsIdx() {
		return factsIdx;
	}

	/* ##################################################################################
	   ############################## UTILY CLASS #######################################
	   to implement the procedure to search for facts in a separate thread for
	   performances care.																*/
	public class FactsSearcher extends CaggThread{
		///////////////////////////////////////////////// FIELDS
		private Integer solutionDiscovered = 0, unfeasibleSolutionDiscovered = 0;
		private List< Facts< Long>> trueFacts = new ArrayList<>();
		private List< Facts< Long>> unfeasibleFacts = new ArrayList<>();
		private List< String> userInput;
		private List< List< String>> actualEvaluatedInput = new ArrayList<>(); // it is a reduction of  user input
		private List< Set< SkippedWord>> skippedWords = new ArrayList<>();
		private Set<SkippedWord> skipped = null;
		private Boolean alreadyLoggedFlag; // it was static!!! just to not not log many times the same message during formatter stop
		
		///////////////////////////////////////////////// CONSTRUCT (starts a new thread)
		private FactsSearcher( String threadName, String loggerName, List< String> userInput){
			super( threadName, loggerName);
			setThreadLogger( getThreadLogger()); // for the base formatter class
			this.alreadyLoggedFlag = false;
			this.userInput = userInput;
			factsIdx = 0; // reset to get next formatting input from evaluator
			this.start();
		}
		
		///////////////////////////////////////////////// SEPARATE THREAD IMPLEMENTATION
		// override it to reduce userInput
		protected void reduceInput( List<String> input) {
			input.remove( 0);
		}
		@Override
		public void implement() {
			log( "++ Separate thread to search for facts starts.");
			Long initialTime = System.nanoTime();
			// for all the conseguential combination of user input
			while( ! userInput.isEmpty()){
				log( "++ try with reduced input: " + userInput);
				// initialise skipped word structure
				skipped = new LinkedHashSet< SkippedWord>();
				// uses inputs and grammar objects. Initialise and populate the list of fact to be evaluated
				combineInputs();
				// Manage reduction policy ( only from left to right for now)
				this.reduceInput( userInput);
			}
			shouldRun( false);
			// release waiting objects in case they exists
			synchronized (trueFacts) {			trueFacts.notifyAll();				}
			synchronized (unfeasibleFacts) {	unfeasibleFacts.notifyAll();		}
			float computationalTime = Float.valueOf( System.nanoTime() - initialTime) / DebuggingDefaults.NANOSEC_2_SEC;
			log( "+++++++ separate thread to format input stop. It required: " + computationalTime + " [s] ++++++");
		}
		private void combineInputs() {		// initilise recursive function.
			if( userInput.size() != 0){
				combineRecorsive( 0, new ArrayList< Long>()); // it updates the given array
				log( "++ all true facts combinations (size=" + trueFacts.size() + ")");//: " + trueFacts);
				log( "++ all true unfeasible facts combination (size=" + unfeasibleFacts.size() + ")");//: " + unfeasibleFacts);
			} else logWarning( "++ Cannot compute solution combination for empty input");		
		}
		// this method is called by combineInputs() in order to do it recursively through the trees node
		private List< Long> combineRecorsive( Integer idx, List< Long> inputIds){	// recursive call and term id combination list creation
			if( isRunning()){
				if( idx < userInput.size()){
					if( ! isInSkipped( idx)){
						// get the last analyzed id. If no one use 0  
						Long startingId;
						if( inputIds.isEmpty()) // it should never be null
							startingId = 0L;
						else startingId = inputIds.get( inputIds.size() - 1);
						// search for the term occurrences on the term map (the grammar object does it!!)
						GrammarBase<? extends SemanticExpressionTreeBase>.TermOccurenceCollector occurrences = getTermOccurence( startingId, idx);
						if( occurrences == null){
							SkippedWord skipWord = new SkippedWord( userInput, idx); // the index may be wrong in case of "^" usage
							skipped.add( skipWord);
							inputIds = combineRecorsive( idx + 1, inputIds);
						} else {
							// notify unfeasible solution and continue with feasible ones
							storeUnfeasibleSolution( inputIds, occurrences.getUnfeasibleTermComposition());
							List< Long> termOccurenceId = occurrences.getFeasibleTermComposition();
							// if no occurrences skip the world given by the user (userInput.get( idx)) since it is not on the grammar
							if( ! termOccurenceId.isEmpty()){
								for( Long id : termOccurenceId){ // accept this sub solution and continue searching iteratively
									inputIds.add( id);
									inputIds = combineRecorsive( idx + 1, inputIds);
									inputIds = copyListIterative( inputIds);
								}
							}
						}
					} else {
						log( "++ skip world since it was already not found: " + skipped);
						inputIds = combineRecorsive( idx + 1, inputIds);
					}
				} // do not use ELSE statement since idx and inputIds can change during iteration. 
				if( idx >= inputIds.size()) // Check the condition again instead  
					storeFeasibleSolution( inputIds);
			} else if( ! alreadyLoggedFlag){
				logWarning( "++ fact searcher stopped !!!");
				alreadyLoggedFlag = true;
			}
			return inputIds;
		}
		// increase performances by not proofing that a term does not exist on the grammar if it is already recognised in the skipped worlds
		private Boolean isInSkipped( int idx){
			if( skipped.size() > 0){ // 1
				// most probably is on the last list of unknown words
				for( SkippedWord sk : skipped)
					if( sk.getWord().equalsIgnoreCase( userInput.get( idx)))
						return true;
				// otherwise looks on the overall (biggest) unknown token founded so far
				for( Set< SkippedWord> sks : skippedWords)
					for( SkippedWord sk : sks)
						if( ! sk.equals( skipped))
							if( sk.getWord().equalsIgnoreCase( userInput.get( idx)))
								return true;
			}
			return false;
		}
		// call the manager getAllTermOccurence with the token specified by the idx
		private GrammarBase< ? extends SemanticExpressionTreeBase>.TermOccurenceCollector getTermOccurence( Long startingId, Integer idx){			// get all the id in term map by giving the input text token idx
			return getTermOccurence( startingId, userInput.get( idx));
		}
		// returns all the term (str literals) occurrences by node id 
		private GrammarBase< ? extends SemanticExpressionTreeBase>.TermOccurenceCollector getTermOccurence( Long startingId, String definition){	// get all the id in term map by giving the input text
			return getGrammar().getAllTermOccurence( startingId, definition);
		}
		// this method copy a list by leaving out the last component (used when sub-ET are composed) 
		private List< Long> copyListIterative( List< Long> list){	// copy the list in a new object by removing the last element
			List< Long> out = new ArrayList< Long>();
			for( int i = 0; i < list.size() - 1; i++)
				out.add( list.get( i));
			return out;
		}
		// called as soon as a new solution is available. It populates the class field based on input combining
		private void storeFeasibleSolution( List<Long> inputIds){
			// do not do nothing if nothing is found
			if( inputIds.isEmpty())
				return;
			// get the actual combined facts
			Facts<Long> fact = new Facts< Long>( inputIds);
			// check if this solution as already been considered by searching on smaller set of words in the user input
			Boolean notAlreadyConsideredSolution = true;
			for( Facts< Long> f : trueFacts){
				List<Long> alreadyfacts = f.getAllFacts();
				List<Long> newFacts = fact.getAllFacts();
				if( alreadyfacts.equals( newFacts)){
					notAlreadyConsideredSolution = false;
					break;
				}
			}
			synchronized ( trueFacts) {
				if( notAlreadyConsideredSolution){
					// the list below it is not synchronised, It rely on true fact indexing and size
					if( ! skipped.isEmpty()){
						for( SkippedWord sk : skipped)
							sk.addSolutionId( solutionDiscovered);
						skippedWords.add( skipped);
					}
					List< String> reducedInput = new ArrayList<>();
					reducedInput.addAll( userInput);
					actualEvaluatedInput.add( reducedInput);
					// store solution and manage synchronisation
					trueFacts.add( fact);
					solutionDiscovered += 1;
					trueFacts.notifyAll();
				}
			}
		}
		private void storeUnfeasibleSolution( List<Long> inputIds, List<Long> unfeasibleCombination){
			// do not do nothing if nothing is found
			if( unfeasibleCombination.isEmpty())
				return;
			synchronized ( unfeasibleFacts) {
				// get the actual combined facts
				for( Long unfeasible : unfeasibleCombination){
					if( SHOULD_COLLECT_UNFEASIBLE_FACTS){
						Facts<Long> fact = new Facts< Long>( inputIds);
						fact.add( unfeasible);
						unfeasibleFacts.add( fact);
					}
					unfeasibleSolutionDiscovered += 1;
					unfeasibleFacts.notifyAll();
				}
			}
		}
		
		///////////////////////////////////////////////// GETTERS (thread safe !!!!)
		// searcher state getter
		public List< Set< SkippedWord>> getSkippedWords() {
			synchronized( trueFacts){
				return skippedWords;
			}
		}
		public Set< SkippedWord> getSkippedWords( int idx) {
			synchronized( trueFacts){
				Set< SkippedWord> out = null;
				if( idx >= 0 && idx < skippedWords.size())
					out = skippedWords.get( idx);
				else if( isRunning()){
					try {
						trueFacts.wait();
						return getSkippedWords( idx);
					} catch (InterruptedException e) {
						logError( e);
					}
				}
				return out;
			}
			
		}
		public List< List< String>> getUsedWords(){
			synchronized( trueFacts){
				return actualEvaluatedInput;
			}
		}
		public List< String> getUsedWords( int idx) {
			synchronized( trueFacts){
				List< String> out = null;
				if( idx >= 0 && idx < actualEvaluatedInput.size())
					out = actualEvaluatedInput.get( idx);
				else if( isRunning()){
					try {
						trueFacts.wait();
						return getUsedWords( idx);
					} catch (InterruptedException e) {
						logError( e);
					}
				}
				return out;
			}
			
		}
		public Integer getSolutionDiscoveredCount() {
			return solutionDiscovered;
		}
		public Integer getUnfeasibleSolutionDiscoveredCount() {
			return unfeasibleSolutionDiscovered;
		}
		// searcher result getter
		public List< Facts< Long>> getAllTrueFacts() {
			synchronized( trueFacts){
				return trueFacts;
			}
		}
		public Facts< Long> getTrueFact( int idx) {// trade save ... returns as soon as it is available
			synchronized( trueFacts){
				Facts< Long> out = null;
				if( idx >= 0 && idx < trueFacts.size())
					out = trueFacts.get( idx);
				else if( isRunning()){
					try {
						trueFacts.wait();
						return getTrueFact( idx);
					} catch (InterruptedException e) {
						logError( e);
					}
				}
				return out;
			}
		}
		public List< Facts< Long>> getAllUnfeasibleFacts() {
			synchronized( unfeasibleFacts){
				return unfeasibleFacts;
			}
		}
		public Facts< Long> getUnfeasibleFact( int idx) {// trade save ... returns as soon as it is available
			synchronized( unfeasibleFacts){
				Facts< Long> out = null;
				if( idx >= 0 && idx < unfeasibleFacts.size())
					out = unfeasibleFacts.get( idx);
				else if( isRunning()){
					try {
						unfeasibleFacts.wait();
						return getUnfeasibleFact( idx);
					} catch (InterruptedException e) {
						logError( e);
					}
				}
				return out;
			}
		}			
	}
	
	/* ##################################################################################
	   ############################## UTILY CLASS #######################################
	   this class describe a word given by the used that has been discarded during input
	   formatting since it is not define in the grammar. This word is descibed also 
	   with respect to input tokens count (it may be not consistent if other worlds are
	   pre-filtered through the symbol '^').											 */
	public class SkippedWord{
		/////// FIELDS
		private String word;
		private Integer idx;
		private List< Integer> solutionId = new ArrayList<>();
		/////// CONSTRUCTOR 
		public SkippedWord(List<String> userInput, Integer idx) {
			this.word = userInput.get( idx);
			this.idx = idx;
		}
		/////// SETTERS and smart GETTERS
		public void addSolutionId( Integer solutionIdx){
			this.solutionId.add( solutionIdx);
		}
		public List< Integer> getSolutionIds(){
			return solutionId;
		}
		public Integer getSolutionId( int idx){
			return solutionId.get( idx);
		}
		public Integer getSolutionMinIndex() {
			return solutionId.get( 0);
		}
		public Integer getSolutionMaxIndex() {
			return solutionId.get( getSolutionIds().size() - 1);
		}
		public Integer getSolutionId(){ // the last
			return solutionId.get( solutionId.size() - 1);
		}
		public Boolean isComingFromSolution( SkippedWord other){
			return isComingFromSolution( other.getSolutionId());
		}
		public Boolean isComingFromSolution( Integer otherSolutionId){
			if( solutionId.contains( otherSolutionId))
				return true;
			else return false;
		}
		//////// STUPID GETTER
		public String getWord() {
			return word;
		}
		public Integer getWordIndexOnUserInput() {
			return idx;
		}
		/////// OBJECT INTERFACE (for element adding to set)
		@Override
		public String toString() {
			// parse solution ids. Since user input reducing is just base on left shifting the solution id would be: [k; k+1; k+2; ...; k+n]
			String solIdStr = "";
			if( ! this.getSolutionIds().isEmpty()){
				Integer k = getSolutionMinIndex();
				Integer n = getSolutionMaxIndex();
				if( k == n)
					solIdStr =  k + "";
				else solIdStr = k + ".." + n;
			}
			return "[" + solIdStr + " (" + getWordIndexOnUserInput() + ")]:" + getWord();
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			//result = prime * result + ((idx == null) ? 0 : idx.hashCode());
			result = prime * result + ((word == null) ? 0 : word.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if( obj instanceof SkippedWord)
				return getWord().equals( ( ( SkippedWord) obj).getWord());
			else if( obj instanceof String)
				return getWord().equals( ( String) obj);
			else return super.equals(obj);
		
			/*if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof SkippedWord))
				return false;
			SkippedWord other = (SkippedWord) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			//if (idx == null) {
			//	if (other.idx != null)
			//		return false;
			//} else if (!idx.equals(other.idx))
			//	return false;
			if (word == null) {
				if (other.word != null)
					return false;
			} else if (!word.equals(other.word))
				return false;
			return true;*/
		}
		private ThreadedInputFormatter getOuterType() {
			return ThreadedInputFormatter.this;
		}
		
	}
}

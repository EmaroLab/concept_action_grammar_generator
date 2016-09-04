package it.emarolab.cagg.interfaces;

import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SemanticTree;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.language.parser.TextualParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* this class  is used to generate and serialise grammars based on the specified source files (on constructor).
	it populates the grammar on constructors so you can directly access to the grammar (through getters)
	or serialise it to file.*/ 
public class CaggCompiler{
		
	/* ############################################################################################ 
	   ########################################## FIELDS ########################################## */
	private static Integer textualParseIdentifier = 0;
	private List< String> sourcesPath;
	private String serialisationPath;
	private List< TextualParser> parsers = new ArrayList<>();
	private GrammarBase< SemanticTree> grammar;
	
	/* ############################################################################################ 
	   ####################################### CONSTRUCTORS ####################################### */
	public CaggCompiler( String[] sourcesPath, String serialisationPath){ // Constructor for more than one source files
		initialise( Arrays.asList( sourcesPath), serialisationPath);
	}
	public CaggCompiler( String paths, String serialisationPath){ // Constructor for one single source file
		List< String> sourcesPath = new ArrayList<>();
		sourcesPath.add( paths);
		initialise(sourcesPath, serialisationPath);
	}
	public CaggCompiler( String[] sourcesPath){ // Constructor for one single source file
		initialise( Arrays.asList( sourcesPath), null);
	}
	public CaggCompiler( String paths){ // Constructor for one single source file
		List< String> sourcesPath = new ArrayList<>();
		sourcesPath.add( paths);
		initialise(sourcesPath, null);
	}
	private void initialise( List< String> sourcesPath, String serialisationPath){ // common initialiser for all constructors
		// store and compute fields
		this.sourcesPath = sourcesPath;
		// parse all the sources file
		for( String s : sourcesPath){
			parsers.add( new TextualParser( s, true, textualParseIdentifier++));
			TestLog.info( "source: " + s + " ... PARSED!");
		}
		// create semantic grammar data structures
		grammar = ComposedGrammar.createGrammar( parsers);
		TestLog.info( "Grammar created: " + grammar);
		// serialise grammar to file for fast usage during test
		if( serialisationPath != null){
			grammar.serialise( serialisationPath);
			TestLog.info( "Grammar serialised in: " + serialisationPath);
		} //else TestLog.info( "Grammar not serialised since path is null");
	}
	
	
	/* ############################################################################################ 
	   ########################################## GETTERS ######################################### */
	public List<String> getSourcesPath() {
		return sourcesPath;
	}
	public String getSerialisationPath() {
		return serialisationPath;
	}
	public List<TextualParser> getParsers() {
		return parsers;
	}
	public GrammarBase< SemanticTree> getGrammar() {
		return grammar;
	}
	/* ############################################################################################ 
	   ########################################## SETTERS ######################################### */
	public void setSerialisationPath( String path){
		this.serialisationPath = path;
	}
	
	/* ############################################################################################ 
	   ######################################## DESERIALISER ##################################### */
	public void serialise(){
		grammar.serialise( serialisationPath);
	}
	public GrammarBase< SemanticTree> deserialise(){
		if( serialisationPath != null)
			return (GrammarBase< SemanticTree>) CaggCompiler.deserialise( serialisationPath);
		TestLog.error( "cannot serialise on null path. Give a serialisationPath during CaggCompiler constructor or set it later");
		return null;
	}
	@SuppressWarnings("unchecked")
	public static GrammarBase< SemanticTree> deserialise( String path){
		return (GrammarBase<SemanticTree>) GrammarBase.deserialise( path);
	}
	@SuppressWarnings("unchecked")
	public static GrammarBase< SemanticTree> deserialise( String path, String alias){
		return (GrammarBase<SemanticTree>) GrammarBase.deserialise( path, alias);
	}
}
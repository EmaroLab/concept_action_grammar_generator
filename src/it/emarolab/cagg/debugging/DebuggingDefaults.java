package it.emarolab.cagg.debugging;

import java.awt.Color;
import java.awt.Font;


/*  [[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
  	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ Class that contains ALL debugging and visualization defaults ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]  
 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]] */

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.DebuggingDefaults.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This static class contains static variables identifying the 
 * default system configurations for visualisation and debugging.<br> 
 * Be aware that those variables are not just used in the GUI, 
 * but those are global configurations for all the system.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingText
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.example.APIInputOutput
 * @see it.emarolab.cagg.example.APIEvaluation
 * @see it.emarolab.cagg.example.GUIRunner
 */
public final class DebuggingDefaults{
	
	/**
	 * Define this class as a static class (not constructible).
	 */
	private DebuggingDefaults(){}; // static class constructor !!!
	
	
	public static final Long NANOSEC_2_SEC = 1000000000L;
	
	public static final Font DEFAULT_GUI_FONT = new Font( "Monospaced", Font.PLAIN, 11);
	public static final Font DEFAULT_GUI_FONT_BOLD = new Font( "Monospaced", Font.BOLD, 11);
	public static final Font DEFAULT_GUI_FONT_ITALIC = new Font( "Monospaced", Font.ITALIC, 11);
	
	
	// ########################################################################################################################
	// for logging bheavior in all the system (defined in the DebuggingText class) ############################################
	/**
	 * Flag to collect ({@code true}) the {@code System.out} stream into the {@code Logger} object 
	 * ({@link it.emarolab.cagg.debugging.DebuggingText#ATTACH_OUT_LOGS}).<br>
	 * Default value: {@literal true}.
	 */
	public static final Boolean LOG_LISTEN_SYSTEM_OUT = true; // collects System.out.print
	/**
	 * Flag to collect ({@code true}) the {@code System.err} stream into the {@code Logger} object 
	 * ({@link it.emarolab.cagg.debugging.DebuggingText#ATTACH_ERR_LOGS}).<br>
	 * Default value: {@literal true}.
	 */
	public static final Boolean LOG_LISTEN_SYSTEM_ERR = true; // collects System.err.print
	/**
	 * Flag to make the {@code Logger} object printing ({@code true}) the ERROR messages
	 * ({@link it.emarolab.cagg.debugging.DebuggingText#SHOW_ERROR}).<br>
	 * Default value: {@literal true}.
	 */
	public static final Boolean LOG_ERROR = true;
	/**
	 * Flag to make the {@code Logger} object printing ({@code true}) the WARNING messages
	 * ({@link it.emarolab.cagg.debugging.DebuggingText#SHOW_WARNING}).<br>
	 * Default value: {@literal true}.
	 */
	public static final Boolean LOG_WARNING = true;
	/**
	 * Flag to make the {@code Logger} object printing ({@code true}) the INFO messages
	 * ({@link it.emarolab.cagg.debugging.DebuggingText#SHOW_INFO}).<br>
	 * Default value: {@literal true}.
	 */
	public static final Boolean LOG_INFO = true; // verbose
	/**
	 * Flag to make the {@code Logger} object printing ({@code true}) the messages on the dedicate panel in the GUI.
	 * ({@link it.emarolab.cagg.debugging.DebuggingText#PRINT_ON_GUI}).<br>
	 * Default value: {@literal true}.
	 */
	public static final Boolean LOG_ON_GUI = true;
	/**
	 * Flag to make the {@code Logger} object printing ({@code true}) the messages on the java console.
	 * ({@link it.emarolab.cagg.debugging.DebuggingText#PRINT_ON_CONSOLE}).<br>
	 * Default value: {@literal true}.
	 */
	public static final Boolean LOG_ON_CONSOLE = true;
	// for debuggin apparence
	/**
	 * The string to be used as a prefix for an ERROR message type.<br>
	 * Default value: {@literal "[ERROR]\t"}.
	 */
	public static final String TAG_ERROR = "[ERROR]\t";
	/**
	 * The string to be used as a prefix for an WARNING message type.<br>
	 * Default value: {@literal "[WARNING]\t"}.
	 */
	public static final String TAG_WARNING = "[WARNING]\t";
	/**
	 * The string to be used as a prefix for an WARNING message type.<br>
	 * Default value: {@literal "[OK]\t"}.
	 */
	public static final String TAG_OK = "[OK]\t";
	/**
	 * The string to be used as a prefix for an INFO message type.<br>
	 * Default value: {@literal "[INFO]\t"}.
	 */
	public static final String TAG_INFO = "[INFO]\t";

	// ########################################################################################################################
	// operative system properties  ###########################################################################################
	/**
	 * The operative system's line separator symbol.<br>
	 * By default {@code = System.getProperty( "line.separator")}.
	 */
	public static final String SYS_LINE_SEPARATOR = System.getProperty( "line.separator");
	/**
	 * The operative system's file separator symbol.<br>
	 * By default {@code = System.getProperty( "file.separator")}.
	 */
	public static final String SYS_PATH_DELIM = System.getProperty( "file.separator");
	/**
	 * The user's home directory path.<br>
	 * By default {@code = System.getProperty( "user.home")}.
	 */
	public static final String SYS_PATH_HOME = System.getProperty( "user.home");
	/**
	 * The user's actual working directory path.<br>
	 * By default {@code = System.getProperty( "user.dir")}.
	 */
	public static final String SYS_PATH_WORKING = System.getProperty( "user.dir");
	
	

	// ########################################################################################################################
	// file path defaults  ####################################################################################################
	public static final String PARH_LOG_CONF_BASE = SYS_PATH_WORKING + SYS_PATH_DELIM + "file" + SYS_PATH_DELIM + "log" + SYS_PATH_DELIM + "log4j_configuration" + SYS_PATH_DELIM;
	
	/**
	 * The default relative path to the folder that collects all the grammar files.
	 * Used when the system is not run from a JAR archive.<br>
	 * By default {@code = "/file/grammars/"}.
	 */
	public static final String PATH_GRAMMAR_RELATIVE_JAR = SYS_PATH_DELIM + "file" + SYS_PATH_DELIM + "grammars" + SYS_PATH_DELIM; // relative w.r.t. jar file
	/**
	 * The default relative path to the folder that collects all the grammar files.
	 * Used when the system is not run not from a JAR archive.<br>
	 * By default {@code = "/files/grammars/"}.
	 */
	public static final String PARH_GRAMMAR_RELATIVE_NOTJAR = SYS_PATH_DELIM + "file" + SYS_PATH_DELIM + "grammars"+ SYS_PATH_DELIM; // relative w.r.t. workspace (PATH_WORKING)
	/**
	 * The absolute path to the grammar directory. 
	 * Used when relative path does not exists and the application is running from JAR archive.
	 * By default {@code = System.getProperty( "user.dir")}.
	 */
	public static final String PATH_GRAMMAT_ABSOLUTE = SYS_PATH_HOME; // when relative does not exists
	/**
	 * The absolute path to the grammar directory. 
	 * Used when the application is running not from JAR archive and it is based on {@link #PARH_GRAMMAR_RELATIVE_NOTJAR}.
	 * By default {@code = System.getProperty( "user.dir") + "/file/grammars/"}.
	 */
	public static final String PATH_ABSOLUTE_NOTJAR = SYS_PATH_WORKING + PARH_GRAMMAR_RELATIVE_NOTJAR;
	/**
	 * The default directory path where serialised object are stored.
	 * Used when the application is running from JAR archive.<br>
	 * By default {@code = "/serialised/"}.
	 */
	public static final String PATH_GRAMMA_DESERIALISATION_JAR = SYS_PATH_DELIM + "serialised" + SYS_PATH_DELIM;
	/**
	 * The default directory path where serialised object are stored.
	 * Used when the application is not running from JAR archive.<br>
	 * By default {@code = System.getProperty( "user.dir") + "/file/serialised/"}.
	 */
	public static final String PATH_GRAMMA_DESERIALISATION_NOTJAR = SYS_PATH_WORKING + SYS_PATH_DELIM + "file" + SYS_PATH_DELIM + "serialised" + SYS_PATH_DELIM;

	// ########################################################################################################################
	// GUI VISUALIZATION CONSTANTS  ###########################################################################################
	/**
	 * The main GUI frame scale with respect to full screen dimensions.<br>
	 * Defaults: {@literal 0.8}
	 */
	public static final Float FRAME_SCALE = 0.8f; // with respect to full screen
	/**
	 * The scale factor of the vertical split panels ({@link javax.swing.JSplitPane}).<br>
	 * For example, it is used as: {@code splitPane.setResizeWeight( DebuggingDefaults.SUBSPLIT_ASN_SCALE);}<br>
	 * Default value: {@literal 0.3}
	 */
	public static final Float SUBSPLIT_ASN_SCALE = 0.3f;
	public static final double SUBSPLIT_TEST_SCALE = 0.8f;
	/**
	 * The other GUI frame scale with respect to full screen dimensions.<br>
	 * Defaults: {@literal 0.8}
	 */
	public static final Float SUBFRAME_DIVISOR = 0.8f;
	// the summ of the split_scale should be 1.0
	/**
	 * The scale factor of the horizontal ({@link javax.swing.JSplitPane}) panel used to instantiate the Source grammar panel
	 * ({@link it.emarolab.cagg.debugging.baseComponents.customPanel.SourceDebuggingPanel}).<br>
	 * For example, it is used as: {@code splitPane.setResizeWeight( DebuggingDefaults.SPLIT_SOURCE_SCALE);}<br>
	 * Default value: {@literal 0.3}
	 */
	public static final Float SPLIT_SOURCE_SCALE = 0.3f; // hide it
	/**
	 * The scale factor of the horizontal ({@link javax.swing.JSplitPane}) panel used to instantiate the Parsing Tree panel
	 * ({@link it.emarolab.cagg.debugging.baseComponents.customPanel.ParserTreeDebuggingPanel}).<br>
	 * For example, it is used as: {@code splitPane.setResizeWeight( DebuggingDefaults.SPLIT_PT_SCALE);}<br>
	 * Default value: {@literal 0.6}
	 */
	public static final Float SPLIT_PT_SCALE = 0.6f;
	/**
	 * The scale factor of the horizontal ({@link javax.swing.JSplitPane}) panel used to instantiate the Abstract Syntax Tree panel;
	 * which contains also the {@link it.emarolab.cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel}
	 * ({@link it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel}).<br>
	 * For example, it is used as: {@code splitPane.setResizeWeight( DebuggingDefaults.SPLIT_AST_SCALE);}<br>
	 * Default value: {@literal 0.5}
	 */
	public static final Float SPLIT_AST_SCALE = 0.5f;
	/**
	 * The scale factor of the horizontal ({@link javax.swing.JSplitPane}) panel used to instantiate the Test panel
	 * ({@link it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel}).<br>
	 * For example, it is used as: {@code splitPane.setResizeWeight( DebuggingDefaults.SPLIT_TEST_SCALE);}<br>
	 * Default value: {@literal 0.5}
	 */
	public static final Float SPLIT_TEST_SCALE = 0.2f;
	/**
	 * The scale factor of the horizontal ({@link javax.swing.JSplitPane}) panel used to instantiate the Logs panel
	 * ({@link it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel}).<br>
	 * For example, it is used as: {@code splitPane.setResizeWeight( DebuggingDefaults.SPLIT_LOG_SCALE);}<br>
	 * Default value: {@literal 0.9}
	 */
	public static final Float SPLIT_LOG_SCALE = 0.9f;
	// split panel tile button settings
	/**
	 * The scale factor of split panels ({@link javax.swing.JSplitPane}) applied as:
	 * {@code splitPane.setResizeWeight( DebuggingDefaults.SUBSPLIT_ASN_SCALE);} when the panel titles are clicked.
	 * Default value: {@literal 0.1} (hide it).
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
	 */
	public static final float SPLIT_COLLAPSE_SCALE = 0.1f; // used in BaseDebugginGuiPanel class
	/**
	 * The scale factor of split panels ({@link javax.swing.JSplitPane}) applied as:
	 * {@code splitPane.setResizeWeight( DebuggingDefaults.SUBSPLIT_ASN_SCALE);} when the panel titles are clicked.
	 * Default value: {@literal 0.9} (expande it).
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
	 */
	public static final float SPLIT_EXPAND_SCALE = 0.9f;	// used in BaseDebugginGuiPanel class
	/**
	 * Scroll increment used in all the {@link javax.swing.JScrollPane} used in the application.<br>
	 * It is used as: {@code scrollPane.getVerticalScrollBar().setUnitIncrement( DebuggingDefaults.SCROOL_INCREMENT);}
	 */
	public static final Integer SCROOL_INCREMENT = 20;		// used in BaseDebugginGuiPanel class
	// tre visualisation setting
	/**
	 * The scale factor of the parsing tree in its panel.
	 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.ParserTreeDebuggingPanel
	 */
	public static final double TREE_SCALE_TYPE = .9f;		// used in ParserTreeDebuggingPanel and ParserBase classes.
	// color for buttons and sections
	/**
	 * Each color of the GUI follow a gradient between two colors.
	 * This is the base color for all the gradients.
	 * Used in {@link it.emarolab.cagg.debugging.baseComponents.JGradientButton}.<br>
	 * Default value {@literal gray}.
	 */
	public static final Color COLOR_BASE_GRADIENT = Color.gray;	// used for JGradientButton class
	/**
	 * This is {@code = COLOR_BASE_GRADIENT}. 
	 * @see #COLOR_BASE_GRADIENT
	 */
	public static final Color COLOR1_SOURCE = COLOR_BASE_GRADIENT;
	/**
	 * The color of the source panel and relate components. <br>
	 * Default value {@literal orange}.
	 */
	public static final Color COLOR2_SOURCE = Color.orange.darker();
	/**
	 * This is {@code = COLOR_BASE_GRADIENT}. 
	 * @see #COLOR_BASE_GRADIENT
	 */
	public static final Color COLOR1_PT = COLOR_BASE_GRADIENT;
	/**
	 * The color of the parsing tree panel and relate components. <br>
	 * Default value {@literal orange}.
	 */
	public static final Color COLOR2_PT = Color.orange.brighter();
	/**
	 * This is {@code = COLOR_BASE_GRADIENT}. 
	 * @see #COLOR_BASE_GRADIENT
	 */
	public static final Color COLOR1_AST = COLOR_BASE_GRADIENT;
	/**
	 * The color of the abstract syntax tree panel and relate components. <br>
	 * Default value {@literal orange}.
	 */
	public static final Color COLOR2_AST = Color.orange.brighter();
	/**
	 * This is {@code = COLOR_BASE_GRADIENT}. 
	 * @see #COLOR_BASE_GRADIENT
	 */
	public static final Color COLOR1_ASN = COLOR_BASE_GRADIENT;
	/**
	 * The color of the abstract syntax node panel and relate components. <br>
	 * Default value {@literal orange}.
	 */
	public static final Color COLOR2_ASN = Color.orange;
	/**
	 * This is {@code = COLOR_BASE_GRADIENT}. 
	 * @see #COLOR_BASE_GRADIENT
	 */
	public static final Color COLOR1_LOG = COLOR_BASE_GRADIENT;
	/**
	 * The color of the logger panel and relate components. <br>
	 * Default value {@literal cyan}.
	 */
	public static final Color COLOR2_LOG = Color.cyan.brighter();
	/**
	 * The color of the heder of the logging table. <br>
	 * Default value {@literal gray}.
	 */
	public static final Color COLOR_LOG_HEADER = Color.gray.brighter();
	
	/**
	 * This is {@code = COLOR_BASE_GRADIENT}. 
	 * @see #COLOR_BASE_GRADIENT
	 */
	public static final Color COLOR1_COMP = COLOR_BASE_GRADIENT;
	/**
	 * The color of the complete grammar panel and relate components. <br>
	 * Default value {@literal green}.
	 */
	public static final Color COLOR2_COMP = Color.green.brighter().brighter();
	/**
	 * This is {@code = COLOR_BASE_GRADIENT}. 
	 * @see #COLOR_BASE_GRADIENT
	 */
	public static final Color COLOR1_TEST = COLOR_BASE_GRADIENT;
	/**
	 * The color of the test panel and relate components. <br>
	 * Default value {@literal green}.
	 */
	public static final Color COLOR2_TEST = COLOR2_COMP.darker();
	/**
	 * Color of all the text fields in the GUI. <br>
	 * Default value {@literal gray}.
	 */
	public static final Color COLOR_TEST_FIELD = Color.gray.brighter(); // used in TestDebugginGuiPanel
	/**
	 * Color off the node of the complete grammar tree that have null value.<br>
	 * Default value: {@literal gray}.
	 */
	public static final Color COLOR_TREE_NULL_VALUE = new Color( 58, 69, 60); // gray
	/**
	 * Color off the node of the complete grammar tree that have true value.<br>
	 * Default value: {@literal blue}.
	 */
	public static final Color COLOR_TREE_TRUE_VALUE = new Color( 10, 50, 168); // blue
	/**
	 * Color off the node of the complete grammar tree that have false value.<br>
	 * Default value: {@literal red}.
	 */
	public static final Color COLOR_TREE_FALSE_VALUE = new Color( 219, 44, 82); // red
	/**
	 * Color of the background of a selected note in the complete grammar tree. <br>
	 * Default value: {@literal white}.
	 */
	public static final Color COLOR_TREE_SELECTED_VALUE = Color.LIGHT_GRAY;
	// top button and label text
	/**
	 * The prefix on the title of the Grammar sub-tabs.<br>
	 * Default value: {@literal "G:"}.
	 */
	public static final String TAB_TITLE_PREFIX = "G:";
	/**
	 * The title of the Grammar tab.<br>
	 * Default value: {@literal "Grammars"}.
	 */
	public static final String MAINTAB1_TITLE_PREFIX = "Grammars";
	/**
	 * The title of the Test tab.<br>
	 * Default value: {@literal "Testing"}.
	 */
	public static final String MAINTAB2_TITLE_PREFIX = "Testing";
	/**
	 * The prefix on the title of the Solution found in the complete grammar tree during test.<br>
	 * Default value: {@literal "Sol:"}.
	 */
	public static final String TESTTAB_TITLE_PREFIX = "Sol:";
	/**
	 * The main frame title of the GUI. <br>
	 * Default value: {@literal "Semantic Parser Debugging Gui"}.
	 */
	public static final String FRAME_TITLE = "Semantic Parser Debugging Gui";
	/**
	 * The deserialisation frame title of the GUI.
	 * Default value: {@literal "Complite Grammar Tree"}.
	 */
	public static final String FRAME_GRAMMAR_TITLE = "Complite Grammar Tree";
	/**
	 * The label of the button to open source file. <br>
	 * Default value: {@literal "File: "}.
	 */
	public static final String LABEL_OPENING_TEXT = "File: ";
	/**
	 * The label of the button to compute the Parsing Tree of the source file. <br>
	 * Default value: {@literal "Load: "}.
	 */
	public static final String BTN_OPEN_TITLE = "Load";
	/**
	 * The label of the button to compute the Abstract Syntax Tree of the Parsing Tree. <br>
	 * Default value: {@literal "Parse"}.
	 */
	public static final String BTN_PT_TITLE = "Parse";
	/**
	 * The label of the button to evaluate the results of an input string given as speech simulation. <br>
	 * Default value: {@literal "Test"}.
	 */
	public static final String BTN_TEST_ON_TITLE = "Test";
	/**
	 * The label of the button to stop evaluate an input string given as speech simulation. <br>
	 * Default value: {@literal "Stop"}.
	 */
	public static final String BTN_TEST_OFF_TITLE = "Stop";
	/**
	 * The label of the button to search and visualise a node in a semantic expression tree created during simulation. <br>
	 * Default value: {@literal "Find"}.
	 */
	public static final String BTN_FIND_TITLE = "Find";
	/**
	 * The label of the button to visualise the next found node in a semantic expression tree created during simulation. <br>
	 * Default value: {@literal "Next"}.
	 */
	public static final String BTN_FIND_NEXT_TITLE = "Next";
	/**
	 * The label of the button to visualise the previous found node in a semantic expression tree created during simulation. <br>
	 * Default value: {@literal "Previous"}.
	 */
	public static final String BTN_FIND_PREVIOUS_TITLE = "Previous";
	
	/**
	 * The label of the button to add a new source into the grammar to be parsed in the data structure. <br>
	 * Default value: {@literal "Add"}.
	 */
	public static final String BTN_GRAMMAR_TITLE = "Add";
	/**
	 * The label of the button to create the complete grammar from the Abstract Syntax Tree of the sources. <br>
	 * Default value: {@literal "Create"}.
	 */
	public static final String BTN_MERGE_TITLE  = "Create";
	/**
	 * The label of the button to serialise the grammar in a data structure. <br>
	 * Default value: {@literal "Serialise"}.
	 */
	public static final String BTN_SERIALISE_TITLE = "Serialise";
	/**
	 * The label of the button to deserialise a data structure. <br>
	 * Default value: {@literal "Open"}.
	 */
	public static final String BTN_DESERIALISE_TITLE = "Open";
	// title of the split panel
	/**
	 * The title of the Source panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.SourceDebuggingPanel}.<br>
	 * Default value: {@literal "Source Text: (this is the text used by the parser)"}.
	 */
	public static final String PANEL_SOURCE_TITLE = "Source Text: (this is the text used by the parser)";
	/**
	 * The title of the Parsing Tree panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.ParserTreeDebuggingPanel}.<br>
	 * Default value: {@literal "(PT) Parsing Tree: (this contains only string data coming from ANTLR parser)"}.
	 */
	public static final String PANEL_PT_TITLE = "(PT) Parsing Tree: (this contains only string data coming from ANTLR parser)";
	/**
	 * The title of the Parsing Tree panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel}.<br>
	 * Default value: {@literal "(AST) Abstract Syntax Tree: (nodes contains a data class, select them to show it)"}.
	 */
	public static final String PANEL_AST_TITLE = "(AST) Abstract Syntax Tree: (nodes contains a data class, select them to show it)";
	/**
	 * The title of the Parsing Tree panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel}.<br>
	 * Default value: {@literal "(ASN) Abstract Syntax Node:"}.
	 */
	public static final String PANEL_ASN_TITLE = "(ASN) Abstract Syntax Node:";
	/**
	 * The title of the Parsing Tree panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel}.<br>
	 * Default value: {@literal "Syntax Testing: (for testing by typing instead of speaking)"}.
	 */
	public static final String PANEL_TEST_TITLE = "Semantic Evaluation (for testing by typing instead of speaking)";
	/**
	 * The title of the Parsing Tree panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel}.<br>
	 * Default value: {@literal "Semantic Expression:"}.
	 */
	public static final String PANEL_COMPLETETREE_TITLE = "Semantic Expression:";
	/**
	 * The title of the Parsing Tree panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.MapsDebugginGuiPanel}.<br>
	 * Default value: {@literal "Grammars Maps:"}.
	 */
	public static final String PANEL_PREAMBLE_TITLE = "Grammars Maps:";
	/**
	 * The title of the Parsing Tree panel {@link it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel}.<br>
	 * Default value: {@literal "Debuggin Logs:"}.
	 */
	public static final String PANEL_LOG_TITLE = "Debugging Logs:";
	/**
	 * The title of the Preamble node map for the complete grammar.<br>
	 * Default value:  {@literal "COMPLETE PREAMBLE MAP:"}.
	 */
	public static final String PANEL_PREAMBLE_MAP_SUBTITLE = "COMPLETE PREAMBLE MAP:";
	/**
	 * The title of the Rule nodes map for the complete grammar.<br>
	 * Default value:  {@literal "COMPLETE RULE MAP:"}.
	 */
	public static final String PANEL_RULE_MAP_SUBTITLE = "COMPLETE RULE MAP:";
	/**
	 * The title of the Term node map for the complete grammar.<br>
	 * Default value:  {@literal "COMPLETE TERM MAP:"}.
	 */
	public static final String PANEL_TERM_MAP_SUBTITLE = "COMPLETE TERM MAP:";
	/**
	 * The title of the Tag node map for the complete grammar.<br>
	 * Default value:  {@literal "COMPLETE TAG MAP:"}.
	 */
	public static final String PANEL_TAG_MAP_SUBTITLE = "COMPLETE TAG MAP;";
	
	/**
	 * The title of the rule node map for the complete grammar.<br>
	 * Default value:  {@literal "COMPLETE RULE NODE MAP:"}.
	 */
	public static final String PANEL_RULENODE_MAP_SUBTITLE = "COMPLETE RULE NODE MAP";
	
	/**
	 * The tile frame of the saving (seralising) form.
	 */
	public static final String BTN_SAVE_TITLE = "Serialize Grammar Tree";
	// text in the test section of the gui
	/**
	 * Label to identify input text for tests. Used from:
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel}.
	 */
	public static final String LABEL_INTEST = "Set here the input your would say:"; // used in TestDebuggingGuiPanel class
	/**
	 * Label to identify output text for tests. Used from:
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel}.
	 */
	public static final String LABEL_OUTTEST = "Look here the output of the system:"; // used in TestDebuggingGuiPanel class

	// add empty line to the end of the source to avoid the sourceTab to take all the space
	/**
	 * Empty lines that will be added to the end of the source file in order to respect the {@link javax.swing.JSplitPane} scale factors.
	 */
	public static final String ADD_TO_SOUCE_TAIL = 	  SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR +
			SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + 
			SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR	+
			SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + 
			SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR +
			SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + 
			SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR + SYS_LINE_SEPARATOR;
	/**
	 * Empty space that will be added as {@link javax.swing.JLabel} in order to separate components on the top menu.<br>
	 * Default value: {@literal "   "}
	 */
	public static final String ADD_TOPMENU_SPACE = "   ";


		
}

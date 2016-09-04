package it.emarolab.cagg.debugging;

import it.emarolab.cagg.core.evaluation.CaggThread;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.GuiEvaluationInterface;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SemanticTree;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.core.language.parser.TextualParser;
import it.emarolab.cagg.debugging.baseComponents.BooleanButtonListener;
import it.emarolab.cagg.debugging.baseComponents.DeserialisationGui;
import it.emarolab.cagg.debugging.baseComponents.JGradientBooleanButton;
import it.emarolab.cagg.debugging.baseComponents.JGradientButton;
import it.emarolab.cagg.debugging.baseComponents.PanelTabButton;
import it.emarolab.cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.MapsDebugginGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.ParserTreeDebuggingPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.SourceDebuggingPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.DebuggingGui.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class implements the main frame of the GUI.
 * </p>
 *
 * @see JarChecker
 * @see DebuggingActioner
 * @see DebuggingStaticActioner
 * @see it.emarolab.cagg.example.GUIRunner
 * @see it.emarolab.cagg.debugging.DebuggingDefaults
 * @see it.emarolab.cagg.debugging.DebuggingText
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.JGradientButton
 * @see it.emarolab.cagg.debugging.baseComponents.PanelTabButton
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.MapsDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.ParserTreeDebuggingPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SourceDebuggingPanel
 **/
@SuppressWarnings("serial")
public abstract class DebuggingGui extends JFrame {

	// ########################################################################################################################
	// Fields #################################################################################################################
	private GrammarBase< SemanticTree> merger;
	private TestDebugginGuiPanel testPanel = null;
	private MapsDebugginGuiPanel preabmleGuiPanel = null;
	private SemanticTreeGuiPanel completeGuiPanel = null;
	private JButton openBtn = null, parseBtn = null, serialiseBtn = null; 
	private JGradientBooleanButton testBtn = null;
	private JTabbedPane tabPanelsDefault = null;
	private JLabel fileLabel; // to show the open (in this tab) file path in top menu
	private static Integer grammarTabCounter = 0, actionerIDcount = 0; // enumerate the grammar tab
	private static List< DebuggingActioner> actioners = new ArrayList< DebuggingActioner>(); // every tab as an actioner attached to it
		
	// ########################################################################################################################
	// Main constructor #######################################################################################################
	/**
	 * Initialise this class by setting up the GUI components. This call does not show it, see {@link #runGui()} for this.
	 */
	public DebuggingGui(){	// just initialise the gui and show it
		super();
		initialiseGui();
	}
			

	/* 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ Class to get jar file path informations ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]] 
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]] */
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.debugging.DebuggingGui.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *    
	 * <p>
	 * This class is used to populate the data needed from path source loading
	 * with respect to the fact that the application is run from JAR archive or not.  
	 * </p>
	 *
	 * @see DebuggingGui
	 */
	// compute if the application has been run from jar or not
	// and try to returns the path to the jar (it may return null)
	public static class JarChecker{
		private static Boolean 	isFromJar;  // class field
		
		/**
		 * Initialise this object by checking if the application is running from JAR archive or not.
		 */
		public JarChecker(){	// constructor	
			isFromJar = checkIsRunningFromJar(); 
		}
		private Boolean checkIsRunningFromJar() { // method to get if the application has been run from jar
			String classJar =  this.getClass().getResource("/" + this.getClass().getName().replace('.', '/') + ".class").toString();
			if( classJar.startsWith("jar:") | classJar.startsWith("rsrc:")){
				UILog.info( "The application is running from jar. (" + classJar + ")");
				return true;
			}
			UILog.info( "The application is not running from jar. ("  + classJar + ")");
			return false;
		}

		/**
		 * @return {@code true} if the application is running from JAR archive. {@code false} otherwise.
		 */
		public static Boolean isRunningFromJar(){ // getter function  
			return isFromJar; 
		} 
		
		/**
		 * @return the path to the JAR application runner file. It returns {@code null} if an {@code Exception} occurs.
		 */
		public static String getJarPath(){ // to get the directory path up to the jar applicatiom runner file
			try {
				return DebuggingGui.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			} catch (Exception e) {
				UILog.error( e);
				return null;
			}
		}
	}

	/* 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ Run and Show the GUI ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]  */
	/**
	 * @return the actioneers attached to all the loaded components with which the user can interact.
	 */
	public static List<DebuggingActioner> getActioners() {
		return actioners;
	}

	/**
	 * Remove an auctioneer from the GUI. The purpose of this function is only to be called from {@link it.emarolab.cagg.debugging.baseComponents.PanelTabButton}
	 * when a grammar sub-tab is deleted.<br>
	 * It has been designed to be called from {@link it.emarolab.cagg.debugging.baseComponents.PanelTabButton}
	 * @param actionerId the id (whit respect to sub-tab panel removed) of the actioneers to be removed.
	 */
	public static void removeActioner(int actionerId){	// to update actioners (called on PanelTabButton when tab is closed)
		if( actionerId >= 0 && actionerId < actioners.size()){
			actioners.remove( actionerId);					
			grammarTabCounter--;
		}
	}
	
	/**
	 * @return the parsing listeners of all the loaded grammars.
	 */
	public static List< TextualParser> getTextualParsers(){
		List< TextualParser> textualParsers = new ArrayList< TextualParser>();
		for( DebuggingActioner a : actioners){
			textualParsers.add( a.getTextualParser());
		}
		return textualParsers;
	}
	
	/*  [[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ Main class constructor sets the GUI components ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]  
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]  */
	// Create the frame. on constructor
	private void initialiseGui() {
		// ########################################################################################################################
		// set frame	###########################################################################################################
		setTitle( DebuggingDefaults.FRAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(200, 100, (int) (dim.getWidth() * DebuggingDefaults.FRAME_SCALE), (int) (dim.getHeight() * DebuggingDefaults.FRAME_SCALE));

		fileLabel = new JLabel( "");	// initialise now to remove null error later	

		// add log panel
		JSplitPane logSplitPanel = new JSplitPane(); 							// --------------
		logSplitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		logSplitPanel.setResizeWeight( DebuggingDefaults.SPLIT_LOG_SCALE);
		add( logSplitPanel);
		new LogDebuggingGuiPanel( logSplitPanel, DebuggingDefaults.PANEL_LOG_TITLE, DebuggingDefaults.COLOR1_LOG, DebuggingDefaults.COLOR2_LOG, true);
		
		// add tab for grammars merging and serialisation (main top level tab panel)
		tabPanelsDefault = new JTabbedPane();	// add new tab container
		logSplitPanel.setLeftComponent( tabPanelsDefault);
		tabPanelsDefault.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		
		// add tab for more grammars (bottom level tab panel)
		final JTabbedPane tabPanels = new JTabbedPane();	// add new tab container
		tabPanels.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabPanelsDefault.add( DebuggingDefaults.MAINTAB1_TITLE_PREFIX, tabPanels);
		tabPanelsDefault.setBackgroundAt( 0, DebuggingDefaults.COLOR2_SOURCE);
		
		addTabTestContents( tabPanelsDefault);				// initialise merge and test grammar tab
		actioners.add( addTabContents( tabPanels));			// initialise the first tab (WITH ACTIONERS)
		addMenuContents( tabPanels, actioners.get( 0)); 	// initialise the menu on the top of the frame

		// to set the label contents of the opened file
		tabPanels.addChangeListener(new ChangeListener() { // show on memu the file opened in this tab
			public void stateChanged(ChangeEvent e) {
				Integer tabIndex =  tabPanels.getSelectedIndex(); // - because there is the log and text tab
				if( tabIndex > -1)
					fileLabel.setText( actioners.get( tabIndex).getOpeningFilePath()); // grammar tab
			}
		});
		// set button enable with respect to top level tab panel
		tabPanelsDefault.addChangeListener(new ChangeListener() { // show on memu the file opened in this tab
			public void stateChanged(ChangeEvent e) {
				Integer tabIndex =  tabPanelsDefault.getSelectedIndex(); // - because there is the log and text tab
				if( tabIndex == 0)
					setParsingButtonVisibility();
				else setTestingButtonVisibility();				
			}
		});
		setParsingButtonVisibility();
	}
	private void setParsingButtonVisibility(){
		fileLabel.setVisible( true);
		openBtn.setVisible( true);
		parseBtn.setVisible( true);
		serialiseBtn.setVisible( false);
		testBtn.setVisible( false);
	}
	private void setTestingButtonVisibility(){
		fileLabel.setVisible( false);
		openBtn.setVisible( false);
		parseBtn.setVisible( false);
		serialiseBtn.setVisible( true);
		testBtn.setVisible( true);
	}
	
	// fill the default (test) tab with contents
	private void addTabTestContents( JTabbedPane parentComponent){ 
		// add main vertical split for test tab panel
		JSplitPane testSplitPane = new JSplitPane(); 							// --------------
		testSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		testSplitPane.setResizeWeight( DebuggingDefaults.SPLIT_TEST_SCALE);
		parentComponent.add( DebuggingDefaults.MAINTAB2_TITLE_PREFIX, testSplitPane);
		tabPanelsDefault.setBackgroundAt( 1, DebuggingDefaults.COLOR2_TEST);
		// add sub split view on the right part
		JSplitPane serialSlpitPane = new JSplitPane();
		serialSlpitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		serialSlpitPane.setResizeWeight( DebuggingDefaults.SPLIT_TEST_SCALE); // set to fit
		testSplitPane.setRightComponent( serialSlpitPane);
		// create panel to visualise complete final rule tree (lefth heand side)
		JPanel completeTreePanel = new JPanel();
		testSplitPane.setLeftComponent( completeTreePanel);
		completeGuiPanel = new SemanticTreeGuiPanel( completeTreePanel, DebuggingDefaults.PANEL_COMPLETETREE_TITLE, DebuggingDefaults.COLOR1_COMP, DebuggingDefaults.COLOR2_COMP);
		// create panel to show the preamble map (right bottom corner)
		JPanel preamblePanel = new JPanel();
		serialSlpitPane.setRightComponent( preamblePanel);
		preabmleGuiPanel = new MapsDebugginGuiPanel( preamblePanel, DebuggingDefaults.PANEL_PREAMBLE_TITLE, DebuggingDefaults.COLOR1_COMP, DebuggingDefaults.COLOR2_COMP);
		// create panel to test the system and serialise the resulting structure (right top corner)
		JPanel testMainPanel = new JPanel();
		serialSlpitPane.setLeftComponent( testMainPanel);
		serialSlpitPane.setResizeWeight( DebuggingDefaults.SUBSPLIT_TEST_SCALE);
		testPanel = new TestDebugginGuiPanel( testMainPanel, DebuggingDefaults.PANEL_TEST_TITLE, DebuggingDefaults.COLOR1_TEST, DebuggingDefaults.COLOR2_TEST);
	}
	
	// fill the tab with contents
	private DebuggingActioner addTabContents( JTabbedPane parentComponent){ // set automatically the grammar name as serials
		return addTabContents( parentComponent, DebuggingDefaults.TAB_TITLE_PREFIX + actionerIDcount);
	}
	private DebuggingActioner addTabContents( JTabbedPane parentComponent, String tabTitle){ 
		// ########################################################################################################################
		// add split panel for different view	###################################################################################
		JSplitPane sourceSplitPane = new JSplitPane(); 							// --------------
		sourceSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		parentComponent.add(tabTitle, sourceSplitPane);//, BorderLayout.CENTER);
		sourceSplitPane.setResizeWeight( DebuggingDefaults.SPLIT_SOURCE_SCALE);
		JSplitPane ptSplitPane = new JSplitPane();								// --------------
		ptSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		sourceSplitPane.setRightComponent(ptSplitPane);
		ptSplitPane.setResizeWeight( DebuggingDefaults.SPLIT_PT_SCALE);
		JSplitPane astSplitPane = new JSplitPane();								// --------------
		astSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		astSplitPane.setResizeWeight( DebuggingDefaults.SPLIT_AST_SCALE);
		JSplitPane asnSubSplitPane = new JSplitPane();							// --------------
		asnSubSplitPane.setResizeWeight( DebuggingDefaults.SUBSPLIT_ASN_SCALE);
		ptSplitPane.setRightComponent(asnSubSplitPane);
		
		// ########################################################################################################################
		// add contents to the view	###############################################################################################
		SourceDebuggingPanel sourcePanel = new SourceDebuggingPanel( sourceSplitPane, DebuggingDefaults.PANEL_SOURCE_TITLE, 
				DebuggingDefaults.COLOR1_SOURCE, DebuggingDefaults.COLOR2_SOURCE);															// --------------		
		ParserTreeDebuggingPanel ptPanel = new ParserTreeDebuggingPanel( ptSplitPane, DebuggingDefaults.PANEL_PT_TITLE, 
				DebuggingDefaults.COLOR1_PT, DebuggingDefaults.COLOR2_PT);																	// --------------
		AsnDebugginGuiPanel asnSubPanel = new AsnDebugginGuiPanel( astSplitPane, asnSubSplitPane, DebuggingDefaults.PANEL_ASN_TITLE,
				DebuggingDefaults.COLOR1_ASN, DebuggingDefaults.COLOR2_ASN, true);															// --------------
		AstDebugginGuiPanel astPanel = new AstDebugginGuiPanel( asnSubPanel, astSplitPane, asnSubSplitPane, DebuggingDefaults.PANEL_AST_TITLE, 
				DebuggingDefaults.COLOR1_AST, DebuggingDefaults.COLOR2_AST);																// --------------
		
		// prepare output
		DebuggingActioner out = new DebuggingActioner( actionerIDcount++, fileLabel, sourcePanel, ptPanel, astPanel); // set panel in which apply action effects
		parentComponent.setTabComponentAt( grammarTabCounter++, new PanelTabButton( parentComponent)); // add closing buttons to the tabs
		return out;
	}
	// add the top menu, buttons and labels.
	private void addMenuContents(final JTabbedPane tabPanels, DebuggingActioner actioner){
		// ########################################################################################################################
		// add button on top bar 	###############################################################################################
		JMenuBar menuBar = new JMenuBar();																		// --------------
		setJMenuBar(menuBar);
		JButton newGrammarBtn = new JButton( DebuggingDefaults.BTN_GRAMMAR_TITLE);								// --------------
		menuBar.add(newGrammarBtn);
		newGrammarBtn.addActionListener(new ActionListener() { // add button action
			public void actionPerformed(ActionEvent e) {
				actioners.add( addTabContents( tabPanels));	// create all the graphic for this tab and update actioners list
				tabPanelsDefault.setSelectedIndex( 0); // focus grammar mains tab
				tabPanels.setSelectedIndex( actioners.size() - 1); // focus on new grammar
			}
		});
		JButton mergeBtn = new JButton( DebuggingDefaults.BTN_MERGE_TITLE);										// -------------- 
		menuBar.add(mergeBtn);
		mergeBtn.addActionListener(new ActionListener() { // add button action
			public void actionPerformed(ActionEvent e) {
				// focus on testing tab
				tabPanelsDefault.setSelectedIndex( 1); // focus on testing tab
				// merge the grammars
				try{
					List<TextualParser> parsers = DebuggingGui.getTextualParsers();
					merger = (GrammarBase<SemanticTree>) ComposedGrammar.createGrammar( parsers);
					if( merger == null){
						JOptionPane.showMessageDialog(null, 
							"the grammar cannt be create. Are you sure to have parsed all the necessary source code succesfully?" + DebuggingDefaults.SYS_LINE_SEPARATOR
							+ "Also, check that all the rules used on the grammar are correctly defined!", 
							"Serialisation Error", JOptionPane.ERROR_MESSAGE);
					}
					SemanticExpressionNodeBase compliteRule = merger.getSemanticExpression().getRoot();
					// visualize complite grammar tree
					completeGuiPanel.visualizeTree( compliteRule);
					preabmleGuiPanel.visualizeMaps( merger);
					// set test panel for enter of input field
					testPanel.setTestObject( merger, completeGuiPanel);
				} catch( Exception ex){
					UILog.error( ex);
					UILog.error("Failure on grammars creation !!!!");
				}
			}
		});
		JButton deserialiseBtn = new JButton( DebuggingDefaults.BTN_DESERIALISE_TITLE);							// -------------- 
		menuBar.add( deserialiseBtn);
		deserialiseBtn.addActionListener(new ActionListener() { // add button action
			public void actionPerformed(ActionEvent e) {
				DeserialisationGui.createAndstartGui();
			}
		});
		menuBar.add( new JLabel(  DebuggingDefaults.ADD_TOPMENU_SPACE)); // space betrween buttons
		openBtn = JGradientButton.newInstance( DebuggingDefaults.BTN_OPEN_TITLE, 								// --------------
				DebuggingDefaults.COLOR1_SOURCE, DebuggingDefaults.COLOR2_SOURCE);								
		menuBar.add(openBtn);
		openBtn.addActionListener(new ActionListener() { // add button action
			public void actionPerformed(ActionEvent e) {
				actioners.get( tabPanels.getSelectedIndex()).openSource( DebuggingGui.this);
			}
		});
		parseBtn = JGradientButton.newInstance( DebuggingDefaults.BTN_PT_TITLE, 
				DebuggingDefaults.COLOR1_PT, DebuggingDefaults.COLOR2_PT);										// --------------
		menuBar.add( parseBtn);
		parseBtn.addActionListener(new ActionListener() { // add button action
			public void actionPerformed(ActionEvent e) {
				actioners.get( tabPanels.getSelectedIndex()).generateParsingTree();
			}
		});								
		testBtn = new JGradientBooleanButton( DebuggingDefaults.BTN_TEST_ON_TITLE, DebuggingDefaults.BTN_TEST_OFF_TITLE,
				DebuggingDefaults.COLOR1_TEST, DebuggingDefaults.COLOR2_TEST);									// --------------
		menuBar.add( testBtn);
		testPanel.setBooleanButton( testBtn);
		testBtn.addActionListener( new BooleanButtonListener( testBtn) {
			private GuiEvaluationInterface evaluator;
			@Override
			public void doOnAction(ActionEvent e) {
				evaluator = DebuggingStaticActioner.testClick(merger, testPanel, completeGuiPanel, testBtn);
			}
			@Override
			public void doOffAction(ActionEvent e) {
				if( evaluator != null){
					evaluator.stopEvaluation();
					evaluator = null; // consume evaluator
				} else {
					GuiEvaluationInterface enterEval = testPanel.getEvaluator(); // save it, since it consume the evaluator inteernally
					if( enterEval != null)
						enterEval.stopEvaluation();
				}
			}
		});
		serialiseBtn = JGradientButton.newInstance( DebuggingDefaults.BTN_SERIALISE_TITLE, 
				DebuggingDefaults.COLOR1_COMP, DebuggingDefaults.COLOR2_COMP);									// --------------
		menuBar.add( serialiseBtn);
		serialiseBtn.addActionListener(new ActionListener() { // add button action
			public void actionPerformed(ActionEvent e) {
				if( merger != null){
					DebuggingStaticActioner.serialiseClick( DebuggingGui.this, merger);
				} else UILog.error( "Nothing to serialise !!!! Be sure to load/parse/merge grammars correctly.");
			}
		});
		menuBar.add( new JLabel(  DebuggingDefaults.ADD_TOPMENU_SPACE)); // space betrween buttons				// --------------
		String labelContents = "(null)";
		if( actioner_openingPath != null)
			labelContents = DebuggingDefaults.LABEL_OPENING_TEXT + actioner_openingPath;
		fileLabel.setText( labelContents);																		
		menuBar.add( fileLabel);
	}

	/* 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ Set components and uopdate visualization on User Action ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
	 	[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]  */
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.debugging.DebuggingGui.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class contains the methods to be used when the user interact with the GUI. 
	 * Particularly, it describes the actions that can be done in the {@code Testing} tab.
	 * </p>
	 *
	 * @see DebuggingGui
	 */
	public static class DebuggingStaticActioner{
		// fields
		private static final JFileChooser fc = new JFileChooser();
		private static String serialisationPath = null;
		// static initialisation
		static{
			fc.setFileSelectionMode( JFileChooser.FILES_ONLY);
		}
		
		/**
		 * Initialie and maintain the directory path when opening file.
		 * @param stateFilePath the previous opened directory or {@code null}.
		 * @return returns the stateFilePath if it is not {@code null}. Or the default path when the application
		 * is launched from JAR ({@code JarChecker.getJarPath() + DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_JAR}) 
		 * or not ({@link it.emarolab.cagg.debugging.DebuggingDefaults#PATH_GRAMMA_DESERIALISATION_NOTJAR})
		 */
		public static String getBasePath(  String stateFilePath){
			if( stateFilePath != null){
				UILog.info( "Open old directory: " + stateFilePath);
				return stateFilePath;	// return the last opened
			} if( JarChecker.isFromJar){
				UILog.info( "Open jar default directory: " + DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_JAR);
				return JarChecker.getJarPath() + DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_JAR;		// return from jar launch
			} else {
				String out = DebuggingDefaults.PATH_GRAMMA_DESERIALISATION_NOTJAR;
				UILog.info( "Open not jar defaultDirectory: " + out);
				return out;	// return from developing launch (eclipse)
			}
		}
		
		
		/**
		 * Method called when the button {@code "Serialise"} is clicked.
		 * @param mainFrame the GUI frame in which the {@link javax.swing.JFileChooser} is linked on. 
		 * @param merger the grammar structure to be serialised.
		 * @return {@code true} if success. {@code false} otherwise.
		 */
		public static Boolean serialiseClick( JFrame mainFrame, GrammarBase<?> merger){
			fc.setCurrentDirectory( new File( getBasePath( serialisationPath))); 			// set opening directory
			int returnVal = fc.showSaveDialog( mainFrame); 				// open chooser
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				serialisationPath = fc.getSelectedFile().getAbsolutePath(); 	// update to reopen from the last location
				merger.serialise( serialisationPath);
				UILog.info( "Serialise file on: " + serialisationPath);
				return true;
			} else UILog.info( "serialisation cancelled");
			return false;
		}
		
		/**
		 * Method called when the button {@code "Test"} is clicked.
		 * @param merger the grammar structure to be tested.
		 * @param testPanel the panel from where get input and set results.
		 * @param completeGuiPanel the complete grammar tree panel to set node evaluation colors.
		 */
		public static GuiEvaluationInterface testClick( GrammarBase<?> merger, final TestDebugginGuiPanel testPanel, final SemanticTreeGuiPanel completeGuiPanel, final JGradientBooleanButton booleanButton){
			if( merger != null & testPanel != null & completeGuiPanel != null & booleanButton != null){
				final GuiEvaluationInterface evaluator = DebuggingGui.thisInstance.getEvaluator( merger);
				if( evaluator != null){
					new CaggThread( "test-stop-btn") {
						@Override
						public void implement() {
							try{
								evaluator.startAndMonitorResult( testPanel, completeGuiPanel);
								if( ! evaluator.hasBeenKilled())
									booleanButton.changeState();
							} catch( Exception e){
								UILog.error( e);
							}
						}
					}.start();
					return evaluator;
				} 
			} 
			// Change state since error
			booleanButton.changeState();
			JOptionPane.showMessageDialog( null, 
					"Error during testing. Check the log and be sure to have a valid grammar!", "PARSING ERROR", JOptionPane.ERROR_MESSAGE);
			UILog.error( "Cannot test with null input parameter !!!! Be sure to load/parse/merge them correctly.");
			return null;
		}
	}

	
	/**
	 * store statically the instance of the GUI in order to allow to implement the 
	 * method that returns the evaluator (and sets the formatter) to the user.
	 * @param thisInstance the instanced object that extends the DebuggingGui class
	 */
	public static void setThisInstance( DebuggingGui thisInstance){
		DebuggingGui.thisInstance = thisInstance;
	}
	private static DebuggingGui thisInstance;
	/**
	 * This method is called from {@link DebuggingStaticActioner#testClick(Grammar, TestDebugginGuiPanel, SemanticTreeGuiPanel)}
	 * and should return a new instance of the evaluator used in the GUI at every call.<br>
	 * By default it returns {@link cagg.core.evaluator.baseInterface.GuiSemanticEvaluator}.
	 * You can override this method to use a different evaluator on the GUI.
	 * <p>
	 * A minimal working implementation is: <br>
	 * <code>
	 * protected GUISemanticEvaluator getEvaluator( String inputStr, Grammar merger){ <br>
	 * 	BaseInputFormatter formatter = new InputFormatter( merger, inputStr); <br>
	 * 	return  new GUISemanticEvaluator( formatter); <br>
	 * }<br>
	 * </code>
	 * </p>
	 * @param formatter the object formatting the input in tree paths to be evaluated.
	 * @param merger the object contains the parsed grammars
	 * @return a new evaluator to be used in the debugging GUI.
	 */
	protected abstract GuiEvaluationInterface getEvaluator( GrammarBase<?> merger);
	
	
	/**
	 * @version Concept-Action Grammar Generator 1.1 <br>
	 * File: src//cagg.debugging.DebuggingGui.java <br>
	 *  
	 * @author Buoncompagni Luca <br>
	 * DIBRIS, emaroLab,<br> 
	 * University of Genoa. <br>
	 * 15/gen/2016 <br>
	 *  
	 * <p>
	 * This class contains the methods to be used when the user interact with the GUI. 
	 * Particularly, it describes the actions that can be done in a specific sub-tab of the {@code Grammar} tab.
	 * </p>
	 *
	 * @see DebuggingGui
	 */
	private static String actioner_openingPath = "";
	public class DebuggingActioner{
		// action variables
		private final JFileChooser fc = new JFileChooser();
		private TextualParser textualParser = null;

		// ########################################################################################################################
		// action visualization manipulation on components ########################################################################
		private String actioner_openingPath = "";
		private SourceDebuggingPanel sourcePanel;
		private ParserTreeDebuggingPanel ptPanel;
		private AstDebugginGuiPanel astPanel;
		private JLabel fileLabel;
		private Integer id;
		
		// set panel for visualization ( to be manipulated)
		/**
		 * Initalise this object by setting its fields through input parameters.
		 * @param serialId the identified of this object. Usually the index with respect to the {@code grammar} sub-tab array position.
		 * @param file the path to the source file opened to be shown in the top menu. 
		 * @param source the panel containing the source text.
		 * @param pt the panel containing the Parsing Tree. 
		 * @param ast the panel containing the Abstract Syntax Tree.
		 */
		public DebuggingActioner( Integer serialId, JLabel file, SourceDebuggingPanel source, ParserTreeDebuggingPanel pt, AstDebugginGuiPanel ast){
			id = serialId;
			fileLabel = file;
			sourcePanel = source;
			ptPanel = pt;
			astPanel = ast;
			initialise();
		}

		// ########################################################################################################################
		// set action variables ###################################################################################################
		private void initialise(){
			// set how open file
			fc.setFileSelectionMode( JFileChooser.FILES_ONLY);
			try{ // set starting opening directory
				if( ! actioner_openingPath.isEmpty()){
					UILog.info( "Opening previous directory: " + actioner_openingPath);	
				} else {
					if( JarChecker.isFromJar){
						if( JarChecker.getJarPath() != null) { // try with default location
							actioner_openingPath = JarChecker.getJarPath() + DebuggingDefaults.PATH_GRAMMAR_RELATIVE_JAR; // in jar
							UILog.info( "(jar) Open default directory: " + actioner_openingPath);
						}
					} else {
						actioner_openingPath = DebuggingDefaults.SYS_PATH_WORKING + DebuggingDefaults.PARH_GRAMMAR_RELATIVE_NOTJAR; // not from jar
						UILog.info( "(not jar) Open default directory: " + actioner_openingPath);
					}
					if ( ! Files.isDirectory( Paths.get( actioner_openingPath))) {// if the directory does not exits
						actioner_openingPath = DebuggingDefaults.PATH_GRAMMAT_ABSOLUTE; // go to home dir
						UILog.info( "Directory does not exists, open in user home");
					}
				}
			} catch( Exception e){
				UILog.error( e);
			}
		}

		// ########################################################################################################################
		// do actions #############################################################################################################
		/**
		 * Method called when a new source file is opened in a {@code Grammar} sub-tab. It shows the file picker
		 * and update the GUI; particularly the {@code source} panel given on constructor according. 
		 * @param mainFrane the GUI frame in which the {@link javax.swing.JFileChooser} is linked on.
		 */
		public void openSource( JFrame mainFrane) { 					// on openBtn click
			fc.setCurrentDirectory( new File( actioner_openingPath)); 			// set opening directory
			int returnVal = fc.showOpenDialog( mainFrane); 				// open chooser
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				actioner_openingPath = fc.getSelectedFile().getAbsolutePath(); 	// update to reopen from the last location
				openSource( actioner_openingPath);								// open source data
				UILog.info( "source tab updated!");
			} else {
				UILog.info( "load file cancelled");
			}
			if( fileLabel != null)
				fileLabel.setText( actioner_openingPath);
		}
		/**
		 * This method is called when the GUI is initialised with a source file path ({@link DebuggingGui#runGui(String)})
		 * and show the file in the {@code source} panel given on constructor according.
		 * @param filePath the source file to be opened
		 */
		public void openSource( String filePath){	// update graphic (called also if the source is opened by default (on constructor))
			UILog.info( "opening file from: " + filePath);
			sourcePanel.setText( readFile( filePath));
		}

		/**
		 * This method is called when the {@code "Parse"} button is clicked and create the parsing tree by visualising and storing the 
		 * Parsing Tree in the relative panel {@code pt} (given on constructor).<br> 
		 * Moreover, it computes (store) the Abstract Syntax Tree and Node contents and 
		 * visualise their value in the relative panel {@code ast} (given on constructor).
		 */
		public void generateParsingTree() {										// on parserBtn click
			String textToParse = sourcePanel.getText();							// get data from source tab
			textualParser = new TextualParser( textToParse, id);				// parse the file
			if( textualParser.hasError()){
				JOptionPane.showMessageDialog( null, 
					"Error during parsing. Check the log and correct the syntax of your source fils!", "PARSING ERROR", JOptionPane.ERROR_MESSAGE);
			}
			ptPanel.visualizeTree( textualParser); 								// visualise the parsing tree and store
			// call "textualParser.getListener().getAbstractSyntaxTree();" if you do not want to get the abstract syntax tree without hit the button !!!!!!
			astPanel.visualizeTree( textualParser.getListener());				// visualise the Abstract Syntax tree and store
			UILog.info( "parsing tree update, it use the data displayed in the source tab.");
		}

		// ########################################################################################################################
		// getter and useful methods (they can be null if the user does not click on relative btn) ################################
		/**
		 * @return the open grammar directory path. 
		 */
		public String getOpeningFilePath(){
			return actioner_openingPath;
		}
		/**
		 * @return the Parsing Listener attached to this source file
		 */
		public TextualParser getTextualParser(){
			return textualParser;
		}
		/**
		 * @return the Source panel attached to this source file.
		 */
		public SourceDebuggingPanel getSourcePanel() {
			return sourcePanel;
		}
		/**
		 * @return the Parsing Tree panel attached to this source file
		 */
		public ParserTreeDebuggingPanel getParsingTreePanel() {
			return ptPanel;
		}
		/**
		 * @return the Abstract Syntax Tree panel attached to this source file
		 */
		public AstDebugginGuiPanel getAbstractSyntaxTreePanel() {
			return astPanel;
		}
		/**
		 * @return the top menu path Label attached to this source panel
		 */
		public JLabel getFileLabel() {
			return fileLabel;
		}
		/**
		 * @return the source id.
		 */
		public Integer getSerialId(){
			return id;
		}
		
		// for easy debugging prints
		@Override 
		public String toString(){
			return "Actioner_" + id;
		}

		// for read all lines in a file
		private String readFile( String pathname) {
			File file = new File(pathname);
			StringBuilder fileContents = new StringBuilder((int)file.length());
			try{
				Scanner scanner = new Scanner(file);
				try {
					while(scanner.hasNextLine()) {        
						fileContents.append(scanner.nextLine() + DebuggingDefaults.SYS_LINE_SEPARATOR);
					}
					return fileContents.toString();
				} finally {
					scanner.close();
				}
			} catch (Exception e){
				UILog.error( e);
				return "";
			}
		}
	}


}

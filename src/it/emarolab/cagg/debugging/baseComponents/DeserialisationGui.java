package it.emarolab.cagg.debugging.baseComponents;

import it.emarolab.cagg.core.evaluation.CaggThread;
import it.emarolab.cagg.core.evaluation.interfacing.GuiEvaluationInterface;
import it.emarolab.cagg.core.evaluation.semanticGrammar.SemanticTree;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.SemanticExpressionTreeBase.SemanticExpressionNodeBase;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.DebuggingGui.DebuggingStaticActioner;
import it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.MapsDebugginGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.DeserialisationGui.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class implements the GUI frame that is shown on deserialisation (when the {@code Open} button is clicked).<br>
 * Firstly, it shown a file picker in order to select the data structure. Then, it deserialise it and show 
 * the {@code Testing} tab. 
 * </p>
 * 
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.MapsDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel
 */
@SuppressWarnings("serial")
public class DeserialisationGui extends JFrame {

	//////////// STATIC IMPLEMENTATION
	
	private static final JFileChooser fc = new JFileChooser();
	private static String filePath = null;	
	
	/**
	 * It instanciate this frame. Ask the user to choose a file to be deserialised and show the 
	 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel}
	 * for showing purpose.
	 * @return the created frame.
	 */
	public static void createAndstartGui() {
		EventQueue.invokeLater(new CaggThread( "deser-gui") {
			public void implement() {
				// create gui
				DeserialisationGui frame = new DeserialisationGui();
				String openingPath = "";
				// ask for input file path
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY);
				fc.setCurrentDirectory( new File( DebuggingStaticActioner.getBasePath( filePath))); 			// set opening directory
				int returnVal = fc.showOpenDialog( frame); 				// open chooser
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					openingPath = fc.getSelectedFile().getAbsolutePath(); 	// update to reopen from the last location
					filePath = openingPath;
					// deserialise
					@SuppressWarnings("unchecked")
					GrammarBase< SemanticTree> merger = (GrammarBase<SemanticTree>) GrammarBase.deserialise( openingPath);
					if( merger == null){
						JOptionPane.showMessageDialog(null, 
							"the grammar cannt be open from file. Try to create serialise it again!", 
							"Serialisation Error", JOptionPane.ERROR_MESSAGE);
					}
					//startGui( frame, merger);
					frame.setMerger( merger);
					frame.setFilePath( filePath + DebuggingDefaults.ADD_TOPMENU_SPACE);
					frame.setVisible( true);
					// log
					UILog.info( "Deserialising from: " + openingPath);
				} else {
					frame = null;
					UILog.info( "Deserialisation cancelled");
				}	
			}
		});
		//return frame;
	}
	
	//////////// NON STATIC IMPLEMENTATION
	// fields
	private GrammarBase<?> merger = null;
	private SemanticTreeGuiPanel treePanel;
	private MapsDebugginGuiPanel mapsPanel;
	private TestDebugginGuiPanel testPanel;
	private JLabel fileLabel;
	private JGradientBooleanButton testBtn;
	
	// frame constructor, called called from static method createAndstartGui()
	private DeserialisationGui() {
		// ########################################################################################################################
		// set frame	###########################################################################################################
		setTitle( DebuggingDefaults.MAINTAB2_TITLE_PREFIX);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(200, 100, (int) (dim.getWidth() * DebuggingDefaults.FRAME_SCALE * DebuggingDefaults.SUBFRAME_DIVISOR),
				(int) (dim.getHeight() * DebuggingDefaults.FRAME_SCALE * DebuggingDefaults.SUBFRAME_DIVISOR));
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// add menu
		JMenuBar menuBar = new JMenuBar();																		// --------------
		setJMenuBar(menuBar);
		testBtn = new JGradientBooleanButton( DebuggingDefaults.BTN_TEST_ON_TITLE, DebuggingDefaults.BTN_TEST_OFF_TITLE,
				DebuggingDefaults.COLOR1_TEST, DebuggingDefaults.COLOR2_TEST);									// --------------
		menuBar.add( testBtn);
		testBtn.addActionListener( new BooleanButtonListener( testBtn) {
			private GuiEvaluationInterface evaluator;
			@Override
			public void doOnAction(ActionEvent e) {
				evaluator = DebuggingStaticActioner.testClick(merger, testPanel, treePanel, testBtn);
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
		fileLabel = new JLabel();
		menuBar.add( fileLabel);
		
		// add contents
		addTabTestContents(contentPane);
	}
	
	// fill the default (test) tab with contents
	private void addTabTestContents( JPanel parentComponent){ 
		// add main vertical split for test tab panel
		JSplitPane testSplitPane = new JSplitPane(); 							// --------------
		testSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		testSplitPane.setResizeWeight( DebuggingDefaults.SPLIT_TEST_SCALE);
		parentComponent.add( testSplitPane);
		// add sub split view on the right part
		JSplitPane serialSlpitPane = new JSplitPane();
		serialSlpitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		serialSlpitPane.setResizeWeight( DebuggingDefaults.SPLIT_TEST_SCALE); // set to fit
		testSplitPane.setRightComponent( serialSlpitPane);
		// create panel to visualise complete final rule tree (lefth heand side)
		JPanel completeTreePanel = new JPanel();
		testSplitPane.setLeftComponent( completeTreePanel);
		treePanel = new SemanticTreeGuiPanel( completeTreePanel, DebuggingDefaults.PANEL_COMPLETETREE_TITLE, DebuggingDefaults.COLOR1_COMP, DebuggingDefaults.COLOR2_COMP);
		// create panel to show the preamble map (right bottom corner)
		JPanel preamblePanel = new JPanel();
		serialSlpitPane.setRightComponent( preamblePanel);
		mapsPanel = new MapsDebugginGuiPanel( preamblePanel, DebuggingDefaults.PANEL_PREAMBLE_TITLE, DebuggingDefaults.COLOR1_COMP, DebuggingDefaults.COLOR2_COMP);
		// create panel to test the system and serialise the resulting structure (right top corner)
		JPanel testMainPanel = new JPanel();
		testPanel = new TestDebugginGuiPanel( testMainPanel, DebuggingDefaults.PANEL_TEST_TITLE, DebuggingDefaults.COLOR1_TEST, DebuggingDefaults.COLOR2_TEST);
		testPanel.setBooleanButton( testBtn);
		serialSlpitPane.setLeftComponent( testMainPanel);
		serialSlpitPane.setResizeWeight( DebuggingDefaults.SUBSPLIT_TEST_SCALE);
		
	}
	
	// set the deserialisd grammar 
	private void setMerger( GrammarBase< SemanticTree> merger){
		this.merger = merger;
		this.updateContents( merger);
		testPanel.setTestObject(merger, treePanel); // set for test input field click
	}
	// set the path to the file to deserialise
	private void setFilePath( String filePath){
		this.fileLabel.setText(filePath);
	}
	
	// update visualising contents
	private void updateContents( GrammarBase<SemanticTree> merger){
		if( merger != null){
			SemanticExpressionNodeBase compliteRule = merger.getSemanticExpression().getRoot();
			// visualize complite grammar tree
			treePanel.visualizeTree( compliteRule);
			mapsPanel.visualizeMaps( merger);
		} else UILog.error( "cannot add contents to the gui.");
	}
}

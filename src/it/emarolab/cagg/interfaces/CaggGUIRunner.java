package it.emarolab.cagg.interfaces;

import it.emarolab.cagg.core.evaluation.CaggThread;
import it.emarolab.cagg.core.evaluation.GuiEvaluator;
import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.semanticGrammar.syntaxCompiler.GrammarBase;
import it.emarolab.cagg.debugging.DebuggingGui;
import it.emarolab.cagg.debugging.UILog;

import java.awt.EventQueue;

@SuppressWarnings("serial")
public class CaggGUIRunner extends DebuggingGui{

	// Launch the GUI..... [????? DOC]
	public static void runGui(){
		runGui( null, null); 
	}
	public static void runGui( String threadName){
		runGui( threadName, null);
	}
	public static void runGui( String threadName, String loggerName){
		// initialise if it is running from jar Boolean
		new JarChecker();
		// define a separated thread to create the GUI
		CaggThread t = new CaggThread( threadName, loggerName) { // null for defaults
			public void implement() {
				// create the GUI
				UILog.setLoggers( this.getThreadLogger());
				CaggGUIRunner frame = new CaggGUIRunner(); 
				frame.setVisible(true);
				// set the GUI on a static variable to be used for testing procedure
				// the testing objects are than set from the getEvaluator(..) method
				DebuggingGui.setThisInstance( frame); 
			}
		};
		// run the gui 
		EventQueue.invokeLater( t);
	}

	// change this implementation (this must implement GUISemanticEvaluator) 
	// if you want to use a specific evaluator and formatter on the GUI.
	@Override
	protected GuiEvaluator getEvaluator( GrammarBase<?> merger){
		ThreadedInputFormatter formatter;
		formatter = new ThreadedInputFormatter( merger);
		GuiEvaluator evaluator = new GuiEvaluator( formatter); // run evaluation on constructor
		return evaluator;
	}	
}
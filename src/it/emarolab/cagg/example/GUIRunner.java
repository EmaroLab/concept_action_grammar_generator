package it.emarolab.cagg.example;

import com.sun.org.apache.xpath.internal.operations.Bool;

import it.emarolab.cagg.debugging.CaggLoggersManager;
import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.DebuggingText;
import it.emarolab.cagg.debugging.DebuggingText.Logger;
import it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel;
import it.emarolab.cagg.interfaces.CaggGUIRunner;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.example.SemanticParserGUIRunner.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class runs the Graphical User Interface (GUI).
 * This is the main class used to show the system through JAR runner
 * </p><p>
 * It purpose is to easily interact with the system in order to debug your grammar
 * and create (by serialising) the necessary data to use it efficiently by API.<br>  
 * </p>
 * 
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.DebuggingText
 * @see it.emarolab.cagg.debugging.DebuggingDefaults 
 */
@SuppressWarnings("unused")
public class GUIRunner{

	/**
	 * This is a main function running the GUI with the default value specified in 
	 * {@link it.emarolab.cagg.debugging.DebuggingDefaults}. 
	 * 
	 * @param args (not used!)
	 */
	public static void main(String[] args) {
		new CaggLoggersManager(); // initialise simple logger map with default name w.r.t. log4j2 configuration file
		CaggGUIRunner.runGui( "main-gui"); // run GUI from helper interface 
	}
}

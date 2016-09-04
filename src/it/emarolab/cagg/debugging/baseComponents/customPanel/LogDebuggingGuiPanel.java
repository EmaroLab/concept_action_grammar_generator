package it.emarolab.cagg.debugging.baseComponents.customPanel;

import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;
import it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel;
import it.emarolab.cagg.debugging.baseComponents.JLog4j2Appender;
import it.emarolab.cagg.debugging.baseComponents.LoggingTable;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class extends {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel}
 * to implement the panel to show system logs through the {@link it.emarolab.cagg.debugging.DebuggingText.Logggger} 
 * object. 
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.DebuggingText
 * @see it.emarolab.cagg.debugging.DebuggingText.ConsoleLogRidirector
 * @see it.emarolab.cagg.debugging.DebuggingText.Logggger
 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel
 */
@SuppressWarnings("serial")
public class LogDebuggingGuiPanel extends BaseDebugginGuiPanel{

	// private class fields
	private static Log4jTable loggingTable;

	// default constructors
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public LogDebuggingGuiPanel(JSplitPane parent, String title, Color c1, Color c2) {
		super(parent, title, c1, c2);
	}
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JSplitPane, String, Color, Color, Boolean)}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 * @param isRight set to {@code true} if this panel should be placed as a right child of the parent {@code JSplitPane}. 
	 * {@code false} otherwise.
	 */
	public LogDebuggingGuiPanel(JSplitPane parent, String title, Color c1, Color c2, Boolean isRight) {
		super(parent, title, c1, c2, isRight);
	}
	/**
	 * Just calls its super class constructor with the relative input parameters:
	 * {@link it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#BaseDebugginGuiPanel(JPanel, String, Color, Color)}.
	 * @param logMainPanel the {@code JPanel} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public LogDebuggingGuiPanel( JPanel logMainPanel, String title, Color c1, Color c2) {
		super(logMainPanel, title, c1, c2);
	}

	// set the custom visualisation
	/**
	 * In this panel the layout of the {@code contentPanel} is set to {@code BoxLayout(contentPanel, BoxLayout.PAGE_AXIS)}.
	 * Than, it sets the {@code contentScrollPane} to never scroll horizontally for text line wrap.
	 * Finally, adds a {@link javax.swing.JTextArea} in which show the logs and set its behavior.
	 * @see it.emarolab.cagg.debugging.baseComponents.BaseDebugginGuiPanel#setContents()
	 */
	@Override
	public void setContents(){  // called in the super class constructor
		super.setContents();
		// set the scroll
		//contentScrollPane.setHorizontalScrollBarPolicy( HORIZONTAL_SCROLLBAR_NEVER);

		// set content panel (it is inside the scroll)
		contentPanel.setLayout( new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

		// set log container
		loggingTable = new Log4jTable();
		contentPanel.add( loggingTable);

		// Subscribe the text area to JTextAreaAppender
		JLog4j2Appender.addLogArea( loggingTable);
	}

	/// utility class to define the logging table
	private static Map< String, Color> threadColorMap = new ConcurrentHashMap<>(); // to make random color unique for threads
	private static Map< String, Color> logColorMap = new ConcurrentHashMap<>();  // to make random color unique for logging name
	static{ // in order to do not generate color close to the defoult once
		try{
			threadColorMap.putIfAbsent( LoggingTable.LEVEL_OK_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			threadColorMap.putIfAbsent( LoggingTable.LEVEL_INFO_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			threadColorMap.putIfAbsent( LoggingTable.LEVEL_WARNING_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			threadColorMap.putIfAbsent( LoggingTable.LEVEL_ERROR_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			threadColorMap.putIfAbsent( "DeFoLt", LoggingTable.DEFOULT_BACKGROUND);
			
			logColorMap.putIfAbsent( LoggingTable.LEVEL_ERROR_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			logColorMap.putIfAbsent( LoggingTable.LEVEL_WARNING_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			logColorMap.putIfAbsent( LoggingTable.LEVEL_OK_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			logColorMap.putIfAbsent( LoggingTable.LEVEL_INFO_TAG, getLevelColor( LoggingTable.LEVEL_OK_TAG));
			logColorMap.putIfAbsent( "DeFoLt", LoggingTable.DEFOULT_BACKGROUND);
		}catch( NullPointerException e){
			e.printStackTrace();
		}
	}
	private static Font getLevelFont( String level){
		switch ( level.trim()) { // w.r.t. DebuggingDefoults ?!?!?!?!?
		case LoggingTable.LEVEL_OK_TAG		: return DebuggingDefaults.DEFAULT_GUI_FONT_BOLD;
		case LoggingTable.LEVEL_INFO_TAG	: return DebuggingDefaults.DEFAULT_GUI_FONT;
		case LoggingTable.LEVEL_WARNING_TAG : return DebuggingDefaults.DEFAULT_GUI_FONT_ITALIC;
		case LoggingTable.LEVEL_ERROR_TAG	: return DebuggingDefaults.DEFAULT_GUI_FONT_BOLD;
		default:
			UILog.error( "Cannot format msg log on table for level: " + level);
			return null;
		}
	}
	private static Color getLevelColor(String level) {
		switch ( level.trim()) { // w.r.t. DebuggingDefoults ?!?!?!?!?
		case LoggingTable.LEVEL_OK_TAG		: return Color.green.brighter();
		case LoggingTable.LEVEL_INFO_TAG	: return Color.cyan;
		case LoggingTable.LEVEL_WARNING_TAG : return Color.yellow;
		case LoggingTable.LEVEL_ERROR_TAG	: return Color.orange;
		default:
			UILog.error( "Cannot format msg log on table for level: " + level);
			return null;
		}
	}
	public class Log4jTable extends LoggingTable{
		// static settings of the table
		@Override
		protected String[] getColumnsName() { // getColumnsName( int) implemented by defoult
			return new String[]{ "TIME", "THREAD", "CLASS", "LEVEL", "LOGGER", "MESSAGE"}; // it has to be static
		}
		// helper fields retrievers
		public String getLoggerName( Object[] msg){
			return msg[ 4].toString().replaceAll(" ", "").trim();
		}
		public String getLevel( Object[] msg){
			return msg[ 3].toString().replaceAll(" ", "").trim();
		}
		public String getThread( Object[] msg){
			return msg[ 1].toString().replaceAll(" ", "").trim();
		}

		// text interface
		@Override
		protected Object[] parseMessage(String msg) {
			// compose the split policy. (ex: COLUMN_SEPARATOR = "-|-" => splitter = "\\-\\|\\-\\s*")
			String splitter = "";
			for( int i = 0; i < COLUMN_SEPARATOR.length(); i++)
				splitter += "\\" + COLUMN_SEPARATOR.charAt( i);
			splitter += "\\s*";
			// split message and create raw object to be returned
			String[] tokens = msg.split( splitter);
			Object[] out = new Object[ tokens.length];
			for( int i = 0; i < tokens.length; i++)
				out[ i] = tokens[i];
			return out;
		}

		// format interface
		@Override
		protected synchronized List<CellFormatter> formatRow(Object[] msg) {
			// set the font with respect to level
			String level = getLevel( msg);
			// set font for all the message
			Font font = getLevelFont( level);
			// set background color
			Color threadBackground = newColor( getThread( msg), threadColorMap, false);
			Color levelBackground = getLevelColor( level);
			Color logBackground = newColor( getLoggerName( msg), logColorMap, true);
			// set text color
			Color threadText = Color.white;
			Color levelText = Color.black;
			Color logText = Color.black;
			// format the row
			List< CellFormatter> formatted = Collections.synchronizedList( new ArrayList< CellFormatter>());
			synchronized (formatted) {
				formatted.add( new CellFormatter( threadBackground, threadText, font)); // format the time column
				formatted.add( new CellFormatter( threadBackground, threadText, font)); // format the thread column
				formatted.add( new CellFormatter( threadBackground, threadText, font)); // format the class column
				formatted.add( new CellFormatter( levelBackground, levelText, font)); // format the level column
				formatted.add( new CellFormatter( logBackground, logText, font)); // format the logger name column
				formatted.add( new CellFormatter( logBackground, logText, font)); // format the message column
			}
			return formatted;
		}
		private Color newColor(String key, Map<String, Color> colorMap, boolean light) { // false dark
			if( colorMap.containsKey( key))
				return colorMap.get( key);
			// otherwise create it
			int cnt = 0;
			Color c = getRandomColor( light, colorMap, key);  // it internally updates colorMap
			// spin until a new color is found or limit is reached
			while( ! colorMap.containsKey( key)){
				// get if max number of temptative is reached
				if(  cnt++ >= RANDOM_COLOR_TEMPTATIVE_LIMIT){
					UILog.warning( "Random color generator reached its searching limit: " + key + " (" + cnt + ")");
					return DEFOULT_BACKGROUND;
				}
				c = getRandomColor( light, colorMap, key); // it internally updates colorMap
			}			
			return c;
		}
		private boolean containsColor( Map< String, Color> colorMap, Color newColor){ // true if c is similar to a color in the map
			for( Color usedColor : colorMap.values())
				if( similarTo( usedColor, newColor))
					return true;
			return false;
		}
		private boolean similarTo(Color c1, Color c2){
			// get HUE component of c1
			float[] c1HSB = new float[3];
			Color.RGBtoHSB( c1.getRed(), c1.getGreen(), c1.getBlue(), c1HSB);
			// get HUE component of c2	 
			float[] c2HSB = new float[3];
			Color.RGBtoHSB( c2.getRed(), c2.getGreen(), c2.getBlue(), c2HSB);
			// compute distances
			float distH = c1HSB[ 0] - c2HSB[ 0];
			float distS = c1HSB[ 1] - c2HSB[ 1];
			float distB = c1HSB[ 2] - c2HSB[ 2];
			float dist = distH * distH + distS * distS + distB * distB;
			if( dist < COLOR_SIMILARITY_THRESHOULD)
				return true;
			return false;
		}
		private Color getRandomColor( Boolean light, Map< String, Color> colorMap, String key){
			// get a new ligh or dark color
			Color c;
			if( light)
				c = new Color( Color.HSBtoRGB( rdm(), rdm(), rdm( COLOR_LIGHT_RANGE[ 0], COLOR_LIGHT_RANGE[ 1]))); // light color
			else c = new Color( Color.HSBtoRGB( rdm(), rdm(), rdm( COLOR_DARK_RANGE[ 0], COLOR_DARK_RANGE[ 1]))); // dark color
			// update color map
			if( ! containsColor( colorMap, c)){ // store the new color
				colorMap.putIfAbsent( key, c);
			}
			return c;
		}
		private Random random = new Random();
		private float rdm(){
			return rdm( 0.0f, 1.0f);
		}
		private float rdm( float min, float max){
			return random.nextFloat() * ( max - min) + min;
		}
	}


	public static void addLogContents(String log) {
		loggingTable.addContents( log);
	}	
}

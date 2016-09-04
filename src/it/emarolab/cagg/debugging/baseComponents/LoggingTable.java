package it.emarolab.cagg.debugging.baseComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import it.emarolab.cagg.debugging.DebuggingDefaults;
import it.emarolab.cagg.debugging.UILog;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public abstract class LoggingTable extends JPanel{

	/* ##################################################################################
	   ############################# CONSTANTS ##########################################
	 */
	public static final String COLUMN_SEPARATOR = "-|-"; // char that can be specified on the log4j xml file to separate fields as: <PatternLayout pattern="%rms -|- %t -|- %C -|- %p -|- %c -|- %msg" />
	// Useful logging constants
	public static final String LEVEL_OK_TAG = "DEBUG";
	public static final String LEVEL_INFO_TAG = "INFO";
	public static final String LEVEL_WARNING_TAG = "WARN";
	public static final String LEVEL_ERROR_TAG = "ERROR";
	// usefull apparence constants
	public static final Color DEFOULT_BACKGROUND = Color.white;
	public static final Color DEFOULT_TEXT_COLOR = Color.black;
	public static final Font DEFOULT_FONT = DebuggingDefaults.DEFAULT_GUI_FONT;
	public static final Float COLOR_SIMILARITY_THRESHOULD = 0.1f;
	public static final Float[] COLOR_LIGHT_RANGE = new Float[] { 0.85f, 0.95f}; // in 0,1 the frsit must be the lower! Referring to the lightness component in HSV representation
	public static final Float[] COLOR_DARK_RANGE = new Float[] { 0.05f, 0.55f}; // in 0,1 the frsit must be the lower! Referring to the lightness component in HSV representation
	public static final int RANDOM_COLOR_TEMPTATIVE_LIMIT = 5000;

	/* ##################################################################################
	   ############################## FIELDS ############################################
	 */
	private DefaultTableModel model;
	private int rowCount = 0;
	private JTable table;
	private List< List< CellFormatter>> formatMap = Collections.synchronizedList(new ArrayList< List< CellFormatter>>()); // for each cell of the table contains the background, text color and font setting (raw< column>)

	/* ##################################################################################
	   ############################# CONSTRUCTOR ########################################
	 */
	// constructor to visually create the table (empty) and header
	public LoggingTable(){
		// initialise
		model = new DefaultTableModel( getColumnsName(), 0);
		table = new LogJTable( getTableModel());

		// add contents
		addTable();
	}
	// called on constructor to set THIS panel
	protected void addTable(){
		setLayout(new BorderLayout()); // set layout
		add(table, BorderLayout.CENTER); // add table
		addHeader(); // add header
	}
	protected void addHeader(){
		JTableHeader header = table.getTableHeader();
		header.setBackground( DebuggingDefaults.COLOR_LOG_HEADER);
		add( header, BorderLayout.NORTH);
	}
	// interface
	protected abstract String[] getColumnsName(); // it should be constants (used on constructor)

	/* ##################################################################################
	   ############################### GETTERS ##########################################
	 */
	protected DefaultTableModel getTableModel(){
		return model;
	}
	protected JTable getTable(){
		return table;
	}
	public int getMsgCount(){
		return rowCount;
	}
	protected String getColumnsName( int idx){
		return getColumnsName()[ idx];
	}
	public int getColumnsCount(){
		return getColumnsName().length;
	}
	protected List<List<CellFormatter>> getFormatMap() {
		return formatMap;
	}

	/* ##################################################################################
	   ############################## APPENDERS #########################################
	 */
	// methods to add logs message as wow in the table
	public synchronized void addContents( Object[] row){
		if( row.length != getColumnsCount())
			System.err.println( "TableLogger should have row data of the same size of culumns names: (" + Arrays.asList(row) + " != " + getColumnsCount() + ")");
		try{
			getTableModel().addRow( row); // show contents
			rowCount += 1; 
			formatMap.add( formatRow( row)); // save cell formatting objects
		} catch( Exception e){
			UILog.error( e);
		}
	}
	public void addContents( String msg){	
		Object[] contents = parseMessage( msg);
		addContents( contents);
	}
	// interfaces
	protected abstract Object[] parseMessage( String msg); // for appending a new row
	protected abstract List< CellFormatter> formatRow( Object[] msg); // to format a new row

	/* ##################################################################################
	   ############################ UTILITY CLASS #######################################
	   utility class to set graphical behavior of the logging JTable through the 
	   abstract method formatRow() and the formatter object formatMap 					*/
	public class LogJTable extends JTable{
		// default constructors
		public LogJTable(TableModel dm, TableColumnModel cm) {
			super(dm, cm);
			initialise();
		}
		public LogJTable(TableModel dm) {
			super(dm);
			initialise();
		}
		private void initialise(){
			this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.setFont( DebuggingDefaults.DEFAULT_GUI_FONT);
			this.getTableHeader().setReorderingAllowed(false);
			// set the table to be selectable but not editable
			this.setAutoCreateRowSorter(true);
	        this.setRowSelectionAllowed(true);
	        this.setCellSelectionEnabled(true);
		}
		// to change row color
		@Override
		public synchronized Component prepareRenderer( TableCellRenderer renderer, int row, int column){
			Component c = super.prepareRenderer( renderer, row, column);

			// formatting cell color and font
			CellFormatter formatter = formatMap.get( row).get( column); // get the formatting property from the map
			c.setBackground( formatter.getBackground()); 
			c.setForeground( formatter.getTextColor());
			c.setFont( formatter.getFont());

			// to control column width
			int rendererWidth = c.getPreferredSize().width;
			TableColumn tableColumn = getColumnModel().getColumn( column);
			tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

			return c;	    
		}		
		
		// to manage selectable and editable
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
	}

	/* ##################################################################################
	   ############################ UTILITY CLASS #######################################
	   to contain all the data to format a single cell of the table 					*/
	public class CellFormatter{
		/// fields
		private Color background = DEFOULT_BACKGROUND;
		private Color textColor = DEFOULT_TEXT_COLOR;
		private Font font = DEFOULT_FONT;
		/// constructor
		public CellFormatter() {}
		public CellFormatter( Color background, Color textColor, Font font) {
			setBackground(background);
			setTextColor(textColor);
			setFont(font);
		}
		/// setters
		protected void setBackground(Color background) {
			if( background != null)
				this.background = background;
			else UILog.warning( "CellFormatter recieve a null background");
		}
		protected void setTextColor(Color textColor) {
			if( textColor != null)
				this.textColor = textColor;
			else UILog.warning( "CellFormatter recieve a null text color");
		}
		protected void setFont(Font font) {
			if( font != null)
				this.font = font;
			else UILog.warning( "CellFormatter recieve a null font");
		}
		// getters
		public Color getBackground() {
			return background;
		}
		public Color getTextColor() {
			return textColor;
		}
		public Font getFont() {
			return font;
		}
		// interface
		@Override
		public String toString(){
			return this.getClass().getSimpleName() + "{ font:" + font + ", color=" + textColor + ", background=" + background;
		}
	}
}

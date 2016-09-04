package it.emarolab.cagg.debugging.baseComponents;

import it.emarolab.cagg.debugging.DebuggingDefaults;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.BaseDebugginGuiPanel.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class implements a base structure for all the custom panels located in package:
 * {@link it.emarolab.cagg.debugging.baseComponents.customPanel}. 
 * Thus, it is never intanciated directly.<br>
 * Those types of panels are aimed to be located into a {@link javax.swing.JSplitPane} which is also given as
 * input in the constructor. It is important to remark that this object automatically add it self into its parent.<br>
 * Moreover, the panels contain a title composed by a button that is used to maximise and minimise
 * the contents in the parent {@code JSplitPane}.<br>
 * Also, they contains a {@link javax.swing.JScrollPane} focused on a {@code contentPanel} ({@link javax.swing.JPanel}) 
 * which is the place where the extending objects should place their contents.
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingGui
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AsnDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.AstDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SemanticTreeGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.LogDebuggingGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.MapsDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.ParserTreeDebuggingPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SourceDebuggingPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.SubSplitDebuggingGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.customPanel.TestDebugginGuiPanel
 * @see it.emarolab.cagg.debugging.baseComponents.JGradientButton
 */
@SuppressWarnings("serial")
public class BaseDebugginGuiPanel extends JPanel {
	
	private JButton titleBtn;
	private Boolean expand = true;
	/**
	 * The panel in which the extending class should place their contents.
	 */
	protected JPanel contentPanel;
	/**
	 * A scroll panel focused on {@link #contentPanel}.
	 */
	protected JScrollPane contentScrollPane;
	/**
	 * The top gradient color of this panel.
	 */
	protected Color color1;
	/**
	 * The bottom gradient color of this panel.
	 */
	protected Color color2;
	
	/**
	 * Create a new left panel by calling: {@link #BaseDebugginGuiPanel(JSplitPane, String, Color, Color, Boolean)},
	 * with {@code isRight} parameter set to {@code false}.
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label contents to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public BaseDebugginGuiPanel( final JSplitPane parent, String title, Color c1, Color c2){
		initialise( parent, title, c1, c2, false);
	}
	/**
	 * Create a new panel. 
	 * @param parent the {@link javax.swing.JSplitPane} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 * @param isRight set to {@code true} if this panel should be placed as a right child of the parent {@code JSplitPane}. 
	 * {@code false} otherwise.
	 */
	public BaseDebugginGuiPanel( final JSplitPane parent, String title, Color c1, Color c2, Boolean isRight){
		initialise( parent, title, c1, c2, isRight);
	}
	private void initialise( final JSplitPane parent, String title, Color c1, Color c2, Boolean isRight){
		setLayout(new BorderLayout(0, 0));
		// set title and title click behavior
		titleBtn = JGradientButton.newInstance( title, c1, c2);
		titleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collapseExpand( parent);
				expand = !expand;
				perfromOnTitleClick( e);
			}
		});
		add( titleBtn, BorderLayout.NORTH);
		// set split, content panel etc..
		initialiseContents( c1, c2);
		// set the panel to the parent (left or right splitter)
		if( parent != null){ 
			if( isRight){
				parent.setRightComponent( this);
				expand = ! expand;
			} else parent.setLeftComponent( this);
		}		
	}
	
	
	/**
	 * Create a new panel placed on a {@link javax.swing.JPanel} instead onto a {@link javax.swing.JSplitPane}.
	 * This constructor makes this object not performing panel collapsing and expanding on title click.
	 * @param parent the {@code JPanel} containing this {@link BaseDebugginGuiPanel} object.
	 * @param title the label to be place on top of this object.
	 * @param c1 the top gradient color of this object.
	 * @param c2 the bottom gradient color of this object.
	 */
	public BaseDebugginGuiPanel(final JPanel parent, String title, Color c1, Color c2) {
		setLayout(new BorderLayout(0, 0));
		parent.setLayout(new BorderLayout(0, 0));
		// set title and title click behavior
		titleBtn = JGradientButton.newInstance( title, c1, c2);
		titleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				perfromOnTitleClick( e);
			}
		});
		add( titleBtn, BorderLayout.NORTH);
		// set split, content panel etc..
		initialiseContents( c1, c2);
		// add to the parent
		parent.add( this);
	}
	
	private void initialiseContents( Color c1, Color c2){
		// set scroll pane
		contentScrollPane = new JScrollPane();
		contentScrollPane.getVerticalScrollBar().setUnitIncrement( DebuggingDefaults.SCROOL_INCREMENT);
		contentScrollPane.getHorizontalScrollBar().setUnitIncrement( DebuggingDefaults.SCROOL_INCREMENT);
		add( contentScrollPane);
		// set main panel in side a scroll pane
		contentPanel = new JPanel();
		contentScrollPane.setViewportView( contentPanel);
		// set the color
		this.color1 = c1;
		this.color2 = c2;
		// custom extension contents
		setContents(); 
	}
	
	/**
	 * Given a parent split panel as inputs it use its internal state for expand or collapse the right or left
	 * child and vice-versa.
	 * @param pane the JSplitPane to be expanded or collapsed based on panel tile click. 
	 */
	// title button action click
	protected void collapseExpand( JSplitPane pane){
		if( pane != null){
			if( expand){
				// hide left or top
				pane.getLeftComponent().setMinimumSize(new Dimension());
				pane.setDividerLocation( DebuggingDefaults.SPLIT_COLLAPSE_SCALE);
			} else {
				// Hide right or bottom
				pane.getRightComponent().setMinimumSize(new Dimension());
				pane.setDividerLocation( DebuggingDefaults.SPLIT_EXPAND_SCALE);
			}
		}
	}
	
	// for extending classes
	/**
	 * @return the button attched to the title of this panel
	 */
	protected JButton getTitleBtn(){
		return this.titleBtn;
	}
	
	/**
	 * Clear all the contents by calling {@code contentPanel.removeAll()} and {@code contentPanel.repaint()}.<br>
	 * This call does not affect the {@code JSplitPane} related to this object.
	 */
	public void removeContents(){
		contentPanel.removeAll();
		contentPanel.repaint();	
	}
	
	/**
	 * Update all the contents by calling {@code contentPanel.revalidate()}, {@code contentScrollPane.revalidate()}
	 * and {@code this.revalidate()}.<br>
	 * This call does not affect the {@code JSplitPane} related to this object.
	 */
	public void updateContents(){
		contentPanel.revalidate();
		contentScrollPane.revalidate();
		this.revalidate();
	}
	
	/**
	 * This method automatically sets {@code contentPanel.setLayout(new BorderLayout(0, 0));} and it is 
	 * called at the and of the each constructors.  Its purpose is to be overwritten from the 
	 * extending class in case in which the {@code contentPanel} should be customised. 
	 */
	protected void setContents(){
		// called during class constructor
		// it does not do nothing by default (to be implemented in extensions)
		// in order to costumes the view
		// remember to do it not in this.panel but in contentPanel object !!!!!!!!
		contentPanel.setLayout(new BorderLayout(0, 0));
	}
	
	/**
	 * This method does not do any computations and it is 
	 * called at when the title is clicked by the user.  Its purpose is to be overwritten from the 
	 * extending class in case in which the behavior should be customised. Note that, if parent of this class is a 
	 * JSplitPnale (thus instantiated by using either: {@link #BaseDebugginGuiPanel(JSplitPane, String, Color, Color, Boolean)} or
	 * {@link #BaseDebugginGuiPanel(JSplitPane, String, Color, Color)}) this method is called after that the collapsing/expanding
	 * behaviour is performed.
	 * @param e the object to be used to handle user click interaction.
	 */
	protected void perfromOnTitleClick(ActionEvent e){
	}
	
	
	/**
	 * Add a separator to the {@link #contentPanel}.
	 */
	protected void addSeparator(){
		addSeparator( this.contentPanel);
	}
	/**
	 * Add a {@link javax.swing.JSeparator} to the given panel.
	 * @param panel the panel in which the separator should be added.
	 */
	protected static void addSeparator( JPanel panel){
		JSeparator separator = new JSeparator();
		panel.add(separator);
	}
	
	/**
	 * Add a {@link javax.swing.JLabel} to the {@link #contentPanel}.
	 * @param txt the text to be introduced into the label.
	 */
	protected void addLabel( String txt){
		addLabel(txt, this.contentPanel);
	}
	/**
	 * Add a {@link javax.swing.JLabel} to the given panel.
	 * @param txt the text to be introduced into the label.
	 * @param panel the panel in which the label should be added.
	 */
	protected static void addLabel( String txt, JPanel panel){
		JLabel label = new JLabel( txt);
		panel.add( label);
		label.setAlignmentX(JLabel.CENTER);//.LEFT);
	}
}





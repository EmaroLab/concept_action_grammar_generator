package it.emarolab.cagg.debugging.baseComponents;

import it.emarolab.cagg.debugging.DebuggingGui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.*;


/**
 * @version Concept-Action Grammar Generator 1.1 <br>
 * File: src//cagg.debugging.baseComponents.PanelTabButton.java <br>
 *  
 * @author Buoncompagni Luca <br>
 * DIBRIS, emaroLab,<br> 
 * University of Genoa. <br>
 * 15/gen/2016 <br>
 *  
 * <p>
 * This class is adapted from the: 
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-TabbedPaneDemoProject.zip">components.TabbedPaneDemo</a> 
 * class, distributed from Oracle through the {@literal "How to Use Tabbed Panes"} tutorial, available at:
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html">https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html</a>.  
 * </p>
 *
 * @see it.emarolab.cagg.debugging.DebuggingGui
 */
@SuppressWarnings("serial")
public class PanelTabButton extends JPanel {
    private final JTabbedPane pane;
    
    public PanelTabButton(final JTabbedPane pane) {
    	//unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;

    	setOpaque(false);
                
        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(PanelTabButton.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        
        add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton button = new TabButton();
        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        /**
         * Here there is the only change with respect to the referring tutorial since
         * it does not only remove the tab but also calls {@link it.emarolab.cagg.debugging.DebuggingGui#removeActioner( int)}
         * where the input parameter is the index of the table computed as
         * {@code pane.indexOfTabComponent( PanelTabButton.this)}.
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         **/
        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent( PanelTabButton.this);
            if (i > 0){ // do not delete the tab 0 !!!! //(i != -1) {
                pane.remove( i);
                DebuggingGui.removeActioner( i);
            }
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}


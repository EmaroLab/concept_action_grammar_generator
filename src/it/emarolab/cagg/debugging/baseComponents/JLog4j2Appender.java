package it.emarolab.cagg.debugging.baseComponents;

import it.emarolab.cagg.core.evaluation.CaggThread;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

// ref: http://stackoverflow.com/questions/24005748/how-to-output-logs-to-a-jtextarea-using-log4j2

@Plugin(name = "JLog4j2Appender", category = "Core", elementType = "appender", printObject = true)
public class JLog4j2Appender extends AbstractAppender {

	private static volatile List< LoggingTable> jTextAreaList = new ArrayList<>();
	
	protected JLog4j2Appender(String name, Layout<?> layout, Filter filter, boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions);
	}

	@PluginFactory
	public static JLog4j2Appender createAppender(@PluginAttribute("name") String name,
			@PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
			@PluginElement("Layout") Layout<?> layout,
			@PluginElement("Filters") Filter filter) {

		if (name == null) {
			LOGGER.error("No name provided for JTextAreaAppender");
			return null;
		}

		if (layout == null) {
			layout = PatternLayout.createDefaultLayout();
		}
		return new JLog4j2Appender(name, layout, filter, ignoreExceptions);
	}

	// Add the target JTextArea to be populated and updated by the logging information.
	public static void addLogArea(final LoggingTable textArea) {
		JLog4j2Appender.jTextAreaList.add( textArea);
	}

	@Override
	public void append(LogEvent event) {
		final String message = new String(this.getLayout().toByteArray(event));

		// Append formatted message to text area using the Thread.
		try {
			SwingUtilities.invokeLater(new CaggThread( "log4j-gui-appender") {
				@Override
				public void implement() {
					for ( LoggingTable tbl : jTextAreaList){
						try {
							tbl.addContents( message);
						} catch (final Throwable t) {
							this.logError( "Unable to append log to text area: " + t.getMessage());
							this.logError( t.getLocalizedMessage());
						}
					}
				}
			});
		} catch (final IllegalStateException e) {
			// ignore case when the platform hasn't yet been iniitialized
		}
	}

}
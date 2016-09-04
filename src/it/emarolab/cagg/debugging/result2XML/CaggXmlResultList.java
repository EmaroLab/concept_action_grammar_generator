package it.emarolab.cagg.debugging.result2XML;

import it.emarolab.cagg.core.evaluation.inputFormatting.ThreadedInputFormatter;
import it.emarolab.cagg.core.evaluation.interfacing.ComposedGrammar;
import it.emarolab.cagg.core.evaluation.interfacing.EvaluatorBase;
import it.emarolab.cagg.debugging.DebuggingDefaults;

import java.util.ArrayList;
import java.util.List;

public class CaggXmlResultList {

	/* ##################################################################################
	   ################################ FIELDS ##########################################
	 */
	private List< CaggXmlResult> trueResult;

	/* ##################################################################################
	   ############################## CONSTRUCTOR #######################################
	 */
	public CaggXmlResultList(){} // for xml interface

	public void initialise( List< EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults> results){
		this.trueResult = new ArrayList< CaggXmlResult>();
		for( EvaluatorBase<ComposedGrammar, ThreadedInputFormatter>.EvaluationResults r : results){
			CaggXmlResult xmlResult = new CaggXmlResult();
			xmlResult.initialise( r);
			this.trueResult.add( xmlResult);
		}
	}

	/* ##################################################################################
	   ################################ GETTERS #########################################
	 */
	public List<CaggXmlResult> getTrueResult() {
		return trueResult;
	}

	/* ##################################################################################
	   ################################ SETTERS #########################################
	 */
	public void setTrueResult(List<CaggXmlResult> results) {
		this.trueResult = results;
	}

	@Override
	public String toString() {
		String res = "[";
		for( int i = 0; i < trueResult.size(); i++){
			if( i != 0)
				res += "\t\t\t";
			res += trueResult.get( i);
			if( i != trueResult.size() - 1)
				res += DebuggingDefaults .SYS_LINE_SEPARATOR;
		}
		res += "]";
		return res;
	}
}

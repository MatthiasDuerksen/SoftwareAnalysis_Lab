package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.ArrayList;
import java.util.List;

public class AvailableExpression implements IAnalysisInformation {
	List<String> variablesAndValues;
	List<String> operators;

	public AvailableExpression() {
		super();
		this.variablesAndValues = new ArrayList<>();
		this.operators = new ArrayList<>();
	}

	/**
	 * Implementation missing for: public List<String> getVariablesAndValues(),
	 * public List<String> getOperators(), public void addVariableOrValue(String
	 * variableOrValue), public void addOperator(String operator), public boolean
	 * equals(Object obj)
	 *
	 * Try to generate as much as possible
	 *
	 * Hint: Eclipse Menu -> Source -> Generate ...
	 */
	// TODO: Implement this

	@Override
	public String toString() {
		String returnStr = variablesAndValues.get(0);
		for (int i = 0; i < operators.size(); i++) {
			returnStr += " " + operators.get(i) + " " + variablesAndValues.get(i + 1);
		}
		return returnStr;
	}
}

package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

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
	 * variableOrValue), public void addOperator(String operator), public
	 * boolean equals(Object obj)
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

	public void addOperator(String op) {
		operators.add(op);
	}

	public void addVariableOrValue(String s) {
		variablesAndValues.add(s);
	}

	private boolean listsEqual(List<String> list1, List<String> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		for (int i = 0; i < list1.size(); i++) {
			if (!list1.get(i).equals(list2.get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AvailableExpression) {
			AvailableExpression other = (AvailableExpression) obj;
			return listsEqual(operators, other.operators) && listsEqual(variablesAndValues, other.variablesAndValues);
		}
		return false;
	}

	public List<String> getVariablesAndValues() {
		return variablesAndValues;
	}

	public List<String> getOperators() {
		return operators;
	}
}

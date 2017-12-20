package de.upb.fpauck.sa.lab.whileprograms.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Assignment extends Statement {
	public Assignment(int label, int depth) {
		super(label, depth);
	}

	@Override
	public String getDefVariable() {
		return variables.get(0);
	}

	@Override
	public List<String> getUseVariables() {
		List<String> rhs = new ArrayList<>();
		for (int i = 1; i < variables.size(); i++) {
			rhs.add(variables.get(i));
		}
		return rhs;
	}

	@Override
	public String getStatementString() {
		String returnStr = variablesAndValues.get(0) + " := " + variablesAndValues.get(1);
		for (int i = 0; i < operators.size(); i++) {
			returnStr += " " + operators.get(i) + " " + variablesAndValues.get(i + 2);
		}
		return returnStr;
	}
}
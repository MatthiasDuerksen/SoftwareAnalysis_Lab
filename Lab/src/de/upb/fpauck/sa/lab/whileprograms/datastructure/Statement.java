package de.upb.fpauck.sa.lab.whileprograms.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.upb.fpauck.sa.lab.whileprograms.framework.UniqueArrayList;

public class Statement {
	int label;
	int depth;

	private List<Statement> next;
	private List<Statement> prev;

	List<String> variablesAndValues;
	List<String> variables;
	List<String> values;
	List<String> operators;

	// Helper variable
	static List<Statement> visited = new ArrayList<>();

	public Statement(int label, int depth) {
		this.label = label;
		this.depth = depth;

		variablesAndValues = new ArrayList<>();
		variables = new ArrayList<>();
		values = new ArrayList<>();
		operators = new ArrayList<>();

		next = new ArrayList<>();
		prev = new ArrayList<>();
	}

	public List<String> getVariablesAndValues() {
		return variablesAndValues;
	}

	public String getDefVariable() {
		return null;
	}

	public List<String> getUseVariables() {
		return variables;
	}

	public List<String> getValues() {
		return values;
	}

	public List<String> getOperators() {
		return operators;
	}

	public List<Statement> getAllStatements() {
		return getAllStatements(true);
	}

	public List<Statement> getAllStatements(boolean reset) {
		if (reset) {
			visited.clear();
		}
		visited.add(this);

		List<Statement> all = new UniqueArrayList<>();
		all.add(this);
		for (Statement nextStatement : getNext()) {
			if (!visited.contains(nextStatement)) {
				all.addAll(nextStatement.getAllStatements(false));
			}
		}

		Collections.sort(all, new Comparator<Statement>() {
			@Override
			public int compare(Statement lhs, Statement rhs) {
				if (lhs.getLabel() > rhs.getLabel()) {
					return 1;
				} else if (lhs.getLabel() == rhs.getLabel()) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		return all;
	}

	public int getLabel() {
		return label;
	}

	public int getDepth() {
		return depth;
	}

	public boolean hasNext() {
		return getNext() != null;
	}

	public List<Statement> getNext() {
		return next;
	}

	public List<Statement> getPrev() {
		return prev;
	}

	public void addVariableOrValue(String variableOrValue) {
		variablesAndValues.add(variableOrValue);
		if (variableOrValue.matches("-?[0-9]*")) {
			values.add(variableOrValue);
		} else {
			variables.add(variableOrValue);
		}
	}

	public void addOperator(String operator) {
		operators.add(operator);
	}

	public String getStatementString() {
		return "unknown";
	}

	@Override
	public String toString() {
		List<String> allLines = Arrays.asList(toStringHelp(true).split("\n"));
		Collections.sort(allLines);
		StringBuilder sb = new StringBuilder();
		for (String str : allLines) {
			sb.append(str + "\n");
		}
		return sb.toString();
		
	}

	private String toStringHelp(boolean reset) {
		if (reset) {
			visited.clear();
		}
		visited.add(this);

		String str = label + ":\t" + spacer() + getStatementString();
		str += " (Next = {";
		boolean first = true;
		for (Statement nextStmt : next) {
			str += (first ? "" : ", ") + nextStmt.getLabel() + ": " + nextStmt.getStatementString();
			if (first) {
				first = false;
			}
		}
		str += "}) (Previous = {";
		first = true;
		for (Statement prevStmt : prev) {
			str += (first ? "" : ", ") + prevStmt.getLabel() + ": " + prevStmt.getStatementString();
			if (first) {
				first = false;
			}
		}
		str += "})";
		for (Statement nextStmt : next) {
			if (!visited.contains(nextStmt)) {
				str += "\n" + nextStmt.toStringHelp(false);
			}
		}
		return str;
	}

	public String spacer() {
		String spacer = "";
		for (int i = 0; i < depth; i++) {
			spacer += "\t";
		}
		return spacer;
	}
}

package de.upb.fpauck.sa.lab.whileprograms.datastructure;

public class WhileLoop extends Statement {
	public WhileLoop(int label, int depth) {
		super(label, depth);
	}

	@Override
	public String getStatementString() {
		String returnStr = variablesAndValues.get(0);
		for (int i = 0; i < operators.size(); i++) {
			returnStr += " " + operators.get(i) + " " + variablesAndValues.get(i + 1);
		}
		return returnStr;
	}
}

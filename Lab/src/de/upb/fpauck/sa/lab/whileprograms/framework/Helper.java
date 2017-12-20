package de.upb.fpauck.sa.lab.whileprograms.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.upb.fpauck.sa.lab.whileprograms.analyses.AvailableExpression;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Assignment;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.WhileLoop;

public class Helper {
	public static final String[] ARITHMETIC_OPERATOR = { "+", "-", "*", "/" };
	public static final String[] BOOLEAN_OPERATOR = { "=", ">", ">=", "<", "<=", "!=" };
	public static final String[] CONNECT_OPERATOR = { "AND", "OR", "&", "|", "!" };

	public static Statement getIsolatedExit(Statement program) {
		List<Statement> tempList = new ArrayList<>();
		int maxLabel = 0;
		for (Statement statement : program.getAllStatements()) {
			if (statement instanceof WhileLoop && statement.getNext().size() < 2) {
				tempList.add(statement);
			} else if (statement.getNext().isEmpty()) {
				tempList.add(statement);
			}
			if (statement.getLabel() > maxLabel) {
				maxLabel = statement.getLabel();
			}
		}

		if (tempList.size() == 1) {
			return tempList.get(0);
		} else {
			Statement isolatedExit = new Statement(0, maxLabel + 1);
			for (Statement temp : tempList) {
				temp.getNext().add(isolatedExit);
			}
			return isolatedExit;
		}
	}

	public static List<AvailableExpression> getArithmeticExpressions(Statement stmt) {
		List<AvailableExpression> aExp = new UniqueArrayList<>();
		if (!stmt.getOperators().isEmpty()) {
			int offset = (stmt instanceof Assignment ? 1 : 0);
			for (int length = 1; length <= stmt.getOperators().size(); length++) {
				for (int count = 0; count <= stmt.getOperators().size() - length; count++) {
					AvailableExpression newAExp = new AvailableExpression();
					newAExp.addVariableOrValue(stmt.getVariablesAndValues().get(count + offset));
					for (int i = 0; i < length; i++) {
						newAExp.addOperator(stmt.getOperators().get(i + count));
						newAExp.addVariableOrValue(stmt.getVariablesAndValues().get(i + count + offset + 1));
					}
					if (Collections.disjoint(newAExp.getOperators(), Arrays.asList(BOOLEAN_OPERATOR))
							&& Collections.disjoint(newAExp.getOperators(), Arrays.asList(CONNECT_OPERATOR))) {
						aExp.add(newAExp);
					}
				}
			}

		}
		return aExp;
	}
}

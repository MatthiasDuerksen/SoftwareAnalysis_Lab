package de.upb.fpauck.sa.lab.whileprograms.parser;

import java.util.ArrayList;
import java.util.List;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Assignment;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.IfBranch;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Skip;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.WhileLoop;

public class WhileHandler {
	private Statement program;
	private List<Statement> current;
	private int label;
	private int depth;

	public void programStart() {
		current = null;
		label = 0;
		depth = 0;
		current = new ArrayList<>();
	}

	private void addAndIncrease(Statement nextStatement) {
		label++;
		if (current.isEmpty()) {
			current.add(nextStatement);
			program = current.get(0);
		} else {
			nextStatement.getPrev().addAll(current);
			for (Statement temp : current) {
				temp.getNext().add(nextStatement);
			}
		}
		current.clear();
		current.add(nextStatement);
	}

	public void skipBegin() {
		Statement nextStatement = new Skip(label, depth);
		addAndIncrease(nextStatement);
	}

	public void assignmentBegin() {
		Statement nextStatement = new Assignment(label, depth);
		addAndIncrease(nextStatement);
	}

	public void ifBegin() {
		Statement nextStatement = new IfBranch(label, depth);
		addAndIncrease(nextStatement);

		depth++;
	}

	public void elseBegin() {
		Statement temp;
		do {
			temp = current.get(0).getPrev().get(0);
			current.set(0, temp);
		} while (!(temp instanceof IfBranch));
	}

	public void whileBegin() {
		Statement nextStatement = new WhileLoop(label, depth);
		addAndIncrease(nextStatement);

		depth++;
	}

	public void skipEnd() {
		// do nothing
	}

	public void assignmentEnd() {
		// do nothing
	}

	public void ifEnd() {
		Statement temp;
		do {
			temp = current.get(0).getPrev().get(0);
			current.set(0, temp);
		} while (!(temp instanceof IfBranch) || depth == temp.getDepth());

		current = findEnds(current.get(0));
		if (current.size() == 1) {
			current.add(temp);
		}

		depth--;
	}

	private List<Statement> findEnds(Statement statement) {
		List<Statement> newCurrent = new ArrayList<>();
		if (statement.getNext().isEmpty() || statement instanceof WhileLoop) {
			newCurrent.add(statement);
		} else {
			for (Statement follows : statement.getNext()) {
				newCurrent.addAll(findEnds(follows));
			}
		}
		return newCurrent;
	}

	public void whileEnd() {
		Statement lastOfBody = current.get(0);
		Statement temp;
		do {
			temp = current.get(0).getPrev().get(0);
			current.set(0, temp);
		} while (!(temp instanceof WhileLoop) || lastOfBody.getDepth() == temp.getDepth());
		lastOfBody.getNext().add(temp);
		temp.getPrev().add(lastOfBody);

		depth--;
	}

	public void operatorFound(String operator) {
		current.get(0).addOperator(operator);
	}

	public void valueFound(String value) {
		current.get(0).addVariableOrValue(value);
	}

	public void variableFound(String variable) {
		current.get(0).addVariableOrValue(variable);
	}

	public Statement getProgram() {
		return program;
	}
}

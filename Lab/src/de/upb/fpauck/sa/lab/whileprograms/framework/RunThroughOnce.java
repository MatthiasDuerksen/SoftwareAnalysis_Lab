package de.upb.fpauck.sa.lab.whileprograms.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.analyses.IAnalysisInformation;
import de.upb.fpauck.sa.lab.whileprograms.analyses.IWhileAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;

public class RunThroughOnce {
	private Statement program;
	private IWhileAnalysis analysis;
	private boolean debug;

	private Map<Statement, List<IAnalysisInformation>> analysisInformation;

	// Helper variable
	private static List<Statement> visited = new ArrayList<>();

	public RunThroughOnce(Statement program, IWhileAnalysis analysis, boolean debug) {
		this.program = program;
		this.analysis = analysis;
		this.debug = debug;

		// Initialize analysis
		analysisInformation = analysis.init(program);
	}

	public void run() {
		// RunOnce algorithm
		if (analysis.isForward()) {
			flowThrough(program, analysisInformation.get(program));
			if (debug) {
				System.out.println(analysisInformation.get(program));
			}
		} else {
			Statement isolatedExit = Helper.getIsolatedExit(program);
			flowThrough(isolatedExit, analysisInformation.get(isolatedExit));
			if (debug) {
				System.out.println(analysisInformation.get(isolatedExit));
			}
		}

		// Output result
		for (Statement statement : program.getAllStatements()) {
			System.out.println(statement.getLabel() + ": " + statement.getStatementString() + ", "
					+ analysisInformation.get(statement));
			if (debug) {
				System.out.println(statement.toString().split("\n")[0]
						.substring(statement.toString().split("\n")[0].indexOf("(Next")));
			}
		}
	}

	private void flowThrough(Statement statement, List<IAnalysisInformation> currentInformation) {
		visited.add(statement);

		analysisInformation.put(statement, new UniqueArrayList<>(currentInformation));
		currentInformation = analysis.phi(currentInformation, statement);

		if (analysis.isForward()) {
			for (Statement followingStatement : statement.getNext()) {
				if (!visited.contains(followingStatement)) {
					flowThrough(followingStatement, new UniqueArrayList<>(currentInformation));
				}
			}
		} else {
			for (Statement followingStatement : statement.getPrev()) {
				if (!visited.contains(followingStatement)) {
					flowThrough(followingStatement, new UniqueArrayList<>(currentInformation));
				}
			}
		}
	}
}

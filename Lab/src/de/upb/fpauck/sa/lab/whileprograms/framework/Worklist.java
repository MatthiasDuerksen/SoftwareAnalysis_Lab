package de.upb.fpauck.sa.lab.whileprograms.framework;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import de.upb.fpauck.sa.lab.whileprograms.analyses.IAnalysisInformation;
import de.upb.fpauck.sa.lab.whileprograms.analyses.IWhileAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;

public class Worklist {
	private Statement program;
	private IWhileAnalysis analysis;
	private boolean debug;

	private Map<Statement, List<IAnalysisInformation>> analysisInformation;
	private Stack<Edge> worklist;

	public Worklist(Statement program, IWhileAnalysis analysis, boolean debug) {
		this.program = program;
		this.analysis = analysis;
		this.debug = debug;

		// Initialize worklist
		worklist = new Stack<>();
		// TODO: Initialize the worklist here

		// Initialize analysis
		analysisInformation = analysis.init(program);
	}

	public void run() {
		// Worklist algorithm
		// TODO: Implement worklist algorithm here

		// Output result
		for (Statement statement : program.getAllStatements()) {
			System.out.println(statement.getLabel() + ": " + statement.getStatementString() + ", "
					+ analysisInformation.get(statement));
		}
	}
}

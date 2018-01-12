package de.upb.fpauck.sa.lab.whileprograms.framework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.common.io.Files;

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
		compute(program);

		// Initialize analysis
		analysisInformation = analysis.init(program);

	}

	private void compute(Statement st) {
		for (Statement next : st.getNext()) {
			Edge edge = new Edge(st, next);
			if (!worklist.contains(edge)) {
				worklist.add(edge);
				compute(next);
			}
		}
	}

	public void run() {
		// Worklist algorithm
		// TODO: Implement worklist algorithm here
		try {
			FileWriter writer = new FileWriter("test.csv");
			while (worklist.size() > 0) {
				Edge edge = worklist.pop();
				List<IAnalysisInformation> ai_l = analysisInformation.get(edge.getFrom());
				List<IAnalysisInformation> ai_lPrime = analysisInformation.get(edge.getTo());

				if (debug) {

					for (IAnalysisInformation a : ai_l) {
						writer.append(a + ";");
					}
					writer.append(worklist + "\n");

				}

				if (!analysis.inRelation(analysis.phi(ai_l, edge.getFrom()), ai_lPrime)) {
					analysisInformation.put(edge.getTo(),
							analysis.merge(ai_lPrime, analysis.phi(ai_l, edge.getFrom())));
					for (Statement next : edge.getTo().getNext()) {
						if (!worklist.contains(new Edge(edge.getTo(), next))) {
							worklist.push(new Edge(edge.getTo(), next));
						}
					}
				}
			}

			if (debug) {

				writer.flush();
				writer.close();

			}
		} catch (Exception e) {

		}

		// Output result
		for (Statement statement : program.getAllStatements()) {
			System.out.println(statement.getLabel() + ": " + statement.getStatementString() + ", "
					+ analysisInformation.get(statement));
		}
	}
}

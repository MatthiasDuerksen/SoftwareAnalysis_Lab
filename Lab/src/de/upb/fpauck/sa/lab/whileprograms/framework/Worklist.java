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

		Stack<Edge> helperStack = new Stack<>();

		// Initialize worklist
		worklist = new Stack<>();
		// TODO: Initialize the worklist here
		compute(program, helperStack);

		while (!helperStack.isEmpty()) {
			worklist.push(helperStack.pop());
		}

		// Initialize analysis
		analysisInformation = analysis.init(program);

		System.out.println("-------");
		for (Statement s : analysisInformation.keySet()) {
			System.out.println(s.getStatementString() + " -> " + analysisInformation.get(s));
		}
		System.out.println("-------");

	}

	private void compute(Statement st, Stack<Edge> helperStack) {
		for (Statement next : st.getNext()) {

			System.out.println("next: " + next.getStatementString());
			System.out.println(helperStack);

			Edge edge = new Edge(st, next);
			if (!helperStack.contains(edge)) {
				helperStack.add(edge);
				compute(next, helperStack);
			} else {
				System.out.println("lo");
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

				List<IAnalysisInformation> phi = analysis.phi(ai_l, edge.getFrom());
				if (!analysis.inRelation(phi, ai_lPrime)) {
					analysisInformation.put(edge.getTo(), analysis.merge(ai_lPrime, phi));
					for (Statement next : edge.getTo().getNext()) {
						Edge newEdge = new Edge(edge.getTo(), next);
						if (!worklist.contains(newEdge)) {
							worklist.push(newEdge);
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

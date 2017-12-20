package de.upb.fpauck.sa.lab.soot.analyses;

import java.util.HashSet;
import java.util.List;

import soot.Unit;
import soot.Value;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

public class TaintAnalysis extends ForwardFlowAnalysis<Unit, HashSet<Value>> {
	public TaintAnalysis(DirectedGraph<Unit> graph) {
		super(graph);
		doAnalysis();
	}

	/**
	 * Look for sources and sinks and connections between them.
	 */
	@Override
	protected void flowThrough(HashSet<Value> in, Unit ut, HashSet<Value> dest) {
		// Initialize
		// TODO: Implement this

		// Remove kills
		// TODO: Implement this

		// Add gens
		// TODO: Implement this

		// Report
		// TODO: Implement this
	}

	/**
	 * Initially we do not know if there is a source or a sink
	 */
	@Override
	protected HashSet<Value> newInitialFlow() {
		return new HashSet<Value>();
	}

	/**
	 * Initially we do not know if there is a source or a sink
	 */
	@Override
	protected HashSet<Value> entryInitialFlow() {
		return new HashSet<Value>();
	}

	/**
	 * Merges source1 and source2 into a single set, namely dest.
	 */
	@Override
	protected void merge(HashSet<Value> source1, HashSet<Value> source2, HashSet<Value> dest) {
		// Set union
		dest.clear();
		dest.addAll(source1);
		dest.addAll(source2);
	}

	/**
	 * Copies the content of source into dest.
	 */
	@Override
	protected void copy(HashSet<Value> source, HashSet<Value> dest) {
		dest = new HashSet<>(source);
	}

	/**
	 * This method should reply a list of strings. Each string contained in this
	 * list represents one line of a Taint analysis report. Such a report should
	 * contain where sources and sinks have been found as well as which sources are
	 * connected to which sinks.
	 */
	public List<String> getReport() {
		// FIXME: Fix this
		return null;
	}
}

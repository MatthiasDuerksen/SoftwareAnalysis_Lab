package de.upb.fpauck.sa.lab.soot.analyses;

import soot.Unit;
import soot.Value;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.BackwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;

public class LiveVariablesAnalysis extends BackwardFlowAnalysis<Unit, FlowSet<String>> {
	// Set of all variables
	private FlowSet<String> fullSet = new ArraySparseSet<>();

	/**
	 * Implement the constructor. However, leave the first and last line as it is:
	 * First line: super(graph); Last line: doAnalysis();
	 *
	 * Hint 1: Use an iterator to "flow" through the whole method:
	 *
	 * for (Iterator<Unit> iterator = graph.iterator(); iterator.hasNext();) { ... }
	 */
	public LiveVariablesAnalysis(DirectedGraph<Unit> graph) {
		super(graph);

		// Compute all occurring variables
		// TODO: Implement this

		doAnalysis();
	}

	/**
	 * Computes the live variables by "flowing" through the program from source to
	 * dest (backwards).
	 *
	 * Hint 1: Unit ut represents the current statement.
	 */
	@Override
	protected void flowThrough(FlowSet<String> source, Unit ut, FlowSet<String> dest) {
		source.copy(dest);

		// Remove kills
		// TODO: Implement this

		// Add gens
		// TODO: Implement this
	}

	/**
	 * Once an arbitrary node, that is not contained in final(S), is reached this
	 * defines the initial set of live variables.
	 */
	@Override
	protected FlowSet<String> newInitialFlow() {
		// TODO: Implement this
	}

	/**
	 * Defines the initial set of live variables. In other words, the variables
	 * which are live at nodes contained in final(S) (Var(S) or Var*).
	 *
	 * Hint 1: Compute this set in the constructor.
	 */
	@Override
	protected FlowSet<String> entryInitialFlow() {
		// TODO: Implement this
	}

	/**
	 * Merges source1 and source2 into a single set, namely dest.
	 */
	@Override
	protected void merge(FlowSet<String> source1, FlowSet<String> source2, FlowSet<String> dest) {
		// TODO: Implement this
	}

	/**
	 * Copies the content of source into dest.
	 */
	@Override
	protected void copy(FlowSet<String> source, FlowSet<String> dest) {
		source.copy(dest);
	}

	/**
	 * DO NOT CHANGE THIS FUNCTION!
	 *
	 * This function takes a value interpreted as string and reduces it to its
	 * associate variable name.
	 *
	 * Input: $r0.<de.upb.fpauck.livevariablesexample.LVActivity: int x>
	 *
	 * Output: x
	 */
	private String cleanUp(Value value) {
		String variable = value.toString();
		int cutFrom = variable.lastIndexOf(" ");
		int cutTo = variable.lastIndexOf(">");
		if (cutFrom >= 0 && cutTo >= 0) {
			return variable.substring(cutFrom + 1, cutTo);
		} else {
			return null;
		}
	}
}

package de.upb.fpauck.sa.lab.soot.analyses;

import soot.Unit;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.ForwardFlowAnalysis;

public class ConstantPropagationAnalysis extends ForwardFlowAnalysis<Unit, FlowSet<Constant>> {
	private FlowSet<Constant> fullSet = new ArraySparseSet<>();

	public ConstantPropagationAnalysis(DirectedGraph<Unit> graph) {
		// TODO: Implement this
	}

	@Override
	protected void flowThrough(FlowSet<Constant> source, Unit ut, FlowSet<Constant> dest) {
		// TODO: Implement this
	}

	@Override
	protected FlowSet<Constant> newInitialFlow() {
		// TODO: Implement this
	}

	@Override
	protected FlowSet<Constant> entryInitialFlow() {
		// TODO: Implement this
	}

	@Override
	protected void merge(FlowSet<Constant> source1, FlowSet<Constant> source2, FlowSet<Constant> dest) {
		// TODO: Implement this
	}

	@Override
	protected void copy(FlowSet<Constant> source, FlowSet<Constant> dest) {
		// TODO: Implement this
	}
}

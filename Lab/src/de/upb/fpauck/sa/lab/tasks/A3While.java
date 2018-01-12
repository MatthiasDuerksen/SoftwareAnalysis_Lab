package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.whileprograms.analyses.ReachingDefinitionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkNaive;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkWorklist;

public class A3While {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A3/A3.while" };
		}

		// Naive
		FrameworkNaive<ReachingDefinitionAnalysis> fwN = new FrameworkNaive<>(args, ReachingDefinitionAnalysis.class);
		fwN.run();

		System.out.println("----------------");

		// Worklist
		FrameworkWorklist<ReachingDefinitionAnalysis> fwW = new FrameworkWorklist<>(args,
				ReachingDefinitionAnalysis.class);
		fwW.run();
	}
}

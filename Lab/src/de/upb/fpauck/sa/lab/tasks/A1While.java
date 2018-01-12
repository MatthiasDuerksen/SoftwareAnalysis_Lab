package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.whileprograms.analyses.AvailableExpressionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.ReachingDefinitionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkNaive;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkWorklist;

public class A1While {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A1/A1.while" };
		}

		// Instantiate and run Analysis framework
		FrameworkNaive<LiveVariablesAnalysis> fw = new FrameworkNaive<>(args, LiveVariablesAnalysis.class);
		fw.run();
		System.out.println("new analysis");
		// Worklist
				FrameworkWorklist<LiveVariablesAnalysis> fwW = new FrameworkWorklist<>(args,
						LiveVariablesAnalysis.class);
				fwW.run();
	}
}

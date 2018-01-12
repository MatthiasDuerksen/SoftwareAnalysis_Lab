package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.whileprograms.analyses.AvailableExpressionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkNaive;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkWorklist;

public class A2While {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A2/A2.while" };
		}

		// Instantiate and run Analysis framework
		FrameworkNaive<AvailableExpressionAnalysis> fw = new FrameworkNaive<>(args, AvailableExpressionAnalysis.class);
		fw.run();
		
		System.out.println("new analysis");
		
		FrameworkWorklist<AvailableExpressionAnalysis> fwW = new FrameworkWorklist<>(args,
				AvailableExpressionAnalysis.class);
		fwW.run();
	}
}

package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.whileprograms.analyses.AvailableExpressionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkNaive;

public class A2While {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A2/A2.while" };
		}

		// Instantiate and run Analysis framework
		FrameworkNaive<AvailableExpressionAnalysis> fw = new FrameworkNaive<>(args, AvailableExpressionAnalysis.class);
		fw.run();
	}
}

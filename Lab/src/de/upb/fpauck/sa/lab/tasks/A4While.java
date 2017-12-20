package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.whileprograms.analyses.IntervalAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkWorklist;

public class A4While {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A4/A4.while" };
		}

		// Instantiate and run Analysis framework
		FrameworkWorklist<IntervalAnalysis> fw = new FrameworkWorklist<>(args, IntervalAnalysis.class);
		fw.run();
	}
}

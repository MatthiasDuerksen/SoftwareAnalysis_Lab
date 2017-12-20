package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.soot.analyses.ConstantPropagationAnalysis;
import de.upb.fpauck.sa.lab.soot.framework.FrameworkInterProcedural;

public class A5Java {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A5" };
		}

		// Instantiate and run Soot framework
		FrameworkInterProcedural<ConstantPropagationAnalysis> fw = new FrameworkInterProcedural<>(args,
				ConstantPropagationAnalysis.class);
		fw.run();
	}
}

package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.soot.analyses.ConstantPropagationAnalysis;
import de.upb.fpauck.sa.lab.soot.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.soot.analyses.TaintAnalysis;
import de.upb.fpauck.sa.lab.soot.framework.Framework;
import de.upb.fpauck.sa.lab.soot.framework.FrameworkInterProcedural;

public class TestSoot {
	public static void main(String[] args) {
		// Live Variables
		System.out.println("Live Variables");
		args = new String[] { "tasks/A1" };
		Framework<LiveVariablesAnalysis> fwJ1 = new Framework<>(args, LiveVariablesAnalysis.class);
		fwJ1.run();

		args = new String[] { "tasks/A1/A1.apk" };
		Framework<LiveVariablesAnalysis> fwA1 = new Framework<>(args, LiveVariablesAnalysis.class);
		fwA1.run();

		// Taint
		System.out.println("Taint");
		args = new String[] { "tasks/A2/A2.apk" };
		Framework<TaintAnalysis> fwA2 = new Framework<>(args, TaintAnalysis.class);
		fwA2.run();

		// Constant Propagation
		System.out.println("\nConstant Propagation");
		args = new String[] { "tasks/A5" };
		FrameworkInterProcedural<ConstantPropagationAnalysis> fwJ2 = new FrameworkInterProcedural<>(args,
				ConstantPropagationAnalysis.class);
		fwJ2.run();
	}
}

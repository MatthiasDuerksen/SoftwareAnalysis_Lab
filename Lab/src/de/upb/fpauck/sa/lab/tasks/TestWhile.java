package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.whileprograms.analyses.AvailableExpressionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.IntervalAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.ReachingDefinitionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkNaive;
import de.upb.fpauck.sa.lab.whileprograms.framework.FrameworkWorklist;

public class TestWhile {
	public static void main(String[] args) {
		// Live Variables
		System.out.println("Live Variables");
		args = new String[] { "tasks/A1/A1.while" };
		FrameworkNaive<LiveVariablesAnalysis> fwN1 = new FrameworkNaive<>(args, LiveVariablesAnalysis.class);
		fwN1.run();
		System.out.println();
		FrameworkWorklist<LiveVariablesAnalysis> fwW1 = new FrameworkWorklist<>(args, LiveVariablesAnalysis.class);
		fwW1.run();

		// Available Expression
		System.out.println("\nAvailable Expression");
		args = new String[] { "tasks/A2/A2.while" };
		FrameworkNaive<AvailableExpressionAnalysis> fwN2 = new FrameworkNaive<>(args,
				AvailableExpressionAnalysis.class);
		fwN2.run();
		System.out.println();
		args = new String[] { "tasks/A2/A2.while" };
		FrameworkWorklist<AvailableExpressionAnalysis> fwW2 = new FrameworkWorklist<>(args,
				AvailableExpressionAnalysis.class);
		fwW2.run();

		// Reaching Definition
		System.out.println("\nReaching Definition");
		args = new String[] { "tasks/A3/A3.while" };
		FrameworkNaive<ReachingDefinitionAnalysis> fwN3 = new FrameworkNaive<>(args, ReachingDefinitionAnalysis.class);
		fwN3.run();
		System.out.println();
		FrameworkWorklist<ReachingDefinitionAnalysis> fwW3 = new FrameworkWorklist<>(args,
				ReachingDefinitionAnalysis.class);
		fwW3.run();

		// Interval
		System.out.println("\nInterval");
		args = new String[] { "tasks/A4/A4.while" };
		FrameworkNaive<IntervalAnalysis> fwN4 = new FrameworkNaive<>(args, IntervalAnalysis.class);
		fwN4.run();
		System.out.println();
		FrameworkWorklist<IntervalAnalysis> fwW4 = new FrameworkWorklist<>(args, IntervalAnalysis.class);
		fwW4.run();
	}
}

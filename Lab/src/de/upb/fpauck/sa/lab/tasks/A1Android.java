package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.soot.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.soot.framework.Framework;

public class A1Android {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A1/A1.apk" };
		}

		// Instantiate and run Soot framework
		Framework<LiveVariablesAnalysis> fw = new Framework<>(args, LiveVariablesAnalysis.class);
		fw.run();
	}
}

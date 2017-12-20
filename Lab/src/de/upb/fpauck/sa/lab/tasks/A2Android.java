package de.upb.fpauck.sa.lab.tasks;

import de.upb.fpauck.sa.lab.soot.analyses.TaintAnalysis;
import de.upb.fpauck.sa.lab.soot.framework.Framework;

public class A2Android {
	public static void main(String[] args) {
		// Set input
		if (args.length == 0) {
			args = new String[] { "tasks/A2/A2.apk" };
		}

		// Instantiate and run Soot framework
		Framework<TaintAnalysis> fw = new Framework<>(args, TaintAnalysis.class);
		fw.run();
	}
}

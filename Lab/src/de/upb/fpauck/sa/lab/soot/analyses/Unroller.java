package de.upb.fpauck.sa.lab.soot.analyses;

import soot.Body;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.toolkits.callgraph.CallGraph;

public class Unroller {
	public static Body unroll(CallGraph cg, Body intoThisMethod) {
		JimpleBody unrolledBody = Jimple.v().newBody();
		unrolledBody.setMethod(intoThisMethod.getMethod());

		// Calculate what has to be added and removed
		// TODO: Implement this

		// Add and remove units
		// TODO: Implement this

		// Output instrumented version
		// TODO: Implement this

		return unrolledBody;
	}
}

package de.upb.fpauck.sa.lab.soot.framework;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import de.upb.fpauck.sa.lab.soot.analyses.ConstantPropagationAnalysis;
import de.upb.fpauck.sa.lab.soot.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.soot.analyses.TaintAnalysis;
import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.Transform;
import soot.Unit;
import soot.options.Options;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

public class Framework<S> {
	private final String[] args;

	private boolean debug;

	private S analysis;
	private final Stack<Body> methodsAsBodies = new Stack<>();

	private PrintStream systemOut;

	public Framework(String[] args, Class<S> analysisClass) {
		this.args = args;
		debug = false;
		if (args.length > 1) {
			debug = Boolean.valueOf(args[1]).booleanValue();
		}
		if (!debug) {
			disableOutput();
		}

		// Setup Soot
		soot.G.reset();
		if (args[0].endsWith(".apk")) {
			Options.v().set_src_prec(Options.src_prec_apk);
			final List<String> excludedPacks = Arrays.asList(new String[] { "com.google.*", "java.*", "sun.misc.*",
					"android.*", "org.apache.*", "soot.*", "javax.servlet.*" });
			Options.v().set_exclude(excludedPacks);
			Options.v().set_no_bodies_for_excluded(true);
			Options.v().set_force_android_jar("lib/android.jar");
			Options.v().set_process_dir(Collections.singletonList(args[0]));
			// Options.v().set_allow_phantom_refs(true);
		} else {
			Options.v().set_src_prec(Options.src_prec_class);
			Options.v().set_soot_classpath(Helper.getProperties().getProperty(Helper.SOOT_CLASS_PATH));
			Options.v().set_process_dir(Collections.singletonList(args[0]));
		}

		// Run Live Variables Analysis for example method
		PackManager.v().getPack("jtp").add(new Transform("jtp.myTransform", new BodyTransformer() {
			@Override
			protected void internalTransform(Body body, String phase, Map<String, String> options) {
				if (body.getMethod().getName().equals("example")) {
					if (!methodsAsBodies.contains(body)) {
						methodsAsBodies.push(body);
					}

					try {
						Class<S> reflectionClass = analysisClass;
						Constructor<S> reflectionConstructor = reflectionClass.getConstructor(DirectedGraph.class);
						analysis = reflectionConstructor.newInstance(new ExceptionalUnitGraph(methodsAsBodies.peek()));
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println(
								"Could not create an analysis of the provided type: " + analysisClass.getName());
					}
				}
			}
		}));
	}

	public void run() {
		// Execute Analysis
		soot.Main.main(new String[] { new File(args[0]).getName() });

		viewResult();
	}

	private void viewResult() {
		if (!debug) {
			enableOutput();
		}

		// View Result
		for (Body b : methodsAsBodies) {
			if (b.getMethod().getName().equals("example")) {
				if (debug) {
					System.out.println("Method:\n" + b.toString());
				}
				for (Unit u : b.getUnits()) {
					if (analysis instanceof ConstantPropagationAnalysis) {
						System.out.println(((ConstantPropagationAnalysis) analysis).getFlowBefore(u) + " -> "
								+ u.toString() + " -> " + ((ConstantPropagationAnalysis) analysis).getFlowAfter(u));
					} else if (analysis instanceof LiveVariablesAnalysis) {
						System.out.println(((LiveVariablesAnalysis) analysis).getFlowBefore(u) + " -> " + u.toString()
								+ " -> " + ((LiveVariablesAnalysis) analysis).getFlowAfter(u));
					} else {
						System.out.println(((TaintAnalysis) analysis).getFlowBefore(u) + " -> " + u.toString() + " -> "
								+ ((TaintAnalysis) analysis).getFlowAfter(u));
					}
				}
				System.out.println();
			}
		}

		if (analysis instanceof TaintAnalysis) {
			System.out.println("Report:");
			List<String> report = ((TaintAnalysis) analysis).getReport();
			for (String str : report) {
				System.out.println(str);
			}
		}
	}

	private void disableOutput() {
		this.systemOut = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(final int arg) throws IOException {
				// do nothing
			}
		}));
	}

	private void enableOutput() {
		System.setOut(this.systemOut);
	}
}
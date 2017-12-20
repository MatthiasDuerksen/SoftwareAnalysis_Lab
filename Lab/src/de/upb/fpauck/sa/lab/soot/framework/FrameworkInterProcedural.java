package de.upb.fpauck.sa.lab.soot.framework;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.soot.analyses.ConstantPropagationAnalysis;
import de.upb.fpauck.sa.lab.soot.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.soot.analyses.TaintAnalysis;
import de.upb.fpauck.sa.lab.soot.analyses.Unroller;
import soot.Body;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.Transform;
import soot.Unit;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

public class FrameworkInterProcedural<S> {
	private final String[] args;

	private boolean debug;

	private S analysis;

	private Body main;

	private PrintStream systemOut;

	public FrameworkInterProcedural(String[] args, Class<S> analysisClass) {
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
		Options.v().set_src_prec(Options.src_prec_class);
		Options.v().set_soot_classpath(Helper.getProperties().getProperty(Helper.SOOT_CLASS_PATH));
		Options.v().set_process_dir(Collections.singletonList(args[0]));
		Options.v().set_no_bodies_for_excluded(true);
		Options.v().set_whole_program(true);
		Options.v().setPhaseOption("cg.spark", "on");

		PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTransform", new SceneTransformer() {
			@Override
			protected void internalTransform(String phaseName, Map<String, String> options) {
				CallGraph cg = Scene.v().getCallGraph();
				main = Scene.v().getMainMethod().retrieveActiveBody();

				// Unroll & Perform analysis
				main = Unroller.unroll(cg, main);
				try {
					Class<S> reflectionClass = analysisClass;
					Constructor<S> reflectionConstructor = reflectionClass.getConstructor(DirectedGraph.class);
					analysis = reflectionConstructor.newInstance(new ExceptionalUnitGraph(main));
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Could not create an analysis of the provided type: " + analysisClass.getName());
				}
			}
		}));
	}

	public void run() {
		// Execute Soot
		soot.Main.main(new String[] { new File(args[0]).getName() });

		if (!debug) {
			enableOutput();
		} else {
			System.out.println("");
		}

		// View analysis result
		for (Unit u : main.getUnits()) {
			if (analysis instanceof ConstantPropagationAnalysis) {
				System.out.println(((ConstantPropagationAnalysis) analysis).getFlowBefore(u) + " -> " + u.toString()
						+ " -> " + ((ConstantPropagationAnalysis) analysis).getFlowAfter(u));
			} else if (analysis instanceof LiveVariablesAnalysis) {
				System.out.println(((LiveVariablesAnalysis) analysis).getFlowBefore(u) + " -> " + u.toString() + " -> "
						+ ((LiveVariablesAnalysis) analysis).getFlowAfter(u));
			} else {
				System.out.println(((TaintAnalysis) analysis).getFlowBefore(u) + " -> " + u.toString() + " -> "
						+ ((TaintAnalysis) analysis).getFlowAfter(u));
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
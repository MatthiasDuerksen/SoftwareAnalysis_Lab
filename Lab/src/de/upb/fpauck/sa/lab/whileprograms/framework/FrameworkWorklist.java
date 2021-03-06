package de.upb.fpauck.sa.lab.whileprograms.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import de.upb.fpauck.sa.lab.whileprograms.analyses.AvailableExpressionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.IntervalAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.LiveVariablesAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.analyses.ReachingDefinitionAnalysis;
import de.upb.fpauck.sa.lab.whileprograms.parser.ParseException;
import de.upb.fpauck.sa.lab.whileprograms.parser.WhileHandler;
import de.upb.fpauck.sa.lab.whileprograms.parser.WhileParser;

public class FrameworkWorklist<S> {
	private S analysis;
	private Worklist worklistAnalysis;

	public FrameworkWorklist(String[] args, Class<S> analysisClass) {
		boolean debug = false;
		if (args.length > 1) {
			debug = Boolean.valueOf(args[1]).booleanValue();
		}

		WhileHandler handler = new WhileHandler();

		// Parse program
		File input = new File(args[0]);
		try {
			FileReader ir = new FileReader(input);
			WhileParser parser = new WhileParser(ir);
			parser.setHandler(handler);
			parser.programm();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file: " + input.getAbsolutePath());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Get analysis
		try {
			analysis = analysisClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not create an analysis of the provided type: " + analysisClass.getName());
		}
		if (analysis instanceof ReachingDefinitionAnalysis) {
			worklistAnalysis = new Worklist(handler.getProgram(), (ReachingDefinitionAnalysis) analysis, debug);
		} else if (analysis instanceof AvailableExpressionAnalysis) {
			worklistAnalysis = new Worklist(handler.getProgram(), (AvailableExpressionAnalysis) analysis, debug);
		} else if (analysis instanceof LiveVariablesAnalysis) {
			worklistAnalysis = new Worklist(handler.getProgram(), (LiveVariablesAnalysis) analysis, debug);
		} else if (analysis instanceof IntervalAnalysis) {
			worklistAnalysis = new Worklist(handler.getProgram(), (IntervalAnalysis) analysis, debug);
		}
	}

	public void run() {
		// Execute Analysis and output result
		worklistAnalysis.run();
	}
}
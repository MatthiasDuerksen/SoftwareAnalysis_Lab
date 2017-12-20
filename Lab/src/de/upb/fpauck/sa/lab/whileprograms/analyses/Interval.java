package de.upb.fpauck.sa.lab.whileprograms.analyses;

public class Interval implements IAnalysisInformation {
	public static final String PLUS_INFINITY = "+inf";
	public static final String MINUS_INFINITY = "-inf";

	private String variable;
	private String from;
	private String to;

	public Interval(String variable, String from, String to) {
		super();
		this.variable = variable;
		this.from = from;
		this.to = to;
	}

	// TODO: Implement this

	@Override
	public String toString() {
		return "(" + variable + " -> [" + from + ", " + to + "])";
	}
}

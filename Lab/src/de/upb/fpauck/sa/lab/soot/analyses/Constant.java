package de.upb.fpauck.sa.lab.soot.analyses;

public class Constant {
	public static final String TOP = "T";
	public static final String BOTTOM = "B";

	private String variable;
	private String analysisInformation;

	// TODO: Implement this

	@Override
	public String toString() {
		return "(" + variable + ", " + analysisInformation + ")";
	}
}

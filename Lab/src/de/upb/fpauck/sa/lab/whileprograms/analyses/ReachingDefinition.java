package de.upb.fpauck.sa.lab.whileprograms.analyses;

public class ReachingDefinition implements IAnalysisInformation {
	public static final String UNKNOWN = "?";

	private String variable;
	private String number;

	public ReachingDefinition(String variable, String number) {
		this.variable = variable;
		this.number = number;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReachingDefinition) {
			ReachingDefinition def = (ReachingDefinition) obj;
			return def.variable.equals(variable) && def.number.equals(number);
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + variable + ", " + number + ")";
	}
}

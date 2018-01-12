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

	private static int parse(String s) {
		int result;
		if (s.equals(PLUS_INFINITY)) {
			result = Integer.MAX_VALUE;
		} else if (s.equals(MINUS_INFINITY)) {
			result = Integer.MIN_VALUE;
		}

		else {
			result = Integer.parseInt(s);
		}
		return result;
	}

	public static Interval widen(Interval interval1, Interval interval2) {
		String z1;
		if (parse(interval1.from) <= parse(interval2.from)) {
			z1 = interval1.from;
		} else {
			z1 = MINUS_INFINITY;
		}

		String z2;
		if (parse(interval2.to) <= parse(interval1.to)) {
			z2 = interval1.to;
		} else {
			z2 = PLUS_INFINITY;
		}

		return new Interval(interval1.variable, z1, z2);
	}
	
	
	
	public Interval plus(Interval interval){
		int i = parse(from)+parse(interval.from);
		throw new IllegalArgumentException();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Interval) {
			Interval other = (Interval) obj;
			return variable.equals(other.variable) && from.equals(other.from) && to.equals(other.to);
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + variable + " -> [" + from + ", " + to + "])";
	}
}

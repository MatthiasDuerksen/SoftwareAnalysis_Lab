package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.ArrayList;
import java.util.List;

public class Interval implements IAnalysisInformation {
	public static final String PLUS_INFINITY = "+inf";
	public static final String MINUS_INFINITY = "-inf";
	public static final String UNKNOWN = "?";

	private String variable;
	 String from;
	 String to;

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

	private String plus(String n1, String n2) {
		if (n1.equals(PLUS_INFINITY) || n2.equals(PLUS_INFINITY)) {
			return PLUS_INFINITY;
		}
		if (n1.equals(MINUS_INFINITY) || n2.equals(MINUS_INFINITY)) {
			return MINUS_INFINITY;
		} else {
			int result = Integer.parseInt(n1) + Integer.parseInt(n2);
			return String.valueOf(result);
		}
	}

	private String minus(String n1, String n2) {
		if (n1.equals(MINUS_INFINITY) || n2.equals(PLUS_INFINITY)) {
			return MINUS_INFINITY;
		}
		if (n1.equals(PLUS_INFINITY) || n2.equals(MINUS_INFINITY)) {
			return PLUS_INFINITY;
		} else {
			int result = Integer.parseInt(n1) - Integer.parseInt(n2);
			return String.valueOf(result);
		}
	}

	private String times(String n1, String n2) {
		if (n1.equals(MINUS_INFINITY) || n2.equals(PLUS_INFINITY)) {
			return MINUS_INFINITY;
		}
		if (n1.equals(PLUS_INFINITY) || n2.equals(MINUS_INFINITY)) {
			return MINUS_INFINITY;
		} else if (n1.equals(PLUS_INFINITY) || n2.equals(PLUS_INFINITY)) {
			return PLUS_INFINITY;
		}

		else if (n1.equals(MINUS_INFINITY) || n2.equals(MINUS_INFINITY)) {
			return PLUS_INFINITY;
		} else {
			int result = Integer.parseInt(n1) * Integer.parseInt(n2);
			return String.valueOf(result);
		}
	}

	public Interval plus(Interval interval) {
		return new Interval(UNKNOWN, plus(from, interval.from), plus(to, interval.to));

	}

	public Interval minus(Interval interval) {
		return new Interval(UNKNOWN, minus(from, interval.to), minus(to, interval.from));

	}

	 boolean smaller(String n1, String n2) {
		if (n1.equals(PLUS_INFINITY)) {
			return false;
		} else if (n2.equals(PLUS_INFINITY)) {
			return true;
		} else if (n1.equals(MINUS_INFINITY)) {
			return true;
		} else if (n2.equals(MINUS_INFINITY)) {
			return false;
		}

		else {
			return Integer.parseInt(n1) <= Integer.parseInt(n2);
		}
	}

	 boolean greater(String n1, String n2) {
		if (n1.equals(PLUS_INFINITY)) {
			return true;
		} else if (n2.equals(PLUS_INFINITY)) {
			return false;
		} else if (n1.equals(MINUS_INFINITY)) {
			return false;
		} else if (n2.equals(MINUS_INFINITY)) {
			return true;
		}

		else {
			return Integer.parseInt(n1) >= Integer.parseInt(n2);
		}
	}

	public Interval times(Interval interval) {

		String[] array = new String[4];
		array[0] = times(from, interval.to);
		array[1] = times(from, interval.from);
		array[2] = times(to, interval.to);
		array[3] = times(to, interval.from);

		String min = PLUS_INFINITY;
		String max = MINUS_INFINITY;
		for (int i = 0; i < array.length; i++) {
			if (greater(array[i], max)) {
				max = array[i];
			}
			if (smaller(array[i], min)) {
				min = array[i];
			}
		}

		return new Interval(UNKNOWN, min, max);

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

	public Object getVariable() {
		return variable;
	}

	public void setVariable(String defVar) {
		this.variable=defVar;
		
	}
}

package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Assignment;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.IfBranch;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Skip;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.WhileLoop;
import de.upb.fpauck.sa.lab.whileprograms.framework.UniqueArrayList;

public class IntervalAnalysis implements IWhileAnalysis {

	List<Interval> allIntervals = new UniqueArrayList<Interval>();
	List<Statement> statementsVisited = new UniqueArrayList<Statement>();

	@Override
	public Map<Statement, List<IAnalysisInformation>> init(Statement program) {
		// TODO: Implement this
		Map<Statement, List<IAnalysisInformation>> map = new HashMap<>();
		compute(program);
		statementsVisited.clear();
		assign(program, program, map);
		return map;
	}

	private void assign(Statement st, Statement start, Map<Statement, List<IAnalysisInformation>> map) {

		if (statementsVisited.contains(st)) {
			return;
		} else {
			statementsVisited.add(st);
		}

		map.put(st, new UniqueArrayList<>());

		if (st.equals(start)) {
			for (Interval interval : allIntervals) {
				map.get(st).add(interval);
			}
		}
		for (Statement next : st.getNext()) {
			assign(next, start, map);
		}
	}

	private void compute(Statement st) {
		if (statementsVisited.contains(st)) {
			return;
		} else {
			statementsVisited.add(st);
		}

		if (!(st instanceof Assignment)) {
			return;
		}

		allIntervals.add(new Interval(st.getDefVariable(), Interval.MINUS_INFINITY, Interval.PLUS_INFINITY));

		for (Statement next : st.getNext()) {
			compute(next);
		}
	}

	@Override
	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement) {
		List<IAnalysisInformation> copy = new UniqueArrayList<IAnalysisInformation>();
		copy.addAll(analysisInformation);
		List<Interval> kill = new UniqueArrayList<Interval>();
		List<Interval> gen = new UniqueArrayList<Interval>();

		if (statement instanceof IfBranch || statement instanceof WhileLoop || statement instanceof Skip) {
			return copy;
		} else if (statement instanceof Assignment) {
			Assignment ass = (Assignment) statement;
			String defVar = ass.getDefVariable();

			// kill
			for (IAnalysisInformation ai : copy) {
				Interval in = (Interval) ai;
				if (in.getVariable().equals(defVar)) {
					kill.add(in);
				}
			}

			// gen

			Interval interval = null;
			if (ass.getOperators().isEmpty()) {
				String value = ass.getVariablesAndValues().get(1);

				interval = bla(value, copy);

			} else {
				String operator = ass.getOperators().get(0);
				String operand1 = ass.getVariablesAndValues().get(1);
				String operand2 = ass.getVariablesAndValues().get(2);
				Interval in1 = bla(operand1, copy);
				Interval in2 = bla(operand2, copy);
				if (operator.equals("+")) {
					interval = in1.plus(in2);
				} else if (operator.equals("-")) {
					interval = in1.minus(in2);
				} else if (operator.equals("*")) {
					interval = in1.times(in2);
				}
			}

			interval.setVariable(defVar);
			gen.add(interval);
		}

		copy.removeAll(kill);
		copy.addAll(gen);
		return copy;
	}

	private Interval bla(String param, List<IAnalysisInformation> analysisInformation) {
		try {
			Integer.parseInt(param);
			return new Interval(Interval.UNKNOWN, param, param);
		} catch (NumberFormatException e) {
			for (IAnalysisInformation ai : analysisInformation) {
				Interval in = (Interval) ai;
				if (param.equals(in.getVariable())) {
					return in;
				}
			}
		}
		throw new RuntimeException("analyse läuft nicht richtig");
	}

	@Override
	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2) {
		List<IAnalysisInformation> result = new UniqueArrayList<IAnalysisInformation>();

		for (IAnalysisInformation ai : analysisInformation1) {
			Interval i1 = (Interval) ai;

			for (IAnalysisInformation ai2 : analysisInformation2) {
				Interval i2 = (Interval) ai2;

				if (i1.getVariable().equals(i2.getVariable())) {
					result.add(Interval.widen(i1, i2));
				}
			}

		}

		for (IAnalysisInformation ai : analysisInformation2) {
			Interval i1 = (Interval) ai;

			boolean contains = false;
			for (IAnalysisInformation ai2 : analysisInformation1) {
				Interval i2 = (Interval) ai2;

				if (i1.getVariable().equals(i2.getVariable())) {
					contains = true;
				}
			}
			if (!contains) {
				result.add(i1);
			}

		}

		return result;
	}

	@Override
	public boolean inRelation(List<IAnalysisInformation> first, List<IAnalysisInformation> second) {

		for (IAnalysisInformation info : first) {
			boolean found=false;
			for (IAnalysisInformation info2 : second) {
				Interval i1 = (Interval) info;
				Interval i2 = (Interval) info2;

				if (i1.getVariable().equals(i2.getVariable())) {
					found=true;
					if (!i1.from.equals(i2.from)&&i1.smaller(i1.from, i2.from)) {
						return false;
					}
					if (!i1.to.equals(i2.to)&&i1.greater(i1.to, i2.to)) {
						return false;
					}
				}
			}
			
			if(!found){
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isForward() {
		return true;
	}
}

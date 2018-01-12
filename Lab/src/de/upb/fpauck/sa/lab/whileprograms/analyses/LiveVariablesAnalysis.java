package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Assignment;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.IfBranch;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.WhileLoop;
import de.upb.fpauck.sa.lab.whileprograms.framework.Helper;
import de.upb.fpauck.sa.lab.whileprograms.framework.UniqueArrayList;

public class LiveVariablesAnalysis implements IWhileAnalysis {
	/**
	 * As the name suggests, this method will be called once during the
	 * initialization of a LiveVariables analysis. It has to assign all
	 * variables that are live initially. The return type is a map, since we
	 * have to assign the initially live variables for each statement of the
	 * program.
	 *
	 * Hint: Use "Helper.getIsolatedExit(program)" to get the isolated exit of a
	 * program.
	 */
	@Override
	public Map<Statement, List<IAnalysisInformation>> init(Statement program) {
		Map<Statement, List<IAnalysisInformation>> analysisInformation = new HashMap<>();

		// Compute all occurring variables
		List<String> allVariables = new UniqueArrayList<>();
		// TODO: Implement this
		Statement s = Helper.getIsolatedExit(program);
		findAllVariables(allVariables, s);

		List<LiveVariable> allVariablesReal = new UniqueArrayList<LiveVariable>();
		for (String string : allVariables) {
			allVariablesReal.add(new LiveVariable(string));
		}

		// Initialize analysis information
		analysisInformation = new HashMap<>();
		List<IAnalysisInformation> l = new UniqueArrayList<IAnalysisInformation>();
		for (LiveVariable v : allVariablesReal) {
			l.add(v);
		}
		assignLiveVariables(program, analysisInformation, l);

		return analysisInformation;
	}

	void assignLiveVariables(Statement st, Map<Statement, List<IAnalysisInformation>> map, List<IAnalysisInformation> allV) {
		if(st.getNext().isEmpty()){
		map.put(st, allV);
		}else{
			map.put(st, new UniqueArrayList<IAnalysisInformation>());
		}
		for (Statement succ : st.getNext()) {
	
			assignLiveVariables(succ, map, allV);
		}
	}

	void findAllVariables(List<String> var, Statement st) {
		if (st.getDefVariable() != null)
			var.add(st.getDefVariable());
		for (Statement s : st.getPrev()) {
			findAllVariables(var, s);
		}
	}

	/**
	 * This method represents the transfer function for the block represented by
	 * variable "statement". We have to remove and add LiveVariables to the list
	 * "analysisInformation" according to the kill-/gen-function defined during
	 * the lecture.
	 *
	 * Hint: Use "instanceof" to check if a statement is an Assignment, an
	 * IfBranch or a WhileLoop.
	 */
	@Override
	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement) {
		List<IAnalysisInformation> adaptedAi = new UniqueArrayList<>(analysisInformation);

		// TODO: Implement this
		// kill
		List<LiveVariable> kill = new UniqueArrayList<>();
		List<LiveVariable> gen = new UniqueArrayList<>();
		if (statement instanceof Assignment) {
			Assignment ass = (Assignment) statement;
			kill.add(new LiveVariable(ass.getDefVariable()));
			// ass.getUseVariables().forEach(s -> gen.add(new LiveVariable(s)));
			for (String s : ass.getUseVariables()) {
				gen.add(new LiveVariable(s));
			}

		} else if (statement instanceof IfBranch) {
			IfBranch ifb = (IfBranch) statement;
			// ifb.getUseVariables().forEach(s -> gen.add(new LiveVariable(s)));
			for (String s : ifb.getUseVariables()) {
				gen.add(new LiveVariable(s));
			}
		} else if (statement instanceof WhileLoop) {
			WhileLoop whileloop = (WhileLoop) statement;

			// whileloop.getUseVariables().forEach(s -> gen.add(new
			// LiveVariable(s)));
			for (String s : whileloop.getUseVariables()) {
				gen.add(new LiveVariable(s));
			}
		}

		adaptedAi.removeAll(kill);
		adaptedAi.addAll(gen);

		return adaptedAi;
	}

	/**
	 * This method is used to merge "analysisInformation1" with
	 * "analysisInformation2". Most times this is the union or the intersection.
	 */
	@Override
	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2) {
		List<IAnalysisInformation> listAll = new UniqueArrayList<IAnalysisInformation>();
		listAll.addAll(analysisInformation1);
		listAll.addAll(analysisInformation2);
		return listAll;
	}

	/**
	 * Implement the intervals subset relation here. Return true if "first" is a
	 * subset of "second"
	 */
	@Override
	public boolean inRelation(List<IAnalysisInformation> first, List<IAnalysisInformation> second) {
		// TODO: Implement this

		for (IAnalysisInformation info : first) {
			if (!second.contains(info)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Defines whether this analysis is a forward or backward one.
	 */
	@Override
	public boolean isForward() {
		// TODO: Implement this
		return false;
	}
}

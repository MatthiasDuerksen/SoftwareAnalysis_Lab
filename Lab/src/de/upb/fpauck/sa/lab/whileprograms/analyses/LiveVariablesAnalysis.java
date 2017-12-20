package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;
import de.upb.fpauck.sa.lab.whileprograms.framework.UniqueArrayList;

public class LiveVariablesAnalysis implements IWhileAnalysis {
	/**
	 * As the name suggests, this method will be called once during the
	 * initialization of a LiveVariables analysis. It has to assign all variables
	 * that are live initially. The return type is a map, since we have to assign
	 * the initially live variables for each statement of the program.
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

		// Initialize analysis information
		analysisInformation = new HashMap<>();
		// TODO: Implement this

		return analysisInformation;
	}

	/**
	 * This method represents the transfer function for the block represented by
	 * variable "statement". We have to remove and add LiveVariables to the list
	 * "analysisInformation" according to the kill-/gen-function defined during the
	 * lecture.
	 *
	 * Hint: Use "instanceof" to check if a statement is an Assignment, an IfBranch
	 * or a WhileLoop.
	 */
	@Override
	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement) {
		List<IAnalysisInformation> adaptedAi = new UniqueArrayList<>(analysisInformation);

		// TODO: Implement this

		return adaptedAi;
	}

	/**
	 * This method is used to merge "analysisInformation1" with
	 * "analysisInformation2". Most times this is the union or the intersection.
	 */
	@Override
	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2) {
		// TODO: Implement this
	}

	/**
	 * Implement the intervals subset relation here. Return true if "first" is a
	 * subset of "second"
	 */
	@Override
	public boolean inRelation(List<IAnalysisInformation> first, List<IAnalysisInformation> second) {
		// TODO: Implement this
	}

	/**
	 * Defines whether this analysis is a forward or backward one.
	 */
	@Override
	public boolean isForward() {
		// TODO: Implement this
	}
}

package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;

public class AvailableExpressionAnalysis implements IWhileAnalysis {
	private List<AvailableExpression> allAExps;

	@Override
	public Map<Statement, List<IAnalysisInformation>> init(Statement program) {
		Map<Statement, List<IAnalysisInformation>> analysisInformation = new HashMap<>();

		// Compute all occurring arithmetic expressions
		// TODO: Implement this

		// Initialize analysis information
		// TODO: Implement this

		return analysisInformation;
	}

	@Override
	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement) {
		// TODO: Implement this
	}

	@Override
	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2) {
		// TODO: Implement this
	}

	@Override
	public boolean inRelation(List<IAnalysisInformation> first, List<IAnalysisInformation> second) {
		// TODO: Implement this
	}

	@Override
	public boolean isForward() {
		// TODO: Implement this
	}
}

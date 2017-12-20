package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;

public interface IWhileAnalysis {
	public Map<Statement, List<IAnalysisInformation>> init(Statement program);

	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement);

	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2);

	public boolean inRelation(List<IAnalysisInformation> first, List<IAnalysisInformation> second);

	public boolean isForward();
}
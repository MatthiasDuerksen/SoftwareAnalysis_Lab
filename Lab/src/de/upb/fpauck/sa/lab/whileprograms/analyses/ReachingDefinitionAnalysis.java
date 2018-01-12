package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Assignment;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;
import de.upb.fpauck.sa.lab.whileprograms.framework.UniqueArrayList;

public class ReachingDefinitionAnalysis implements IWhileAnalysis {

	List<ReachingDefinition> allReaDef = new UniqueArrayList<ReachingDefinition>();
	int numberOfStatements = 0;

	@Override
	public Map<Statement, List<IAnalysisInformation>> init(Statement program) {
		Map<Statement, List<IAnalysisInformation>> analysisInformation = new HashMap<Statement, List<IAnalysisInformation>>();

		computeAll(program, analysisInformation);
		for (ReachingDefinition def : allReaDef) {
			analysisInformation.get(program).add(def);
		}
//		for (Statement s : analysisInformation.keySet()) {
//			System.out.println(s.getStatementString() + " -> " + analysisInformation.get(s));
//		}
		return analysisInformation;
	}

	private void computeAll(Statement st, Map<Statement, List<IAnalysisInformation>> analysisInformation) {
		if (analysisInformation.containsKey(st)) {
			return;
		}
		numberOfStatements++;
		analysisInformation.put(st, new UniqueArrayList<>());
		for (String var : st.getUseVariables()) {
			ReachingDefinition def = new ReachingDefinition(var, ReachingDefinition.UNKNOWN);
			if (!allReaDef.contains(def)) {
				allReaDef.add(def);
			}
		}

		for (Statement next : st.getNext()) {
			computeAll(next, analysisInformation);
		}

	}

	@Override
	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement) {
		List<IAnalysisInformation> copy = new UniqueArrayList<IAnalysisInformation>();
		copy.addAll(analysisInformation);
		if (statement instanceof Assignment) {
			Assignment ass = (Assignment) statement;
			String defVar = ass.getDefVariable();
			List<ReachingDefinition> kill = new UniqueArrayList<ReachingDefinition>();
			List<ReachingDefinition> gen = new UniqueArrayList<ReachingDefinition>();

			// kill
			kill.add(new ReachingDefinition(defVar, ReachingDefinition.UNKNOWN));
			for (int i = 0; i < numberOfStatements; i++) {
				kill.add(new ReachingDefinition(defVar, String.valueOf(i)));
			}

			// gen
			gen.add(new ReachingDefinition(defVar, String.valueOf(statement.getLabel())));

			copy.removeAll(kill);
			copy.addAll(gen);

		}
		return copy;

	}

	@Override
	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2) {
		
		List<IAnalysisInformation> listAll = new UniqueArrayList<IAnalysisInformation>();
		listAll.addAll(analysisInformation1);
		listAll.addAll(analysisInformation2);
		return listAll;
	}

	@Override
	public boolean inRelation(List<IAnalysisInformation> first, List<IAnalysisInformation> second) {
		for (IAnalysisInformation info : first) {
			if (!second.contains(info)) {
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

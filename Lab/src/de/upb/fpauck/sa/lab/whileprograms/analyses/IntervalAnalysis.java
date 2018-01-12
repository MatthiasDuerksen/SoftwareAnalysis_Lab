package de.upb.fpauck.sa.lab.whileprograms.analyses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.IfBranch;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Skip;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;
import de.upb.fpauck.sa.lab.whileprograms.datastructure.WhileLoop;
import de.upb.fpauck.sa.lab.whileprograms.framework.UniqueArrayList;

public class IntervalAnalysis implements IWhileAnalysis {
	@Override
	public Map<Statement, List<IAnalysisInformation>> init(Statement program) {
		// TODO: Implement this
		Map<Statement, List<IAnalysisInformation>> map = new HashMap<>();
		compute(program, map);
		for(Statement s : map.keySet()){
			System.out.println(s.getStatementString() + " -> " + map.get(s));
		}
		return map;
	}

	private void compute(Statement st, Map<Statement, List<IAnalysisInformation>> map) {
		map.put(st, new UniqueArrayList<>());
		outer: for (Statement next : st.getNext()) {
			for (Statement old : map.keySet()) {
				if (old.equals(next)) {
					continue outer;
				}
			}
			compute(next, map);
		}
	}

	@Override
	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement) {
		// TODO: Implement this
		if (statement instanceof IfBranch || statement instanceof WhileLoop || statement instanceof Skip) {
			//return analysisInformation;
		}
		System.out.println("phi param " + analysisInformation);
		return null;
	}

	@Override
	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2) {
		// TODO: Implement this
		if(analysisInformation1.size()>1){
			System.out.println("merge list 1 : " + analysisInformation1);
		}
		if(analysisInformation2.size()>1){
			System.out.println("merge list 2 : " + analysisInformation2);
		}
		return null;
	}

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

	@Override
	public boolean isForward() {
		return true;
	}
}

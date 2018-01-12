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

public class AvailableExpressionAnalysis implements IWhileAnalysis {
	private List<AvailableExpression> allAExps = new UniqueArrayList<AvailableExpression>();
	List<Statement> statementsVisited = new ArrayList<Statement>();
	private Map<Statement, AvailableExpression> mapStatementExpression = new HashMap<Statement, AvailableExpression>();

	@Override
	public Map<Statement, List<IAnalysisInformation>> init(Statement program) {
		Map<Statement, List<IAnalysisInformation>> analysisInformation = new HashMap<>();

		// Compute all occurring arithmetic expressions
		// TODO: Implement this
		
		compute(program);
		statementsVisited.clear();

		// Initialize analysis information
		// TODO: Implement this

		analysisInformation.put(program, new UniqueArrayList());
		
		assign(program, analysisInformation,program);
		statementsVisited.clear();

		return analysisInformation;
	}

	private void compute(Statement statement) {
		if (statementsVisited.contains(statement)) {
			return;
		} else {
			statementsVisited.add(statement);
		}
		AvailableExpression ex = new AvailableExpression();
		int i = 0;
		if (statement instanceof WhileLoop) {
			i = 1;
		}
		for (; i < statement.getOperators().size(); i++) {
			ex.addOperator(statement.getOperators().get(i));
		}
		i = 1;
		for (; i < statement.getVariablesAndValues().size(); i++) {
			ex.addVariableOrValue(statement.getVariablesAndValues().get(i));
		}

		allAExps.add(ex);
		mapStatementExpression.put(statement, ex);

		for (Statement st : statement.getNext()) {
			compute(st);
		}

	}

	private void assign(Statement s, Map<Statement, List<IAnalysisInformation>> analysisInformation, Statement program) {
		if (statementsVisited.contains(s)) {
			return;
		} else {
			statementsVisited.add(s);
		}

		if(s.equals(program)){
		analysisInformation.put(s, new UniqueArrayList<>());
		}else{
			analysisInformation.put(s, null);
//			for(AvailableExpression exp : allAExps){
//				analysisInformation.get(s).add(exp);
//			}
		}
		for (Statement st : s.getNext()) {
			assign(st, analysisInformation,program);
		}
	}

	@Override
	public List<IAnalysisInformation> phi(List<IAnalysisInformation> analysisInformation, Statement statement) {
		// kill
		List<AvailableExpression> kill = new UniqueArrayList<AvailableExpression>();
		if (statement instanceof Assignment) {
			Assignment ass = (Assignment) statement;
			String defV = ass.getDefVariable();
			for (AvailableExpression expr : allAExps) {
				for (String s : expr.getVariablesAndValues()) {
					if (s.equals(defV)) {
						kill.add(expr);
					}
				}
			}
		}
		

		// gen
		List<AvailableExpression> gen = new UniqueArrayList<AvailableExpression>();
		if (statement instanceof Assignment) {
			Assignment ass = (Assignment) statement;
			String defV = ass.getDefVariable();
			for (AvailableExpression expr : allAExps) {
				if (expr.equals(mapStatementExpression.get(statement))) {
					boolean valid = true;
					for (String s : expr.getVariablesAndValues()) {
						if (s.equals(defV)) {
							valid = false;
							break;
						}
					}
					if (valid) {
						gen.add(expr);
					}

				}
			}
		} else if (statement instanceof WhileLoop || statement instanceof IfBranch) {
			gen.add(mapStatementExpression.get(statement));
		}
		


		List<IAnalysisInformation> adaptedAi = new UniqueArrayList<IAnalysisInformation>(analysisInformation);
		adaptedAi.removeAll(kill);
		adaptedAi.addAll(gen);

		return adaptedAi;
	}

	@Override
	public List<IAnalysisInformation> merge(List<IAnalysisInformation> analysisInformation1,
			List<IAnalysisInformation> analysisInformation2) {
		List<IAnalysisInformation> merged = new UniqueArrayList<IAnalysisInformation>();
		
		if(analysisInformation1==null){
			return analysisInformation2;
		}
		
		for(IAnalysisInformation a : analysisInformation1){
			if(analysisInformation2.contains(a)){
				merged.add(a);
			}
		}
		
		
		return merged;
	}

	@Override
	public boolean inRelation(List<IAnalysisInformation> first, List<IAnalysisInformation> second) {
		
		if(second==null){
			return false;
		}
		
		for (IAnalysisInformation info : first) {
			if (!second.contains(info)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isForward() {
		// TODO: Implement this
		return true;
	}
}

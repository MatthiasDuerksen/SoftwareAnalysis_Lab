package de.upb.fpauck.sa.lab.whileprograms.analyses;

public class LiveVariable implements IAnalysisInformation {
	String variable;

	public LiveVariable(String varialbe) {
		super();
		this.variable = varialbe;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LiveVariable other = (LiveVariable) obj;
		if (variable == null) {
			if (other.variable != null) {
				return false;
			}
		} else if (!variable.equals(other.variable)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return variable;
	}
}
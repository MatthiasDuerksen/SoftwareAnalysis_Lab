package de.upb.fpauck.sa.lab.whileprograms.datastructure;

public class Skip extends Statement {
	public Skip(int label, int depth) {
		super(label, depth);
	}

	@Override
	public String getStatementString() {
		return "skip";
	}
}
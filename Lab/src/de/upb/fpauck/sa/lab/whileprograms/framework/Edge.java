package de.upb.fpauck.sa.lab.whileprograms.framework;

import de.upb.fpauck.sa.lab.whileprograms.datastructure.Statement;

public class Edge {
	private Statement from, to;

	public Edge(Statement from, Statement to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Statement getFrom() {
		return from;
	}

	public void setFrom(Statement from) {
		this.from = from;
	}

	public Statement getTo() {
		return to;
	}

	public void setTo(Statement to) {
		this.to = to;
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
		Edge other = (Edge) obj;
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "(" + from.getLabel() + ", " + to.getLabel() + ")";
	}
}

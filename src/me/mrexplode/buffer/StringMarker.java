package me.mrexplode.buffer;

public class StringMarker {
	
	private boolean operator = false;
	private char prevTemp = 0;
	
	public void check(char c) {
		//avoiding a possible bug case
		if (prevTemp != '\\') {
			if (c == '"') {
				if (operator) {
					operator = false;
				}
				operator = true;
			}
		}
		prevTemp = c;
	}

	public boolean inString() {
		return operator;
	}
	
}

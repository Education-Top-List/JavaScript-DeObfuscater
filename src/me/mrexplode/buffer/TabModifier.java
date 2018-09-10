package me.mrexplode.buffer;

public class TabModifier {
	
	private int modifier = 0;
	
	public void check(char c) {
		if (c == '{') {
			modifier++;
		} else if (c == '}' && modifier >= 1) {
			modifier--;
		}
	}
	
	public String getTab() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < modifier; i++) {
			builder.append("\t");
		}
		
		return builder.toString();
	}

}

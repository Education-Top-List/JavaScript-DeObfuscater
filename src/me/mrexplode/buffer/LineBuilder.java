package me.mrexplode.buffer;

import java.io.BufferedWriter;
import java.io.IOException;

public class LineBuilder {
	
	private	CharBuf buffer = new CharBuf(5);
	private TabModifier tabModifier = new TabModifier();
	private BufferedWriter output = null;
	private StringBuilder stringBuilder = new StringBuilder();
	
	//ignoring the operators inside the for loop
	private int forIgnore = 0;
	
	public LineBuilder(BufferedWriter writer) {
		output = writer;
	}

	public void apply(char c) {
		buffer.add(c);
		//if (!buffer.inStringOperator) {
			tabModifier.check(c);
			
			switch (c) {
			//any character
			default:
				stringBuilder.append(c);
				break;
			//open scope
			case '{':
				stringBuilder.append(c);
				stringBuilder.append("\n");
				try {
					output.write(stringBuilder.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				recreateAndApplyTab(stringBuilder);
				break;
			//close scope
			case '}':
				stringBuilder.append("\n");
				stringBuilder.append(tabModifier.getTab());
				stringBuilder.append(c + "\n");
				try {
					output.write(stringBuilder.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				recreateAndApplyTab(stringBuilder);
				break;
			//line ending
			case ';':
				if (buffer.checkFor() && forIgnore == 0) forIgnore = 3;
				if (forIgnore > 0) {
					forIgnore--;
				} else {
					stringBuilder.append(c);
					stringBuilder.append("\n");
					try {
						output.write(stringBuilder.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
					recreateAndApplyTab(stringBuilder);
				}
				break;
			}
		/*} else {
			try {
				System.out.println("ignored: " + c);
				output.write(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	private void recreateAndApplyTab(StringBuilder builder) {
		builder = new StringBuilder();
		builder.append(tabModifier.getTab());
	}
	
}

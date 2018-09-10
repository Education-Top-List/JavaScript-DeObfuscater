package me.mrexplode.buffer;

/**
 * Fix sized buffer.
 * 
 * Only add method implemented.
 * 
 * @author MrExplode
 *
 */
public class CharBuf {
	private char[] characters;
	private StringMarker marker = new StringMarker();
	
	public boolean inStringOperator = false;
	public int lifetimeCount = 0;
	
	public CharBuf(int size) {
		characters = new char[size + 1];//+1: passive previous tracking for keywords. example: if the buffer content is "for  " the whole sentence could look "Pfor  " so passive checking for the prev character.
	}
	
	public void add(char c) {
		lifetimeCount++;
		marker.check(c);
		inStringOperator = marker.inString();
		
		char temp = c;
		for (int i = 0; i < characters.length; i++) {
			characters[i] = temp;
			if (i++ < characters.length) {
				temp = characters[i++];
			}
		}
	}
	
	public String getContent() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < characters.length -1; i++) {
			b.append(characters[i]);
		}
		return b.toString();
	}
	
	/**
	 * 
	 * @return true if there are a for keyword in the buffer.
	 */
	public boolean checkFor() {
		return new String(characters).contains(" for ");
	}
}

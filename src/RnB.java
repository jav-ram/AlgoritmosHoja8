import java.util.TreeMap;

public class RnB implements WordSet {
	
	private TreeMap<String,Word> base;

	public RnB(){
		base = null;
		base = new TreeMap<String,Word>();
	}
	
	@Override
	public void add(Word wordObject) {
		// TODO Auto-generated method stub
		base.put(wordObject.getWord(),wordObject);
	}

	@Override
	public Word get(Word word) {
		// TODO Auto-generated method stub
		return base.get(word);
	}

}

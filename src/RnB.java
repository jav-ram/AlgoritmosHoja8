import java.util.TreeMap;

public class RnB implements WordSet {
	
	private RedBlackTree base;

	public RnB(){
		base = null;
		base = new RedBlackTree();;
		
	}

	@Override
	public void add(Word wordObject) {
		// TODO Auto-generated method stub
		//wordObject.setWord(Long.toHexString(Double.doubleToLongBits(Math.random())));
		
		base.insert(wordObject);
	}

	@Override
	public Word get(Word word) {
		// TODO Auto-generated method stub
		
		return (Word) base.find(word);
	}

}


public class SplaySet implements WordSet{
	private SplayBST<Word> base;
	
	public SplaySet(){
		base = new SplayBST<Word>();
	}
	

	@Override
	public void add(Word wordObject) {
		// TODO Auto-generated method stub
		base.insert(wordObject);
	}

	@Override
	public Word get(Word word) {
		// TODO Auto-generated method stub
		return base.find(word);
	}
	

}

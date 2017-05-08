import java.util.TreeMap;


/**
 * @author Maria Mercedes Retolaza 
 *
 */
public class TreeMapSet implements WordSet {
	
	private TreeMap base = new TreeMap(); 
	
	@Override
	public void add(Word wordObject) {
		if(get(wordObject)==null){
			base.put(wordObject.getWord().hashCode(), wordObject);
		}
	}
		
	@Override
	public Word get(Word word) {
		// TODO Auto-generated method stub
		return (Word) base.get(word.getWord().hashCode());
	}

}

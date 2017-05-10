import java.util.Hashtable;

/**
 * Created by pablo on 5/10/2017.
 */
public class HashTableSet implements WordSet {

    private Hashtable base = new Hashtable();

    @Override
    public void add(Word wordObject) {
        base.put(wordObject.getWord().hashCode(), wordObject);
    }

    @Override
    public Word get(Word word) {
        return (Word) base.get(word.getWord().hashCode());
    }
}

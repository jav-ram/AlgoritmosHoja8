/**
 * @author María Mercedes Retolaza 
 *
 * @param <K> Generico que representa el tipo de key del mapeo.
 * @param <V> Generico que representa el tipo de value del mapeo.
 */
public interface Mapeo<K, V> {
	
	/**
	 * @param key	Generico que representa el tipo de key del elemento ingresado.
	 * @param value	Generico que representa el tipo de valor del elemento ingresado.
	 */
	void put(K key, V value);

	/**
	 * @param key	Generico que representa el tipo de key del elemento ingresado.
	 * @return		True o False, dependiendo si el elemento aparece en el mapeo.
	 */
	boolean contains(K key);

	/**
	 * @param key	Generico que representa el tipo de key del elemento ingresado.
	 * @return		El valor asociado al key ingresado.
	 */
	V get(K key);
	
}
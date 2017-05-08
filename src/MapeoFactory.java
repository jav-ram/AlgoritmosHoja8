
/**
 * @author Maria Mercedes Retolaza
 *
 */
public interface MapeoFactory {
	/**
	 * @param tipoMapeo	El tipo de mapeo requerido.
	 * @return	La instaciacion del mapeo.
	 */
	@SuppressWarnings("rawtypes")
	Mapeo getMapeo(String tipoMapeo);
}
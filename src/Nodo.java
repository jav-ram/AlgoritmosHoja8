 /**
 * 
 * @author Luis Diego Fernandez
 * @version 22.03.17
 * Clase del objeto Nodo
 *
 */
public class Nodo <T extends Comparable> implements Comparable {
	
	private T Valor;
	private Nodo Left;
	private Nodo Right;
	
	/**
	 * Inicializador
	 * @param valor
	 */
	public Nodo(T valor){
		Valor = valor;
	}
	
	/**
	 * Devuleve valor del nodo.
	 * @return Valor del nodo.
	 */
	public T getValor() {
		return Valor;
	}
	
	/**
	 * SetValor
	 * @param valor
	 */
	public void setValor(T valor) {
		Valor = valor;
	}
	
	/**
	 * Devuleve nodo izquierdo.
	 * @return Nodo Izquiedo.
	 */
	public Nodo getIzquiedo() {
		return Left;
	}
	
	/**
	 * Set Nodo izq
	 * @param izquiedo
	 */
	public void setIzquiedo(Nodo izquiedo) {
		Left = izquiedo;
	}
	
	/**
	 * Devuleve nodo derecho.
	 * @return Nodo derecho.
	 */
	public Nodo getDerecho() {
		return Right;
	}
	
	/**
	 * Set nodo derecho.
	 * @param derecho
	 */
	public void setDerecho(Nodo derecho) {
		Right = derecho;
	}
	
	/**
	 * toString
	 */
	public String toString(){
		return Valor.toString();
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return Valor.compareTo(arg0);
	}
	
	public void printNodos(){
		System.out.println("Nodo:");
		System.out.println(Valor);
		System.out.println("Nodo Izquierdo:");
		System.out.println(Left);
		System.out.println("Nodo Derecho:");
		System.out.println(Right);
		System.out.println("");
		
		try {
			Left.printNodos();
			
		} catch (Exception e){
			System.out.println("Nodo Izquierdo nulo");
		}
		
		try {
			Right.printNodos();
			
		} catch (Exception e){
			System.out.println("Nodo Derecho nulo");
		}
	}
	

}


class WordSetFactory {
	
	// Metodo que genera un objeto que implementa WordSet
	// parametro tipo: 1 = SimpleSet
	//                         2 = implementado con Red black tree
	//                         3 = implementado con Splay Tree
	//                         4 = implementado con Hash Table
	//                         5 = implementado con TreeMap (de Java Collection Framework)
	
	public static WordSet generateSet(int tipo)
	{
	    switch (tipo){
	    
	    case 1:{
	    	return new SimpleSet();
	    }
	    case 2:{
	    	return new RnB(); //RnB
	    }
	    case 3:{
	    	return new SplaySet(); //Splay
	    }
	    case 4:{
	    	return new SplaySet(); //HashTable
	    }
	    case 5:{
	    	return new SplaySet(); //TreeMap
	    }
	    }
			
			return null; // modificarlo para que regrese la implementacion seleccionada
	}
	
	
}
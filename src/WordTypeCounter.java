
import java.io.*;
import java.util.Scanner;

class WordTypeCounter {
	
	public static void main(String[] args) throws Exception
	{
		
		boolean loop = true;
		Scanner teclado = new Scanner(System.in);
		
		if(args.length > 1)
		{
			
			while (loop) {
				
				
				File wordFile = new File(args[0]);
				
				// el segundo parametro indica el nombre del archivo que tiene el texto a analizar
				File textFile = new File(args[1]);
				
				// el tercer parametro sirve para seleccionar la implementacion que se usara para
				// guardar el conjunto de palabras. Use el valor 1 para indicar que se empleara
				// Para el resto de implementaciones: 
				//  2 Red Black Tree
				//  3 Splay Tree
				//  4 Hash Table
				//  5 TreeMap (de java collection framework)
							
				int implementacion = 0;
				
				while (implementacion < 1 || implementacion > 5){
					
					System.out.println("Por favor ingrese uno de las siguientes implementaciones para el analisis de palabras.");
					System.out.println("1) Simple");
					System.out.println("2) Red Black Tree");
					System.out.println("3) Splay Tree");
					System.out.println("4) Hash Table");
					System.out.println("5) TreeMap");

					try {
						
						implementacion = teclado.nextInt();
						
					} catch (Exception e) {
						
						System.out.print("Por favor ingresar unicamente una de las opciones presentadas");
						
					}
					
					
				}		
				
				BufferedReader wordreader;
				BufferedReader textreader;
				
				int verbs=0;
				int nouns=0;
				int adjectives=0;
				int adverbs=0;
				int gerunds=0;
				
				long starttime;
				long endtime;
				
				
				if(wordFile.isFile() && textFile.isFile()) {
					// Leer archivos
					try
					{
						wordreader = new BufferedReader(new FileReader(wordFile));
						textreader = new BufferedReader(new FileReader(textFile));
					}
					catch (Exception ex)
					{
						System.out.println("Error al leer!");
						return;
					}
					
					// Crear un WordSet para almacenar la lista de palabras
					// se selecciona la implementacion de acuerdo al tercer parametro pasado en la linea
					// de comando
					// =====================================================
					WordSet words =  WordSetFactory.generateSet(implementacion);
					// =====================================================
					
					String line = null;
					String[] wordParts;
					
					// leer el archivo que contiene las palabras y cargarlo al WordSet.
					starttime = System.currentTimeMillis();
					line = wordreader.readLine();
					while(line!=null)
					{
						wordParts = line.split("\\.");  // lo que esta entre comillas es una 
						if(wordParts.length == 2)
						{
							words.add(new Word(wordParts[0].trim(),wordParts[1].trim()));
						}
						line = wordreader.readLine();
					}
					wordreader.close();
					endtime = System.currentTimeMillis();
					
					System.out.println("Palabras cargadas en " + (endtime-starttime) + " ms.");
					
					// Procesar archivo de texto
					starttime = System.currentTimeMillis();
					line = textreader.readLine();
					String[] textParts;
					Word currentword;
					Word lookupword = new Word();
					
					while(line!=null)
					{
						// Separar todas las palabras en la
						textParts = line.split("[^\\w-]+"); // utilizar de separador cualquier caracter que no sea una letra,
						
						// Revisar cada palabra y verificar de que tipo es. 
						for(int i=0;i<textParts.length;i++)
						{
							lookupword.setWord(textParts[i].trim().toLowerCase());
							currentword = words.get(lookupword);
							if(currentword != null)
							{
								if(currentword.getType().equals("v-d") || currentword.getType().equals("v") || currentword.getType().equals("q"))
									verbs++;
								else if(currentword.getType().equals("g") )
									gerunds++;
								else if(currentword.getType().equals("a-s") || currentword.getType().equals("a-c") || currentword.getType().equals("a"))
									adjectives++;
								else if(currentword.getType().equals("e"))
									adverbs++;
								else 
									nouns++;
							}
						}
						
						line = textreader.readLine();
					}
					textreader.close();
					endtime = System.currentTimeMillis();
					System.out.println("Texto analizado en " + (endtime-starttime) + " ms.");
					
					// Presentar 
					System.out.println("El texto tiene:");
					System.out.println(verbs + " verbos");
					System.out.println(nouns + " sustantivos");
					System.out.println(adjectives + " adjetivos");
					System.out.println(adverbs + " adverbios");
					System.out.println(gerunds + " gerundios");
					
					
					System.out.println("");
					System.out.println("Desea probar otra implementacion? (Y/N)");
					String respuesta = teclado.nextLine();
					respuesta = teclado.nextLine();
					respuesta = respuesta.toUpperCase();
					System.out.println("");
					
					while (!respuesta.equals("Y") && !respuesta.equals("N")) {
									
						System.out.println("Por favor ingrese unicamente Y o N");
						System.out.println("Desea probar otra implementacion? (Y/N)");
						respuesta = teclado.nextLine();
						respuesta = respuesta.toUpperCase();
						System.out.println("");
					}
					
					if (respuesta.equals("Y")) {
						
						loop = true;
						
					}
					
					else {
						
						loop = false;
						
					}
					
										
				}
				else
				{
					System.out.println("No encuentro los archivos :'( ");
				}
			}
		}
		
		else {
				System.out.println("Faltan Parametros.");
		}

			
	}
}
package Buscaminas;

import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import javax.swing.JTextField;

import Core.Board;
import Core.Window;


/**
 * Aquest Programa es una simulació del famós joc Cercamines. 
 */
public class MainBuscaminas {

	// https://colorhunt.co/palette/5c7285818c78a7b49ee2e0c8 PALETA DE COLOR

	static Scanner scan = new Scanner(System.in);
	static Random rand = new Random();
	
	// He hagut de posar el jugador en estàtic perque necesito que em guardi els valors que l'hi vaig ficant. Perque al menú de configuració ja l'igualo a minas
	static Jugador player = new Jugador();
	//per el mateix motiu, haig de fer el diccionari de guanyadors estàtic
	static TreeMap<String, Integer> ganadores = new TreeMap<>();
	
	//Aixó es igual, perque no funciona.
//	public static File musicaWin = new File("Buscaminas/Resources/Music/untitled.wav");
//	public static Clip clip;
	
	public static void main(String[] args) {

		Board panel = new Board();
		Window ventana = new Window(panel);
		String[][] tablero = new String[10][10];
		int minas = 15;

		ventana.setSize(700, 500);
		ventana.setResizable(false);

		menu(tablero, minas, panel, ventana);

	}
	
	/**
	 * Aquesta funció crea un menu per al joc del cercamines 
	 * 
	 * @param tablero (String[][])
	 * @param minas (int)
	 * @param panel (Board)
	 * @param ventana (Window)
	 */

	public static void menu(String[][] tablero, int minas, Board panel,
			Window ventana) {
		
		boolean ganar = false;
		player.nombre = "jugador 1";
		int opcion = -1;
		while (opcion != 5) {

			String[] mainMenu = { "", " BUSCAMINAS ", " Menu: ", "  1. Mostrar Ajuda ", " 2. Opcions",
					" 3. Jugar Partida. ", " 4.Llista de Guanyadors ", " 5. Sortir ", };
			panel.setText(mainMenu);
			panel.setColorbackground(0x5C7285);
			int[] palette = { 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8 };
			panel.setColortext(palette);
			int[][] matriuMainMenu = { { 0, 1, 0, 0 }, { 0, 2, 0, 0 }, { 0, 3, 0, 0 }, { 0, 4, 0, 0 }, { 0, 5, 0, 0 },
					{ 0, 6, 0, 0 }, { 0, 7, 0, 0 } };
			panel.setActborder(false);
			Posicion p = new Posicion();
			panel.draw(matriuMainMenu, 't');

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			p.filas = panel.getCurrentMouseRow();
			p.columnas = panel.getCurrentMouseCol();

			if (p.filas != -1 && p.columnas != -1) {

				if (p.columnas == 1) {
					switch (p.filas) {
					case 2:
						ajuda(panel);

						break;
					case 3:
						minas = configuracion(tablero,  minas, panel);
						break;
					case 4:
						ganar = jugar(panel, ventana, tablero, minas );
						acabar(panel,ganar);
						actualizarInfo();
						break;
					case 5:
						clasificacion(panel);
						opcion = 4;
						break;
					case 6:
						ventana.close();
						System.out.println("Que vaya bien! Adiós!");
						break;
					}
				}
			}

		}

	}
	
	/**
	 * Aquesta funció mostra un missatge per pantalla 
	 * @param panel (Board)
	 */

	public static void clasificacion(Board panel) {

		Posicion p = new Posicion();

		String[] mensaje = { "", " Ho sento ", " La Classificació es mostrará ", " per consola ", "  Menu " };

		System.out.println(ganadores);
		int[] palette = { 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8 };
		panel.setText(mensaje);
		panel.setColortext(palette);
		int[][] matMensaje = { { 0, 0, 0, 0 }, { 4, 1, 0, 0 }, { 0, 2, 0, 0 }, { 0, 3, 0, 0 } };

		panel.draw(matMensaje, 't');

		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			p.filas = panel.getCurrentMouseRow();
			p.columnas = panel.getCurrentMouseCol();

			if (p.filas == 1 && p.columnas == 0) {
				return;
			}
		}
	}

	/**
	 * Aquesta funció serveix per afegir entrades a un diccionari.
	 * @return (TreeMap)
	 */
	public static TreeMap<String, Integer> actualizarInfo() {
		ganadores.put(player.nombre, player.puntos);
		return ganadores;
	}
	
	/**
	 * Aquesta funcio mira si s'ha guanyat (true) o s'ha perdut (false) i fa display de diferents
	 * gifs segons es guanyi o perdi
	 * @param panel (Board)
	 * @param ganar (boolean)
	 */
	public static void acabar(Board panel,boolean ganar ) {
		Posicion p = new Posicion();
		String[] gifsWinning = { "", "Resources/victory.gif", "Resources/knuckles.gif", "Resources/stonks.gif",
				"Resources/snoopdog.gif", "Resources/victory-winning.gif" };
		String[] gifsLosing = { "", "Resources/bad.gif", "Resources/explosion.gif", "Resources/ohno.gif",
				"Resources/noStonks.gif", "Resources/cry.gif" };
		
		//No aconsegueixo que pari i dona un stack overflow quan torna al menu.
		//music.playMusic();
		if(ganar) {
			panel.setSprites(gifsWinning);
			

		} else {
			panel.setSprites(gifsLosing);
		}
		

		int[][] matGifs = { { 0, 1, 0 }, { 0, 0, 5 }, { 0, 0, 0 }, { 3, 0, 0 }, { 0, 0, 0 } };

		int[][][] matriulayer = { matGifs, { { 4, 0, 0 }, { 0, 0, 0 } }, { { 0, 0, 0 }, { 0, 0, 2 } },
				{ { 0, 0, 0 }, { 0, 0, 0 }, { 0, 1, 0 } } };
		String[] text = { "", " Menu "};

		char[] tipus = { 's', 's', 's', 't' };
		panel.setText(text);
		panel.draw(matriulayer, tipus);

		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			p.filas = panel.getCurrentMouseRow();
			p.columnas = panel.getCurrentMouseCol();

			if (p.filas == 2 && p.columnas == 1) {
				return;
			} 
		}
	}

	/**
	 * Aquesta funcio printa per pantalla la matriu creada de 10x10 utilitzant el numero de mines segons la dificultat 
	 * seleccionada (7 de default)
	 * 
	 * @param panel (Board)
	 * @param ventana (Window) per poder ficar les vores de les cel·les
	 * @param tablero (int[][])
	 * @param minas (int)
	 * @return (boolean)
	 */
	public static boolean jugar(Board panel, Window ventana, String[][] tablero, int minas) {
		boolean partidaEnCurs = true;
		Posicion p = new Posicion();
		String[] sprites = { "Resources/0.png", "Resources/1.png", "Resources/2.png", "Resources/3.png",
				"Resources/4.png", "Resources/5.png", "Resources/6.png", "Resources/7.png", "Resources/8.png",
				"Resources/vacia.png", "Resources/bomba.png" };
		panel.setSprites(sprites);

		int[][] playerField = { { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
				{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
				{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
				{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 } };

		panel.setColorbackground(0xA7B49E);
		ventana.setActLabels(true);
		initTablero(tablero);
		plantarMinas(tablero, minas);
		printTablero(tablero);
		panel.draw(playerField);

		while (partidaEnCurs) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			p.filas = panel.getCurrentMouseRow();
			p.columnas = panel.getCurrentMouseCol();

			if (p.filas != -1 && p.columnas != -1) {

				if (!tablero[p.filas][p.columnas].equals("0")) {
					playerField = descobrir(p.filas, p.columnas, tablero, minas, playerField);
					partidaEnCurs = partidaAcabada(playerField, minas);
				} else {
					playerField = printarBombas(tablero, playerField);
					contarPuntos(player, minas, playerField);
					actualizarInfo();
					System.out.println(player.puntos);
					return false;
				}
			}

			panel.draw(playerField);
		}

		contarPuntos(player, minas, playerField);
		actualizarInfo();
		player.victorias++;
		System.out.println(player.nombre+" "+ player.puntos +" "+ player.victorias);
		return true;

	}

	/**
	 * Aquesta funció conta els punts del jugador segons la seva dificultat i els afegeix als punts del jugador.
	 * 
	 * @param player (Jugador)
	 * @param minas (int)
	 * @param playerField (int[][])
	 */
	public static void contarPuntos(Jugador player, int minas, int[][] playerField) {
		if (minas == 7) {
			player.puntos = contarCasillas(playerField);
		} else if (minas == 15) {
			player.puntos = contarCasillas(playerField) * 3;
		} else if (minas == 30) {
			player.puntos = contarCasillas(playerField) * 7;
		}
	}
	
	/**
	 * Aquesta funció suma totes les cel·les de la matriu que estan per sota de 9;
	 * @param playerField (int[][])
	 * @return int
	 */

	public static int contarCasillas(int[][] playerField) {
		int cont = 0;

		for (int i = 0; i < playerField.length; i++) {
			for (int j = 0; j < playerField[0].length; j++) {
				if(playerField[i][j] < 9 ) {
					cont += playerField[i][j];
				}
			}
		}

		return cont;
	}


	/**
	 * Aquesta funció recorre la matriu "tablero" que es on es posen les bombes i aplica el numero corresponent 
	 * (en aquest cas 10) que correspon al sprite de les bombes al tauler que veura el jugador. 
	 *  
	 * @param tablero (int[][])
	 * @param playerField (int[][])
	 * @return int[][]
	 */

	public static int[][] printarBombas(String[][] tablero, int[][] playerField) {
		for (int i = 0; i < playerField.length; i++) {
			for (int j = 0; j < playerField[0].length; j++) {
				if (tablero[i][j].equals("0")) {
					playerField[i][j] = 10;
				}
			}
		}
		return playerField;

	}

	/**
	 * Aquesta funció comproba si la partida s'ha acabat comparant el numero de cel·les remanents
	 * amb el numero de mines en joc
	 * 
	 * @param tablero (int[][])
	 * @param minas (int)
	 * @return boolean
	 */
	
	private static boolean partidaAcabada(int[][] tablero, int minas) {

		int cont = 0;

		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				if (tablero[i][j] == 9) {
					cont++;
				}
			}

		}

		if (cont == minas) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * Aquesta funció recursiva descobreix les cel·les adjacents a un altre si la primera de 0 mines al voltant.
	 * 
	 * @param filas (int)
	 * @param columnas (int)
	 * @param tablero (int[][])
	 * @param minas (int)
	 * @param playerField (int[][])
	 * @return int[][]
	 */

	public static int[][] descobrir(int filas, int columnas, String[][] tablero, int minas, int[][] playerField) {

		if (!estoyFuerisima(tablero, filas, columnas) && !tablero[filas][columnas].equals("0")
				&& playerField[filas][columnas] == 9) {
			int num = destapar(tablero, filas, columnas);
			playerField[filas][columnas] = num;
			if (num == 0) {

				// amunt
				playerField = descobrir(filas - 1, columnas, tablero, minas, playerField);
				// Esquerra
				playerField = descobrir(filas, columnas - 1, tablero, minas, playerField);
				// Abaix
				playerField = descobrir(filas + 1, columnas, tablero, minas, playerField);
				// Dreta
				playerField = descobrir(filas, columnas + 1, tablero, minas, playerField);

				playerField[filas][columnas] = num;
			} else {
				return playerField;
			}
		}

		return playerField;
	}

	/**
	 * Aquesta funció conta cuantes bombes hi ha al voltant del a cel·la seleccionada per assignar-hi un numero
	 * mes tard.
	 * 
	 * @param tablero (int[][])
	 * @param filas (int)
	 * @param columnas (int)
	 * @return int
	 */
	public static int destapar(String[][] tablero, int filas, int columnas) {
		int bomba = 0;

		for (int i = filas - 1; i <= filas + 1; i++) {
			for (int j = columnas - 1; j <= columnas + 1; j++) {
				if (!estoyFuerisima(tablero, i, j)) {
					if (tablero[i][j].equals("0")) {
						bomba++;
					}
				}
			}
		}

		return bomba;
	}

	/**
	 * Aquesta funció prepara la configuració per pantalla del joc. Pots assignar un nom al jugador (si ja existeix simplement 
	 * afegirà la puntuació al jugador amb el mateix nom) a més de poder seleccionar la dificultat. Simplement clica 
	 * en cualsevol dificultat i prem guardar.
	 * 
	 * @param tablero (int[][])
	 * @param minas (int)
	 * @param panel (Board)
	 * @return int;
	 */

	public static int configuracion(String[][] tablero, int minas,	Board panel) {

		Posicion p = new Posicion();
		int minasPH = 0;

		JTextField textField = new JTextField();
		textField.setBounds(240, 250, 280, 25);

		String[] texto = { " ", " Buscaminas! ", " Configuració de jugador: ", " Nom: ", " Dificultat: ", " fàcil ",
				" normal ", " difìcil ", " Enrere ", " Guardar " };
		panel.setText(texto);
		int[] color = { 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xA7B49E,
				0xA7B49E };
		panel.setColortext(color);
		panel.setColorbackground(0x5C7285);
		int[][] matConfi = { { 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 2, 0, 0, 0 },
				{ 0, 3, 0, 0, 0 }, { 0, 4, 0, 0, 0 }, { 0, 5, 6, 7, 0 }, { 0, 8, 0, 9, 0 } };
		for (int i = 0; i < matConfi.length; i++) {
			for (int j = 0; j < matConfi[0].length; j++) {
				if (i == 4 && j == 2) {
					panel.add(textField);
				}
			}
		}

		panel.draw(matConfi, 't');

		while (true) {

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			p.filas = panel.getCurrentMouseRow();
			p.columnas = panel.getCurrentMouseCol();

			if (p.filas != -1 && p.columnas != -1) {

				// Dificultad
				if (p.filas == 6) {
					if (p.columnas == 1) {
						minasPH = 7;
					} else if (p.columnas == 2) {
						minasPH = 15;
					} else if (p.columnas == 3) {
						minasPH = 30;
					}
				}

				if (p.filas == 7 && p.columnas == 3) {
					
					player.nombre = textField.getText();
					panel.remove(textField);
					return minasPH;
				} else if (p.filas == 7 && p.columnas == 1) {
					panel.remove(textField);
					return minas;
				}
			}

		}
	}

	/**
	 * Aquesta funció mostra per pantalla les instruccions del joc.
	 * 
	 * @param panel (Board)
	 */
	public static void ajuda(Board panel) {

		Posicion p = new Posicion();

		String[] ajuda = { " ", " Buscaminas! ", " En prémer sobre les cel·les", " apareix una zona revelada i ",
				" números que determinen la proximitat ", " d'una mina", " Enrere " };
		panel.setText(ajuda);
		int[] color = { 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xE2E0C8, 0xA7B49E };
		panel.setColortext(color);
		panel.setColorbackground(0x5C7285);

		int[][] matAjuda = { { 0, 0, 0, 0 }, { 6, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 3, 0, 0 },
				{ 0, 4, 0, 0 }, { 0, 5, 0, 0 }, { 0, 0, 0, 0 } };

		panel.draw(matAjuda, 't');

		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			p.columnas = panel.getCurrentMouseCol();
			p.filas = panel.getCurrentMouseRow();

			if (p.filas != -1 && p.columnas != -1) {

				if (p.filas == 1 && p.columnas == 0) {
					return;
				}
			}
		}
	}

	/**
	 * Aquesta funció controla que no estiguis fora de la matriu
	 * 
	 * @param tablero (int[][])
	 * @param k (int)
	 * @param l (int)
	 * @return boolean
	 */
	public static boolean estoyFuerisima(String[][] tablero, int k, int l) {

		if (k < 0 || k > tablero.length - 1 || l < 0 || l > tablero[0].length - 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Aquesta funció printa per consola el taulell amb les mines.
	 * 
	 * @param tablero (int[][])
	 */
	public static void printTablero(String[][] tablero) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				System.out.print(tablero[i][j] + "  ");
			}
			System.out.println();
		}
	}

	/**
	 * Aquesta funció inicialitza una matriu amb uns 
	 * 
	 * @param tablero (int[][])
	 */
	public static void initTablero(String[][] tablero) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				tablero[i][j] = "1";
			}
		}
	}

	/**
	 * Aquesta funció planta les mines dintre de la matriu. Les mines son '0')
	 * 
	 * @param tablero (int[][])
	 * @param minas (int)
	 */
	public static void plantarMinas(String[][] tablero, int minas) {
		while (minas > 0) {
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero[0].length; j++) {
					int probabilidad = rand.nextInt(0, 101);
					if (probabilidad < 2 && minas > 0 && !tablero[i][j].equals("0")) {
						tablero[i][j] = "0";
						minas--;
					}
				}
			}

		}
	}
}

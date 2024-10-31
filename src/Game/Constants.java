package Game;

import java.util.Random;
import java.util.Timer;

public class Constants {
    // Dimensiones de la pantalla y otras constantes
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int UNIT_SIZE = 25;
    public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    public static final int DELAY = 75;

    // Estado del juego
    public static final int INITIAL_BODY_PARTS = 6; // Puntos iniciales del cuerpo

    // Variables del juego
    public static final int[] x = new int[GAME_UNITS]; // Posiciones x de la serpiente
    public static final int[] y = new int[GAME_UNITS]; // Posiciones y de la serpiente
    public static int bodyParts = INITIAL_BODY_PARTS; // Parte del cuerpo inicial
    public static int applesEaten = 0; // Manzanas comido
    public static int appleX; // Posición x de la manzana
    public static int appleY; // Posición y de la manzana
    public static char direction = 'R'; // Dirección inicial
    public static boolean running = false; // Estado del juego
    public static Random random = new Random(); // Generador de números aleatorios
}

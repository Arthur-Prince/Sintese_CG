package game;

import java.util.Scanner;

import engine.GameEngine;
import engine.IGameLogic;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String nome = s.next();
		
		try {
			boolean vSync = true;
			IGameLogic gameLogic = new DummyGame(nome);
			GameEngine gameEng = new GameEngine("sistema solar", 600, 480, vSync, gameLogic);
			gameEng.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
		s.close();
	}
}
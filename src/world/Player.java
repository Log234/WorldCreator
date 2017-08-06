package world;

import world.containers.Container;

public class Player {
	static String name = "Rutherford";
	static int health = 12;
	static Container inventory = new Container("Your inventory", 5);
	
	public static void takeDamage(int dmg, String reason) {
		System.out.println(reason  + " did " + dmg + " points of damage to " + name);
		health = health - dmg;
		
		if (health < 1) {
			Level.current = Level.rooms.get("death");
			System.out.println(Level.current.description);
		}
	}
	
}
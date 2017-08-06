package world;

import java.util.HashMap;

import world.items.Item;

public class Player {
	static String name = "Rutherford";
	static int health = 12;
	static int invSlots = 5;
	static HashMap<String, Item> inventory = new HashMap<String, Item>();
	
	public static void takeDamage(int dmg, String reason) {
		System.out.println(reason  + " did " + dmg + " points of damage to " + name);
		health = health - dmg;
		
		if (health < 1) {
			Level.current = Level.rooms.get("death");
			System.out.println(Level.current.description);
		}
	}
	
	public static int addItem(String id, Item item) {
		int items = item.number;
		
		if (invSlots - items >= 0) {
			invSlots = invSlots - items;
			inventory.put(item.name, item);
			return 0;
		} else {
			
		}
	}
	
}
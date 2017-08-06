package world;

import java.util.HashMap;

import world.containers.Container;
import world.interactables.Gate;
import world.interactables.Interactable;
import world.interactables.InteractableContainer;
import world.interactables.Toggle;
import world.items.Item;
import world.items.LightSource;

public class Level {
	String[] globalCmd = { "go", "inventory" };

	static HashMap<String, Room> rooms = new HashMap<String, Room>();
	public static HashMap<String, Interactable> interactables = new HashMap<String, Interactable>();
	static HashMap<String, Item> items = new HashMap<String, Item>();
	public static Room current;

	boolean victory = false;

	Level() {
		LightSource lighter = new LightSource("Lighter","With a flick of your thumb, the lighter flames alight.",
				"You flick the cap of the lighter on, putting out the flame.",
				"The lighter is old and worn, the previous owner certainly must have used it a lot.");
		InteractableContainer box = new InteractableContainer(new Container("box", 3, lighter), "The metal box is very dusty, some rust has started eating away at the bottom of it.", "With some squeeking from the rusty hinges, the box swinges easily open.");
		Level.interactables.put("oldBox", box);
		current = new Room();
		current.id = "entrance";
		current.description = "You enter the pyramid, it's dark and damp. There is one passage ahead of you and there is a small box in the dust to the left of you.";
		current.movement.put("forward", "hallway");
		current.interactables.put("box", "oldBox");
		rooms.put(current.id, current);

		current = new Room();
		current.id = "hallway";
		current.description = "As you move forward it grows darker. You pull out your trusty lighter, now with the place lighting up, you can see that you are standing in a hallway and there is a lever on your right hand.";
		current.movement.put("forward", "hallway2");
		Toggle lever = new Toggle(
				"You pull the lever with great force and a loud clang followed by a low hum can be heard as it falls into place.",
				"As you push the lever back with some effort a great clang echoes down the halls and the hum stops.",
				"The lever is quite dusty, some cobwebs is spun between it and the wall.");
		interactables.put("lever1", lever);
		current.interactables.put("lever", "lever1");
		rooms.put(current.id, current);

		current = new Room();
		current.id = "hallway2";
		current.description = "You enter a new hallway, there is a massive dark gate up ahead.";
		current.movement.put("back", "hallway");
		Gate gate = new Gate("lever1", current.movement, "forward", "exit", "The gate swings open at a light touch",
				"The gate won't budge, no matter how hard you push",
				"The gate is rising up infront of you, looking ominous and immensly heavy.");
		interactables.put("gate1", gate);
		current.interactables.put("dark gate", "gate1");
		rooms.put(current.id, current);

		current = new Room();
		current.id = "exit";
		current.description = "You find yourself safely out of the pyramid again, what a short journey.";
		rooms.put(current.id, current);
	}

	public void start() {
		current = rooms.get("entrance");
		System.out.println(current.description);

		while (!victory) {
			// Printer ut tilgjenglige commands
			System.out.print("What do you do?: ");
			for (String cmd : globalCmd) {
				System.out.print(cmd + " ");
			}

			if (current.interactables.size() > 0)
				System.out.print("interact inspect ");

			System.out.println();

			String cmd = Utilities.readCommand("Action");

			// Sjekker vi hvilken command brukeren skrev
			switch (cmd) {
			case "go":
				move();
				break;

			case "interact":
				check(true);
				break;

			case "inspect":
				check(false);
				break;

			case "inventory":
				Player.inventory.browse();
				break;

			default:
				System.out.println("Unknown command");
				break;
			}

			// Sjekker vi om vi er i exit rommet
			if (current.id.equals("exit"))
				victory = true;
		}
	}

	void move() {
		System.out.print("Direction: ");
		for (String direction : current.movement.keySet())
			System.out.print(direction + ", ");
		System.out.println("cancel");

		String direction = Utilities.readCommand("Direction");

		if (current.movement.containsKey(direction)) {
			current = rooms.get(current.movement.get(direction));
			System.out.println(current.description);
		} else if (direction.equals("cancel")) {
			return;
		} else {
			System.out.println("You cannot move that way!");
		}
	}

	void check(boolean interact) {
		System.out.print("Items: ");
		for (String item : current.interactables.keySet())
			System.out.print(item + ", ");
		System.out.println("cancel");

		String item = Utilities.readCommand("Item");

		if (current.interactables.containsKey(item)) {
			if (interact)
				interactables.get(current.interactables.get(item)).interact();
			else
				interactables.get(current.interactables.get(item)).inspect();
		} else if (item.equals("cancel")) {
			return;
		} else {
			System.out.println("There is no such item here.");
		}
	}

}

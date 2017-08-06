package world.containers;

import java.util.HashMap;

import world.Level;
import world.Player;
import world.Utilities;
import world.interactables.InteractableContainer;
import world.items.Item;

public class Container {
	String name;
	int size, availableSlots;
	HashMap<String, ItemStack> stacks = new HashMap<String, ItemStack>();

	// Constructors

	public Container(String name, int size) {
		this.name = name;
		this.size = size;
		this.availableSlots = size;
	}

	public Container(String name, int size, Item item) {
		this.name = name;
		this.size = size;
		stacks.put(item.name, new ItemStack(item));
		this.availableSlots = size - 1;
	}

	public Container(String name, int size, ItemStack stack) {
		this.name = name;
		this.size = size;
		stacks.put(stack.getType(), stack);
		this.availableSlots = stack.getSize();
	}

	// Methods

	public boolean addItem(Item item) {
		if (size == 0 || availableSlots > 0) {
			stacks.put(item.name, new ItemStack(item));
			return true;
		} else {
			fullInventory();
			return false;
		}
	}

	public ItemStack addItem(ItemStack stack) {
		ItemStack localStack = new ItemStack();
		if (stacks.containsKey(stack.getType()))
			localStack = stacks.get(stack.getType());

		while (stack.getSize() > 0 && (availableSlots > 0 || size == 0)) {
			localStack.add(stack.take());
			availableSlots--;
		}

		stacks.put(localStack.getType(), localStack);

		if (availableSlots == 0 && size != 0)
			fullInventory();

		return stack;
	}

	public ItemStack take(String item) {
		if (stacks.containsKey(item)) {
			return stacks.get(item).take(1);
		} else {
			System.out.println(name + " contains no such item.");
			return null;
		}
	}
	
	public ItemStack take(String item, int amount) {
		if (stacks.containsKey(item)) {
			ItemStack newStack = stacks.get(item).take(amount);
			
			if (stacks.get(item).getSize() == 0)
				stacks.remove(item);
			
			return newStack;
		} else {
			System.out.println(name + " contains no such item.");
			return null;
		}
	}

	public ItemStack retrieve(int max) {
		if (max == 0) {
			return null;
		}
		System.out.println("------------- " + name + " -------------");
		for (ItemStack stack : stacks.values())
			System.out.println(stack.getSize() + "x " + stack.getType());
		String item = Utilities.readCommand("Item");
		
		ItemStack selection;
		
		if (stacks.containsKey(item))
			selection = stacks.get(item);
		else {
			System.out.println("Your inventory does not contain that item.");
			return null;
		}
		
		if (selection.getSize() == 1) {
			stacks.remove(item);
			return selection;
		}
		
		System.out.print("Select amount: 1 - ");
		
		if (max > selection.getSize())
			System.out.println(selection.getSize());
		else
			System.out.println(max);
		
		try {
			int amount = Integer.parseInt(Utilities.readCommand("Amount"));
			return take(item, amount);
		} catch (NumberFormatException e) {
			System.out.println("There is no such number.");
		}
		return null;
	}

	public void browse() {
		System.out.println("------------- " + name + " -------------");
		for (ItemStack stack : stacks.values())
			System.out.println(stack.getSize() + "x " + stack.getType());

		System.out.print("\nWhat do you do?: interact inspect ");
		if (name.equals("Your inventory")) {
			System.out.println("drop cancel");
		} else {
			System.out.println("take put cancel");
		}
		String cmd = Utilities.readCommand("Action");

		switch (cmd) {
		case "interact":
			check(true);
			break;

		case "inspect":
			check(false);
			break;

		case "drop":
			drop();
			break;

		case "take":
			take();
			break;

		case "put":
			put();
			break;

		case "cancel":
			return;

		default:
			System.out.println("You appear uncertain of what to do.");
			return;
		}

	}

	// Utilities

	private void check(boolean interact) {
		String item = Utilities.readCommand("Item");

		if (stacks.containsKey(item)) {
			if (interact)
				stacks.get(item).get().interact();
			else
				stacks.get(item).get().inspect();
		} else {
			System.out.println("You could not find that item in your inventory.");
		}
	}

	private void fullInventory() {
		System.out.println(name + " is full.");
	}

	private void drop() {
		InteractableContainer pile;
		if (Level.interactables.containsKey(Level.current.id + "DroppedItems")) {
			pile = (InteractableContainer) Level.interactables.get(Level.current.id + "DroppedItems");
		} else {
			Level.current.interactables.put("Pile of items", Level.current.id + "DroppedItems");
			pile = new InteractableContainer(new Container("Pile of items", size), "There is a pile of items on the floor, it seems quite recent... Infact you begin to recognize some of them as things you left behind before!", "You start searching through the pile and find a bunch of things that might still be of use.");
		}
		
		String item = Utilities.readCommand("Item");
		if (stacks.containsKey(item)) {
			
			if (stacks.get(item).getSize() == 1) {
				pile.addItem(take(item));
				Level.interactables.put(Level.current.id + "DroppedItems", pile);
			} else {
				
				ItemStack selection = stacks.get(item);
				System.out.print("Select amount: 1 - " + selection.getSize());
				try {
					int amount = Integer.parseInt(Utilities.readCommand("Amount"));
					pile.addItem(take(item, amount));
					Level.interactables.put(Level.current.id + "DroppedItems", pile);
				} catch (NumberFormatException e) {
					System.out.println("There is no such number.");
				}
			}
		} else {
			System.out.println("You could not find that item in your inventory.");
		}
	}

	private void take() {
		String item = Utilities.readCommand("Item");
		if (stacks.containsKey(item)) {
			if (stacks.get(item).getSize() == 1)
				addItem(Player.inventory.addItem(take(item)));
			else {
				ItemStack selection = stacks.get(item);
				
				System.out.print("Select amount: 1 - ");
				
				if (Player.inventory.availableSlots > selection.getSize())
					System.out.println(selection.getSize());
				else
					System.out.println(Player.inventory.availableSlots);
				
				try {
					int amount = Integer.parseInt(Utilities.readCommand("Amount"));
					addItem(Player.inventory.addItem(take(item, amount)));
				} catch (NumberFormatException e) {
					System.out.println("There is no such number.");
				}
			}
		} else {
			System.out.println("You could not find that item in your inventory.");
		}
	}

	private void put() {
		ItemStack newStack = Player.inventory.retrieve(availableSlots);
		System.out.println(newStack.getSize() + "x" + newStack.getType() + " removed from your inventory.");
		addItem(newStack);
	}
}

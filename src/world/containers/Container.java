package world.containers;

import java.util.HashMap;

import world.Utilities;
import world.items.Item;

public class Container {
	String name;
	int size, availableSlots;
	HashMap<String, ItemStack> stacks = new HashMap<String, ItemStack>();
	
	
	// Constructors
	
	public Container (String name, int size) {
		this.name = name;
		this.size = size;
		this.availableSlots = size;
	}
	
	public Container (String name, int size, Item item) {
		this.name = name;
		this.size = size;
		stacks.put(item.name, new ItemStack(item));
		this.availableSlots = size-1;
	}
	
	public Container (String name, int size, ItemStack stack) {
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
			localStack.add(stack.get());
			stack.remove(1);
			availableSlots--;
		}
		
		stacks.put(localStack.getType(), localStack);
		
		if (availableSlots == 0)
			fullInventory();
			
		return stack;
	}
	
	
	public void browse() {
		System.out.println("------------- " + name + " -------------");
		for (ItemStack stack : stacks.values())
			System.out.println(stack.getSize() + "x " + stack.getType());

		System.out.println("\nWhat do you do?: interact inspect cancel");
		String cmd = Utilities.readCommand("Action");

		boolean interact = false;

		switch (cmd) {
		case "interact":
			interact = true;
			break;

		case "inspect":
			interact = false;
			break;

		case "cancel":
			return;

		default:
			System.out.println("You appear uncertain of what to do.");
			return;
		}

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
	
	// Utilities
	
	private void fullInventory() {
		System.out.println(name + " is full.");
	}
}

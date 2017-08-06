package world.containers;

import java.util.ArrayList;

import world.items.Item;

public class ItemStack {
	private String type;
	private ArrayList<Item> stack = new ArrayList<Item>();
	
	public ItemStack() {
		
	}
	
	public ItemStack(Item item) {
		type = item.name;
		stack.add(item);
	}

	public String getType() {
		return type;
	}

	public int getSize() {
		return stack.size();
	}

	public Item get() {
		return stack.get(0);
	}
	
	public Item take() {
		Item returned = stack.get(0);
		stack.remove(0);
		return returned;
	}
	
	public ItemStack take(int amount) {
		ItemStack newStack = new ItemStack();
		
		for (int i = 0; i < amount; i++) {
			newStack.add(take());
		}
		
		return newStack;
	}

	public Item add(Item item) {
		if (item.name.equals(type)) {
			stack.add(item);
			return null;
		} else if (type == null) {
			type = item.name;
			stack.add(item);
			return null;
		} else
			return item;
	}
}

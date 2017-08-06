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

	public boolean add(Item item) {
		if (item.name.equals(type)) {
			stack.add(item);
			return true;
		} else
			return false;
	}


	public boolean remove(int number) {
		if (number <= stack.size()) {
			for (int i = 0; i < number; i++)
				stack.remove(0);
			return true;
		} else {
			return false;
		}
	}
}

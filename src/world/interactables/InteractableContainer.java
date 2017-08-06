package world.interactables;

import world.containers.Container;
import world.containers.ItemStack;

public class InteractableContainer extends Interactable {
	Container container;
	
	public InteractableContainer(Container container, String description, String action) {
		this.container = container;
		this.description = description;
		this.action = action;
	}
	
	@Override
	public void interact() {
		System.out.println(action);
		container.browse();
	}
	
	public ItemStack addItem(ItemStack stack) {
		return container.addItem(stack);
	}
	
	public ItemStack takeItem(String item, int amount) {
		return container.take(item, amount);
	}
}

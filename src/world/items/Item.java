package world.items;

public abstract class Item {
	public String name;
	String description;
	public int number = 1;
	
	public abstract void interact();
	
	public void inspect() {
		System.out.println(description);
	}
}

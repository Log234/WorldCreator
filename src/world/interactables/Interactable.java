package world.interactables;

public abstract class Interactable {
	String description = "";
	String action = "";
	
	public abstract void interact();
	
	public void inspect() {
		System.out.println(description);
	}
}

package world.interactables;

public class Toggle extends Interactable {
	boolean state = false;
	String enable;
	String disable;
	
	public Toggle(String enable, String disable, String description) {
		this.enable = enable;
		this.disable = disable;
		this.description = description;
	}
	
	@Override
	public void interact() {
		if (state) {
			System.out.println(disable);
			state = false;
		} else {
			System.out.println(enable);
			state = true;
		}
	}
}

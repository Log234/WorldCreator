package world.items;

public class LightSource extends Item {
	boolean lit = false;
	String light;
	String putOut;
	
	
	public LightSource(String name, String light, String putOut, String description) {
		this.name = name;
		this.light = light;
		this.putOut = putOut;
		this.description = description;
	}
	
	@Override
	public void interact() {
		if (lit) {
			System.out.println(putOut);
			lit = false;
		} else {
			System.out.println(light);
			lit = true;
		}

	}

}

package world.interactables;

import java.util.HashMap;

import world.Level;

public class Gate extends Interactable {
	String trigger;
	HashMap<String, String> movement;
	String path;
	String room;
	String enabled;
	String disabled;
	
	public Gate(String trigger, HashMap<String, String> movement, String path, String room, String enabled, String disabled, String description) {
		this.trigger = trigger;
		this.movement = movement;
		this.path = path;
		this.room = room;
		this.enabled = enabled;
		this.disabled = disabled;
		this.description = description;
	}
	
	@Override
	public void interact() {
		Toggle trig = (Toggle) Level.interactables.get(trigger);
		if (trig.state) {
			System.out.println(enabled);
			if (!movement.containsKey(path))
				movement.put(path, room);
		} else {
			System.out.println(disabled);
		}
	}
}

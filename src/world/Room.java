package world;
import java.util.HashMap;

public class Room {
	public String id;
	String description;
	public HashMap<String, String> movement = new HashMap<String, String>();
	public HashMap<String, String> interactables = new HashMap<String, String>();
}

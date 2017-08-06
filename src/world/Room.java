package world;
import java.util.HashMap;

public class Room {
	String id;
	String description;
	HashMap<String, String> movement = new HashMap<String, String>();
	HashMap<String, String> interactables = new HashMap<String, String>();
}

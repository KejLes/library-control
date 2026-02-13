package pio.daw;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Library implements Controlable {
    private Map<String,User> users;

    /**
     * Read the library register file (.txt) and create a library object
     * with the current status of the users.
     * @param path Library registry file path.
     * @return Library object.
     */
    public static Library fromFile(Path path){
		Library		library	= new Library();
		String		line	= null;
		String[]	splittedLine;
		String 		id;
		EventType	e = null;

		try (Scanner sc = new Scanner(path)){
			while (sc.hasNext())
			{
				line = sc.nextLine();
				splittedLine = line.split(";");
				id = splittedLine[0];
				if(splittedLine[1].equals("ENTRADA"))
					e = EventType.ENTRY;
				else
					e = EventType.EXIT;
				library.registerChange(id, e);
			}
		} catch (Exception o) {
			System.err.println("Ha habido un fallo al leer el archivo");
			System.exit(0);
		}
        return (library);
    }

    private Library(){
        this.users = new HashMap<>();
    }

    public void registerChange(String id, EventType e){
        User u = this.users.get(id);
        if(u == null){
            u = new User(id);
        }
        u.registerNewEvent(e);
        this.users.put(id, u);
    }

    public List<User> getCurrentInside()
	{
		
	}

    public  List<User> getMaxEntryUsers()
	{

	}

    public List<User> getUserList()
	{

	}

    public void printResume()
	{

	}
}

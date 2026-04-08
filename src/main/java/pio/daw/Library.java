package pio.daw;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Library implements Controlable {
    private Map<String,User>	users;
	private ArrayList<User>		usersList;

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

	/**
	 * Constructor, just initialize both fields
	 */
    private Library(){
        this.users = new HashMap<>();
		this.usersList = null;
    }

	/**
	 * A auxiliar method while its reading the input (txt), it's for save or
	 * update a new user with the corresponding state if it is inside the library
	 */
	@Override
    public void registerChange(String id, EventType e){
        User u = this.users.get(id);
        if(u == null)
            u = new User(id);
        u.registerNewEvent(e);
        this.users.put(id, u);
    }

	/**
	 * Basiclly, it converts the HashMap to an ArrayList
	 */
	public void convert_HashMap_to_Arraylist()
	{
		this.usersList = new ArrayList<>();
		for (Map.Entry<String, User> u : users.entrySet())
			usersList.add(u.getValue());
		sortArraylist();
	}

	/**
	 * Sort the ArrayList
	 */
	private void sortArraylist()
	{
		usersList.sort((a, b) -> Integer.compare(a.getIDNumber(), b.getIDNumber()));
	}

	/**
	 * Get a List of all the users that are inside, so the field of inside is true
	 */
	@Override
	public List<User> getCurrentInside()
	{
		ArrayList<User> usersInside = new ArrayList<>();
		for (int i = 0; i < usersList.size(); i++)
		{
			if (usersList.get(i).isInside())
				usersInside.add(usersList.get(i));
		}
		return (usersInside);
	}

	/**
	 * Get the user or users with max entries in the library.
	 * How it can be various users, it is a list.
	 */
	@Override
    public List<User> getMaxEntryUsers()
	{
		if (usersList == null)
			convert_HashMap_to_Arraylist();
		ArrayList<User> usersWMaxEntries = new ArrayList<>();
		int	maxEntries = -1;
		for (int i = 0; i < usersList.size(); i++)
		{
			if (usersList.get(i).getNEntries() > maxEntries)
				maxEntries = usersList.get(i).getNEntries();
		}
		for (int i = 0; i < usersList.size(); i++)
		{
			if (usersList.get(i).getNEntries() == maxEntries)
				usersWMaxEntries.add(usersList.get(i));
		}
		return (usersWMaxEntries);
	}

	/**
	 * If is not created the list, calls method convert_HashMap_to_ArrayList(), and returns de list.
	 */
	@Override
    public List<User> getUserList()
	{
		if (usersList == null)
			convert_HashMap_to_Arraylist();
		return (usersList);
	}

	/**
	 * Calls some functions that returns lists with the required information
	 * and print all the information
	 *
	 * The called methods are:
	 * 		getCurrentInside() : List
	 * 		getMaxEntryUsers() : List
	 */
	@Override
    public void printResume()
	{
		getUserList();
		List<User> usersInsideList = getCurrentInside();
		List<User> maxEntryUsersList = getMaxEntryUsers();

		System.out.print("Usuarios actualmente dentro de la biblioteca:\n");
		for(int i = 0; i < usersInsideList.size(); i++)
			System.out.print(usersInsideList.get(i).getId() + "\n");

		System.out.print("\nNúmero de entradas por usuario:\n");
		for(int i = 0; i < usersList.size(); i++)
		{
			if (usersList.get(i).getNEntries() == 0)	// Lo he pueseto porque en el ejmplo el U004 solo sale y tiene como numero de entradas = 0, y no aparece en el test
				continue;
			System.out.print(usersList.get(i).getId() + " -> " + usersList.get(i).getNEntries() + "\n");
		}

		System.out.print("\nUsuario(s) con más entradas:\n");
		for(int i = 0; i < maxEntryUsersList.size(); i++)
			System.out.print(maxEntryUsersList.get(i).getId() + "\n");

		usersInsideList		= null;		// Es la costumbre de hacer free xd
		maxEntryUsersList	= null;
	}
}

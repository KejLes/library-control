package pio.daw;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
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

    private Library(){
        this.users = new HashMap<>();
		this.usersList = null;
    }

    public void registerChange(String id, EventType e){
        User u = this.users.get(id);
        if(u == null)
            u = new User(id);
        u.registerNewEvent(e);
        this.users.put(id, u);
    }

	public void convert_HashMap_to_Arraylist()
	{
		this.usersList = new ArrayList<>();
		ArrayList<User> usersList = new ArrayList<>();
		for (Map.Entry<String, User> u : users.entrySet())
			usersList.add(u.getValue());
		sortArraylist();
	}

	private void sortArraylist()
	{
		ArrayList<User> sorted_users = new ArrayList<>();
		User lowest_user;

		lowest_user = usersList.get(0);
		for(int i = 0; i < usersList.size(); i++)
		{
			if (usersList.get(i).getIDNumber() < lowest_user.getIDNumber())
				lowest_user = usersList.get(i);
			if (i == usersList.size() - 1)
			{
				i = 0;
				//	Guardar el lowestUser en sorted_users;
				for (int j = 0; j < usersList.size(); j++)
				{
					if (usersList.get(j).getIDNumber() == lowest_user.getIDNumber())
					{
						sorted_users.add(lowest_user);
						usersList.remove(j);
						break;
					}
				}
				//	El siguitne lowestUser será el primer elemento serça el primero en usersList si todavía hay elementos
				if(!users.isEmpty())
					lowest_user = usersList.get(0);
			}
		}
		usersList = sorted_users;
		sorted_users = null;
	}

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

    public List<User> getMaxEntryUsers()
	{
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

    public List<User> getUserList()
	{
		if (usersList == null)
			convert_HashMap_to_Arraylist();
		return (usersList);
	}

    public void printResume()
	{
		ArrayList<User> usersInsideList = new ArrayList<>(getCurrentInside());
		ArrayList<User> usersList = new ArrayList<>(getUserList());
		ArrayList<User> maxEntryUsersList = new ArrayList<>(getMaxEntryUsers());

		System.out.println("Usuarios actualmente dentro de la biblioteca:");
		for(int i = 0; i < usersInsideList.size(); i++)
			System.out.println(usersInsideList.get(i).getId());

		System.out.println("\nNúmero de entradas por usuario:");
		for(int i = 0; i < usersList.size(); i++)
			System.out.println(usersList.get(i).getId() + " -> " + usersList.get(i).getNEntries());

		System.out.println("\nUsuario(s) con más entradas:");
		for(int i = 0; i < maxEntryUsersList.size(); i++)
			System.out.println(maxEntryUsersList.get(i).getId());

		usersInsideList		= null;
		usersList			= null;
		maxEntryUsersList	= null;
	}
}

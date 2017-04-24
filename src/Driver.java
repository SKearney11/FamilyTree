import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public String name;
	public String result = "";
	Scanner scan = new Scanner(System.in);
	public ArrayList<Person> people = new ArrayList();
	
	public static void main(String[] args) 
	{
		new Driver().run();
	}
	
	public void run()
	{
		try {
			readFile();
		} catch (FileNotFoundException e) {
			System.out.println("Something went wrong reading the file");
			e.printStackTrace();
		}
		System.out.println("File read successfully");
		assignParents();
		printMenu();
	}
	
	public void readFile() throws FileNotFoundException
	{
		File userFile = new File("C:/Users/skear/workspace/FamilyTree/src/Data/peopleList");
		Scanner sc = new Scanner(userFile);
		String delims = " ";
		while(sc.hasNextLine())
		{
			String person = sc.nextLine();
			String[] personTokens = person.split(delims);
			String name = personTokens[0];
			char gender = personTokens[1].charAt(0);
			int birthYear =  Integer.parseInt(personTokens[2]);
			String parent1 = personTokens[3];
			String parent2 = personTokens[4];
			people.add(new Person(name, gender, birthYear, parent1, parent2, null, null));
		}
	}
	
	public void assignParents(){
		for(int i = 0; i < people.size(); i++)
		{
			for(int k = 0; k < people.size(); k++)
			{
				if(people.get(i).parent1.equals(people.get(k).name))
				{
					people.get(i).setP_Parent1(people.get(k));
				}
				
				if(people.get(i).parent2.equals(people.get(k).getName()))
				{
					people.get(i).p_Parent2 = people.get(k);
				}
				
				//Dealing with unknown parents. These will live outside of the array
				if(people.get(i).parent1.equals("?")){
					people.get(i).setP_Parent1(new Person("Unknown", '?', 0000, "Unknown", "Unknown", null, null));
				}
				if(people.get(i).parent2.equals("?")){
					people.get(i).setP_Parent2(new Person("Unknown", '?', 0000, "Unknown", "Unknown", null, null));
				}
			}
		}
	}
	
	public void printMenu(){
		System.out.println("Main Menu");
		System.out.println("Press 1 to look up parents");
		System.out.println("Press 2 to look up all ancestors");
		System.out.println("Press 3 to look up all siblings");
		System.out.println("Press 0 to exit");
		int choice = scan.nextInt();
		mainMenu(choice);
	}
	
	public void mainMenu(int choice){
		while(choice != 0){
			switch(choice){
			case 1:
				System.out.println("Enter the name you want to look up");
				scan.nextLine();
				name = scan.nextLine();
				System.out.println(parentLookup(name));
				printMenu();
			break;
			case 2:
				System.out.println("Enter the name you want to look up");
				scan.nextLine();
				name = scan.nextLine();
				Person p = findPerson(name);
				if(!(p == null))
				{
					System.out.println(ancestorLookup(p));
				}else{
					System.out.println("Please enter a valid name");
				}
					
				printMenu();
			break;
			case 3:
				System.out.println("Enter the name you want to look up");
				scan.nextLine();
				name = scan.nextLine();
				System.out.println("Siblings = " +siblingLookup(name));
				printMenu();
			}
		System.out.println("Bye");
		System.exit(0);
		}
	}
	
	
	public String parentLookup(String name){
		String result = "unknown";
		for(int i = 0; i < people.size(); i++){
			if(people.get(i).getName().equals(name)){
				result = "The parents of " + people.get(i).getName() + " are " + people.get(i).getP_Parent1().getName() + " and " + people.get(i).getP_Parent2().getName();
				return result;
			}	
		}
		return result;	
	}
	
	public String siblingLookup(String name){
		String siblings = " ";
		Person p1;
		Person p2;
		for(int i = 0; i < people.size(); i++)
		{
			if(people.get(i).getName().equals(name))
			{
				System.out.println("person found");
				p1 = people.get(i).getP_Parent1();
				p2 = people.get(i).getP_Parent2();
				
				for(int k = 0; k < people.size(); k++)
				{
					if(people.get(k).getP_Parent1().getName().equals(p1.getName()) && people.get(k).getP_Parent2().getName().equals(p2.getName()))
					{
						siblings += (people.get(k).getName() + " ");
					}
				}
				return siblings;
			}
		}
		
		return ("No known siblings");
	}		
	
	public Person findPerson(String name){
		for(int i = 0; i < people.size(); i++){
			if(people.get(i).getName().equals(name)){
				Person result = people.get(i);
				System.out.println("Found person");
				return(result);
			}	
		}
		System.out.println("Didnt find person");
		return null;
	}

	public String ancestorLookup(Person person){	
		
		if(!person.getParent1().equals("Unknown"))
		{
			ancestorLookup(person.getP_Parent1()); 
			if(!person.getParent2().equals("Unknown"))
			{
				ancestorLookup(person.getP_Parent2()); 
				result += ("The parents of " + person.getName() + " are " + person.getP_Parent1().getName() + " and " + person.getP_Parent2().getName() + ". \n");
			}
		}
		return result;
	}
}

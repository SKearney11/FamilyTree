import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
	public String name;
	public String result = "";
	Scanner scan = new Scanner(System.in);
	HashMap<String, Person> people = new HashMap<String, Person>();
	
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
		addChildren();
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
			people.put(name, new Person(name, gender, birthYear, parent1, parent2, null, null));
		}
	}

	private void addChildren(){
		for(Person person : people.values()){
			if(person.p_Parent1!=null){
				if(!person.p_Parent1.children.contains(person))
					person.p_Parent1.children.add(person);
			}
			if(person.p_Parent2!=null){
				if(!person.p_Parent2.children.contains(person))
					person.p_Parent2.children.add(person);
			}
		}
	}
	
	public void assignParents(){
		for(Person person : people.values()){
			person.p_Parent1 = people.get(person.parent1);
			person.p_Parent2 = people.get(person.parent2);
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
				System.out.println(ancestorLookup(people.get(name)));
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
		String result = people.get(name).getParent1() + " and " + people.get(name).getParent2();
		return result;
	}
	
	public String siblingLookup(String name){
		ArrayList<String> siblings = new ArrayList<String>();
		
		Person father = people.get(name).p_Parent2;
		Person mother = people.get(name).p_Parent1;
		
		if(father != null){
			for(Person person : father.children){
				if(person.equals(people.get(name))) continue;
				if(!siblings.contains(person)) siblings.add(person.name);
			}
		}
		if(mother != null){
			for(Person person : mother.children){
				if(person.equals(people.get(name))) continue;
				if(!siblings.contains(person)) siblings.add(person.name);
			}
		}
		String siblingString = "";
		for(String sibling : siblings){
			siblingString += sibling+"  ";
		}
		return siblingString;
	}	

	public String ancestorLookup(Person p){
		if(!(p.parent1.equals("?")))
		{
			ancestorLookup(p.getP_Parent1()); 
			
			if(!p.parent2.equals("?"))
			{
				ancestorLookup(p.getP_Parent2()); 
				result += ("The parents of " + p.getName() + " are " + p.getParent1() + " and " + p.getParent2() + ". \n");
			}
		}
		return result;
	}
}

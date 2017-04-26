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
	
	//reads in file using scanner and adds to the hashmap of people
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

	//adds children to an arraylist stored in each person
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
	
	//builds the tree by assigning parents to each node
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
		System.out.println("Press 4 to add a user");
		System.out.println("Press 5 edit a user");
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
			
			break;
			case 4:
				addPerson();
				printMenu();
				break;
			case 5:
				editPerson();
				printMenu();
			}
		}
		System.out.println("Bye");
		System.exit(0);
	}
	 
	
	//returns the parents of a user
	public String parentLookup(String name){
		String result = people.get(name).getParent1() + " and " + people.get(name).getParent2();
		return result;
	}
	
	//checks the children arraylist of each parent node for children and returns them
	public String siblingLookup(String name){
		ArrayList<String> siblings = new ArrayList<String>();
		
		Person father = people.get(name).p_Parent2;
		Person mother = people.get(name).p_Parent1;
		
		if(father != null){
			for(Person person : father.children){
				if(person.equals(people.get(name))) continue;
				if(!siblings.contains(person.name)) siblings.add(person.name);
			}
		}
		if(mother != null){
			for(Person person : mother.children){
				if(person.equals(people.get(name))) continue;
				if(!siblings.contains(person.name)) siblings.add(person.name);
			}
		}
		String siblingString = "";
		for(String sibling : siblings){
			siblingString += sibling+"  ";
		}
		return siblingString;
	}	

	//recursivly finds all ancestors of a person
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
	
	//adds a user and assigns parents
	void addPerson(){
		scan.nextLine();
		System.out.println("Enter name");
		String fName = scan.nextLine();
		System.out.println("Enter Gender (m/f)");
		char gender = scan.nextLine().charAt(0);
		System.out.println("Enter Birth Year (e.g: 1994)");
		int birthYear = Integer.parseInt(scan.nextLine());
		System.out.println("Enter parent 1 (mother)");
		String parent1 = scan.nextLine();
		System.out.println("Enter parent 2 (father)");
		String parent2 = scan.nextLine();
		people.put(fName, new Person(fName, gender, birthYear, parent1, parent2, null, null));

		
		for(Person person : people.values()){
			if(people.get(fName).parent1.equals(person.getName()))
			{
				people.get(fName).setP_Parent1(person);
			}
			if(people.get(fName).parent2.equals(person.getName()))
			{
				people.get(fName).setP_Parent2(person);
			}
		}
	}
	
	//Edits a users details and assigns parents
	void editPerson(){
		String change = " ";
		System.out.println("Enter name of user you would like to edit");
		scan.nextLine();
		String editName = scan.nextLine();
		System.out.println("Data for user = Name: " + people.get(editName).getName() + " Gender: " + people.get(editName).getGender() + " Birth Year: " + people.get(editName).getBirthYear() + " Parent 1: " + people.get(editName).getParent1() + " Parent 2: " + people.get(editName).getParent2());
		System.out.println("Press 1 to edit name");
		System.out.println("Press 2 to edit gender");
		System.out.println("Press 3 to edit year");
		System.out.println("Press 4 to edit mother");
		System.out.println("Press 5 to edit father");
		int input = scan.nextInt();
		switch(input){
		case 1:
			System.out.println("Enter new Name (Changing name will not change the key! Only the name value of the node will change!!!!)");
			scan.nextLine();
			change = scan.nextLine();
			people.get(editName).setName(change);
			change = " ";
			input = 0;
		break;
		case 2:
			scan.nextLine();
			System.out.println("Enter new gender");
			change = scan.nextLine();
			people.get(editName).setGender(change.charAt(0));
			change = " ";
			input = 0;
		break;
		case 3:
			System.out.println("Enter year");
			scan.nextLine();
			change = scan.nextLine();
			people.get(editName).setBirthYear(Integer.parseInt(change));	
			change = " ";
			input = 0;
		break;
		case 4:
			System.out.println("Enter parent1 (mother)");
			scan.nextLine();
			change = scan.nextLine();
			people.get(editName).setParent1(change);
			for(Person person : people.values()){
				if(people.get(editName).parent1.equals(person.getName()))
				{
					people.get(editName).setP_Parent1(person);
				}
			}
			change = " ";
			input = 0;
		break;
		case 5:
			System.out.println("Enter parent2 (father)");
			scan.nextLine();
			change = scan.nextLine();
			people.get(editName).setParent2(change);
			for(Person person : people.values()){
				if(people.get(editName).parent2.equals(person.getName()))
				{
					people.get(editName).setP_Parent2(person);
				}
			}
			change = " ";
			input = 0;
		}
		System.out.println("New data for user = Name: " + people.get(editName).getName() + " Gender: " + people.get(editName).getGender() + " Birth Year: " + people.get(editName).getBirthYear() + " Parent 1: " + people.get(editName).getParent1() + " Parent 2: " + people.get(editName).getParent2());
	}
}

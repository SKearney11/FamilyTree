import java.util.ArrayList;

public class Person {
	String name;
	char gender;
	int birthYear;
	String parent1;
	String parent2;
	Person p_Parent1;
	Person p_Parent2;
	ArrayList<Person> children;
	
	public Person(String name, char gender, int birthYear, String parent1, String parent2, Person p_Parent1,
			Person p_Parent2) {
		this.name = name;
		this.gender = gender;
		this.birthYear = birthYear;
		this.parent1 = parent1;
		this.parent2 = parent2;
		this.p_Parent1 = p_Parent1;
		this.p_Parent2 = p_Parent2;
		children = new ArrayList<Person>(); 
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public int getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	public String getParent1() {
		return parent1;
	}
	public void setParent1(String parent1) {
		this.parent1 = parent1;
	}
	public String getParent2() {
		return parent2;
	}
	public void setParent2(String parent2) {
		this.parent2 = parent2;
	}
	public Person getP_Parent1() {
		return p_Parent1;
	}
	public void setP_Parent1(Person p_Parent1) {
		this.p_Parent1 = p_Parent1;
	}
	public Person getP_Parent2() {
		return p_Parent2;
	}
	public void setP_Parent2(Person p_Parent2) {
		this.p_Parent2 = p_Parent2;
	}
}

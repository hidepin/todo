package todo.domain.service.todo;

public class Person {

	private final String firstName;
	private final String lastName;
	private final int age;
	private final String genderName;

	public Person(String firstName, String lastName, int age, String genderName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.genderName = genderName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getGenderName() {
		return genderName;
	}
}
package treblewallet.bitbox.test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public abstract class ArrayReflectionTest {
	class Person{
		String name;
		int age;
		

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}
		
	}

	public Object invoke(Method method, Object[] args) {
		return null;
	}
	
	abstract public Object arrayMethod(List<Person> persons) ;
	
	@Test
	public void test() throws Exception {
		LinkedList<Person> persons = new LinkedList<>();
		persons.add(new Person("Nikola Tesla", 82));
		Object[] args = {  };
		Method arrayMethod = this.getClass().getMethods()[1];
		invoke(arrayMethod, args);
	}
}

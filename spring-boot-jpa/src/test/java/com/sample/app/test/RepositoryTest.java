package com.sample.app.test;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.app.model.User;
import com.sample.app.repository.SimpleUserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private SimpleUserRepository simpleUserRepository;

    static User user1, user2, user3;

	static {

		user1 = new User();
		user1.setUsername("foobar");
		user1.setFirstname("firstname");
		user1.setLastname("lastname");
		user1.setAge(20);
		
		user2 = new User();
		user2.setUsername("foobar2");
		user2.setFirstname("firstname2");
		user2.setLastname("lastname2");
		user2.setAge(30);
		
		user3 = new User();
		user3.setUsername("foobar3");
		user3.setFirstname("firstname3");
		user3.setLastname("lastname3");
		user3.setAge(40);
	}

	@Test
	public void findSavedUserById() {

		user1 = simpleUserRepository.save(user1);

		System.out.println(simpleUserRepository.findOne(user1.getId()));
	}

	@Test
	public void findSavedUserByLastname(){

		user1 = simpleUserRepository.save(user1);

		List<User> users = simpleUserRepository.findByLastname("lastname");
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameAndLastname() {
		
		user2 = simpleUserRepository.save(user2);
		
		List<User> users = simpleUserRepository.findByFirstnameAndLastname("firstname2", "lastname2");
		users.forEach(System.out::println);
	}

	@Test
	public void findByFirstnameOrLastname() {

		user1 = simpleUserRepository.save(user1);

		List<User> users = simpleUserRepository.findByFirstnameOrLastname("lastname");

		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameIs() {
		
		user1 = simpleUserRepository.save(user1);
		
		List<User> users = simpleUserRepository.findByFirstnameIs("firstname");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameEquals() {
		
		user1 = simpleUserRepository.save(user1);
		
		List<User> users = simpleUserRepository.findByFirstnameEquals("firstname");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeBetween() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeBetween(20, 30);
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeLessThan() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeLessThan(30);
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeLessThanEqual() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeLessThanEqual(30);
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeGreaterThan() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeGreaterThan(30);
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeGreaterThanEqual() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeGreaterThanEqual(30);
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeAfter() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeAfter(30);
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeBefore() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeBefore(30);
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeIsNull() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeIsNull();
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeNotNull() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeNotNull();
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeIsNotNull() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeIsNotNull();
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameLike() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByFirstnameLike("firstname");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameNotLike() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByFirstnameNotLike("firstname");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameStartingWith() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByFirstnameStartingWith("firstname");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameEndingWith() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByFirstnameEndingWith("name2");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameContaining() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByFirstnameContaining("name");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameStartingWithOrderByAgeDesc() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByFirstnameStartingWithOrderByAgeDesc("firstname");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByLastnameNot() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByLastnameNot("lastname3");
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeIn() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeIn(Arrays.asList(20, 30));
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByAgeNotIn() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByAgeNotIn(Arrays.asList(40));
		
		users.forEach(System.out::println);
	}
	
	@Test
	public void findByFirstnameIgnoreCase() {
		
		simpleUserRepository.save(Arrays.asList(user1, user2, user3));
		
		List<User> users = simpleUserRepository.findByFirstnameIgnoreCase("FirstName");
		
		users.forEach(System.out::println);
	}

	@Test
	public void useOptionalAsReturnAndParameterType() {

		simpleUserRepository.save(user1);
		
		boolean isPresent = simpleUserRepository.findByUsername(Optional.of("foobar")).isPresent();

		System.out.println(isPresent);
	}

	@Test
	public void removeByLastname() {

		User user2 = new User();
		user2.setLastname(user1.getLastname());

		User user3 = new User();
		user3.setLastname("no-positive-match");

		simpleUserRepository.save(Arrays.asList(user1, user2, user3));

		long rows = simpleUserRepository.removeByLastname(user1.getLastname());
		System.out.println(rows);
	}

	@Test
	public void useSliceToLoadContent() {

		simpleUserRepository.deleteAll();

		int totalNumberUsers = 11;
		List<User> source = new ArrayList<User>(totalNumberUsers);

		for (int i = 1; i <= totalNumberUsers; i++) {

			User user = new User();
			user.setLastname(user1.getLastname());
			user.setUsername(user.getLastname() + "-" + String.format("%03d", i));
			source.add(user);
		}

		simpleUserRepository.save(source);
		
		Slice<User> users = simpleUserRepository.findByLastnameOrderByUsernameAsc(user1.getLastname(), new PageRequest(1, 5));

		users.forEach(System.out::println);
	}

	@Test
	public void findFirst2ByOrderByLastnameAsc() {

		User user0 = new User();
		user0.setLastname("lastname-0");

		User user1 = new User();
		user1.setLastname("lastname-1");

		User user2 = new User();
		user2.setLastname("lastname-2");

		simpleUserRepository.save(Arrays.asList(user2, user1, user0));

		List<User> users = simpleUserRepository.findFirst2ByOrderByLastnameAsc();

		users.forEach(System.out::println);
	}

	@Test
	public void findTop2ByWithSort() {

		User user0 = new User();
		user0.setLastname("lastname-0");

		User user1 = new User();
		user1.setLastname("lastname-1");

		User user2 = new User();
		user2.setLastname("lastname-2");

		simpleUserRepository.save(Arrays.asList(user2, user1, user0));

		List<User> resultAsc = simpleUserRepository.findTop2By(new Sort(ASC, "lastname"));

		resultAsc.forEach(System.out::println);

		List<User> resultDesc = simpleUserRepository.findTop2By(new Sort(DESC, "lastname"));

		resultDesc.forEach(System.out::println);
	}

	@Test
	public void findByFirstnameOrLastnameUsingSpEL() {

		User first = new User();
		first.setLastname("lastname");

		User second = new User();
		second.setFirstname("firstname");

		User third = new User();

		simpleUserRepository.save(Arrays.asList(first, second, third));

		User reference = new User();
		reference.setFirstname("firstname");
		reference.setLastname("lastname");

		Iterable<User> users = simpleUserRepository.findByFirstnameOrLastname(reference);

		users.forEach(System.out::println);
	}
	
	@Test
	public void findByLastnameNativeQuery() {

		user1 = simpleUserRepository.save(user1);

		List<User> users = simpleUserRepository.findByLastname("lastname");

		users.forEach(System.out::println);
	}

}

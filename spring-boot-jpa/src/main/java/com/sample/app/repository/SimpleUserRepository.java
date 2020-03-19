package com.sample.app.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sample.app.model.User;

public interface SimpleUserRepository extends CrudRepository<User, Long> {
	
	/**
	 * Find the user with the given username. This method will be translated into a query using the
	 * {@link javax.persistence.NamedQuery} annotation at the {@link User} class.
	 * 
	 * @param lastname
	 * @return
	 */
	User findByTheUsersName(String username);

	/**
	 * Uses {@link Optional} as return and parameter type.
	 * 
	 * @param username
	 * @return
	 */
	Optional<User> findByUsername(Optional<String> username);

	/**
	 * Find all users with the given lastname. This method will be translated into a query by constructing it directly
	 * from the method name as there is no other query declared.
	 * 
	 * @param lastname
	 * @return
	 */
	@Query(value = "SELECT * FROM user WHERE lastname = :name", nativeQuery = true)	//nativeQuery	原生查询
	List<User> findByLastname(@Param(value="name")String name);

	/**
	 * Returns all users with the given firstname. This method will be translated into a query using the one declared in
	 * the {@link Query} annotation declared one.
	 * 
	 * @param firstname
	 * @return
	 */
	@Query("select u from User u where u.firstname = :firstname")
	List<User> findByFirstname(String firstname);
	
	@Query("select u from User u where u.firstname = :firstname and u.lastname = :lastname")
	List<User> findByFirstnameAndLastname(@Param(value="firstname")String firstname, @Param(value="lastname")String lastname);
	
	List<User> findByFirstnameIs(String firstname);
	
	List<User> findByFirstnameEquals(String firstname);
	
	List<User> findByAgeBetween(int startAge, int endAge);
	
	List<User> findByAgeLessThan(int age);
	
	List<User> findByAgeLessThanEqual(int age);
	
	List<User> findByAgeGreaterThan(int age);
	
	List<User> findByAgeGreaterThanEqual(int age);
	
	List<User> findByAgeAfter(int age);
	
	List<User> findByAgeBefore(int age);
	
	List<User> findByAgeIsNull();
	
	List<User> findByAgeNotNull();
	
	List<User> findByAgeIsNotNull();
	
	List<User> findByFirstnameLike(String firstname);
	
	List<User> findByFirstnameNotLike(String firstname);
	
	List<User> findByFirstnameStartingWith(String firstname);
	
	List<User> findByFirstnameEndingWith(String firstname);
	
	List<User> findByFirstnameContaining(String firstname);
	
	List<User> findByFirstnameStartingWithOrderByAgeDesc(String firstname);
	
	List<User> findByLastnameNot(String lastname);
	
	List<User> findByAgeIn(Collection<Integer> age);
	
	List<User> findByAgeNotIn(Collection<Integer> age);
	
	List<User> findByFirstnameIgnoreCase(String firstname);
	
	/**
	 * Returns all users with the given name as first- or lastname. This makes the query to method relation much more
	 * refactoring-safe as the order of the method parameters is completely irrelevant.
	 * 
	 * @param name
	 * @return
	 */
	@Query("select u from User u where u.firstname = :name or u.lastname = :name")
//	@Query("select u from User u where u.firstname = ?1 or u.lastname = ?1")	//1表示第一个参数
	List<User> findByFirstnameOrLastname(@Param(value="name")String name);
	
	/**
	 * Returns the total number of entries deleted as their lastnames match the given one.
	 * 
	 * @param lastname
	 * @return
	 */
	Long removeByLastname(String lastname);

	/**
	 * Returns a {@link Slice} counting a maximum number of {@link Pageable#getPageSize()} users matching given criteria
	 * starting at {@link Pageable#getOffset()} without prior count of the total number of elements available.
	 * 
	 * @param lastname
	 * @param page
	 * @return
	 */
	Slice<User> findByLastnameOrderByUsernameAsc(String lastname, Pageable page);

	/**
	 * Return the first 2 users ordered by their lastname asc.
	 * 
	 * <pre>
	 * Example for findFirstK / findTopK functionality.
	 * </pre>
	 * 
	 * @return
	 */
	List<User> findFirst2ByOrderByLastnameAsc();

	/**
	 * Return the first 2 users ordered by the given {@code sort} definition.
	 * 
	 * <pre>
	 * This variant is very flexible because one can ask for the first K results when a ASC ordering
	 * is used as well as for the last K results when a DESC ordering is used.
	 * </pre>
	 * 
	 * @param sort
	 * @return
	 */
	List<User> findTop2By(Sort sort);

	/**
	 * Return all the users with the given firstname or lastname. Makes use of SpEL (Spring Expression Language).
	 *
	 * @param user
	 * @return
	 */
	@Query("select u from User u where u.firstname = :#{#user.firstname} or u.lastname = :#{#user.lastname}")
	Iterable<User> findByFirstnameOrLastname(@Param("user") User user);
	

}

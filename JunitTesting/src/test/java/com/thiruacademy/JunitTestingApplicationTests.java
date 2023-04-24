package com.thiruacademy;

import com.thiruacademy.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JunitTestingApplicationTests {

	@LocalServerPort
	private int port;

	private static String baseUrl = "http://localhost";

	@Autowired
	private static RestTemplate restTemplate;

	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void createUri(){
		baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/products");
	}

	@Autowired
	private TestRepository testRepository;

	@Test
	public void addProduct(){
		Product product = new Product("Book", "200", "Stationary");
		Product response = restTemplate.postForEntity(baseUrl+"/save", product, Product.class).getBody();
		assertEquals("Book", response.getName());
		assertEquals(1, testRepository.findAll().size());

	}

	@Test
	@Sql(statements = "insert into table_product(id, name, price, department) values (1, 'Pen', '30', 'Stattionary')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from table_product where name='Pen'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getAllProducts(){
		//Product product = new Product("Book", "200", "Stationary");
		List<Product> response = restTemplate.getForObject(baseUrl+"/fetchAll", List.class);
		assertEquals(2, response.size());
		assertEquals(2, testRepository.findAll().size());

	}

	@Test
	@Sql(statements = "insert into table_product(id, name, price, department) values (2, 'Book2', '300', 'Stattionary')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from table_product where name='Book2'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getProductById(){
		//Product product = new Product("Book", "200", "Stationary");
		Product response = restTemplate.getForObject(baseUrl+"/fetch/{id}", Product.class, 2);
		//assertEquals(2, response.size());
		//assertEquals(2, testRepository.findAll().size());
		assertAll(
				()->assertNotNull(response),
				()->assertEquals(2, response.getId()),
				()-> assertEquals("Book2", response.getName())
		);

	}

	@Test
	@Sql(statements = "insert into table_product(id, name, price, department) values (3, 'Book3', '300', 'Stattionary')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from table_product where name='Book3'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getUpdateProductById(){
		Product product = new Product("Book3", "200", "Stationary");
		Product response = restTemplate.getForObject(baseUrl+"/fetch/{id}", Product.class, 3);
		restTemplate.put(baseUrl+"/update/{id}", product, 3);
		Product response1 = restTemplate.getForObject(baseUrl+"/fetch/{id}", Product.class, 3);
		assertAll(
				()->assertNotNull(response1),
				()->assertEquals(3, response.getId()),
				()-> assertEquals("Book3", response.getName())
		);

	}

	@Test
	@Sql(statements = "insert into table_product(id, name, price, department) values (5, 'Book4', '300', 'Stattionary')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void deletteProductById(){
		List<Product> response = restTemplate.getForObject(baseUrl+"/fetchAll", List.class);
		assertEquals(2, testRepository.findAll().size());
		restTemplate.delete(baseUrl+"/delete/{id}", 5);
		assertEquals(1, testRepository.findAll().size());
	}


}

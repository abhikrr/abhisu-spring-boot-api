package com.abhisu.abhisu_spring_boot_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class AbhisuSpringBootApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainTest() {
		assertDoesNotThrow(() -> AbhisuSpringBootApiApplication.main(new String[] {}));
	}

}

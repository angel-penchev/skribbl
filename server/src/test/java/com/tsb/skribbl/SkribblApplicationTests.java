package com.tsb.skribbl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class SkribblApplicationTests {

//	@Autowired
//	private MockMvc mvc;

	@Test
	void contextLoads() {
	}

//	@Test
//	void testCallWithCorrectRole() throws Exception {
//		String[] services = readServices();
//		String enrollToken = assertAuthenticate("user", "password", true);
//		String deskToken = assertAuthenticate("desk1", "парола", true);
//
//		assertEnroll(services[0], 1, enrollToken, true); //1
//		assertEnroll(services[1], 1, enrollToken, true); //2
//		assertEnroll(services[0], 2, enrollToken, true); //3
//		assertEnroll(services[0], 3, enrollToken, true); //4
//		assertEnroll(services[2], 1, enrollToken, true); //5
//		assertEnroll(services[1], 2, enrollToken, true); //6
//
//		String[] onlyTwo = new String[] {services[0], services[2]};
//
//		assertCall(onlyTwo, 2,services[0], 1, deskToken, true);
//		assertCall(new String[0], 2, null, -1, deskToken, false);
//		assertCall(new String[] {UUID.randomUUID().toString()}, 2,null, -1, deskToken, false);
//		assertCall(new String[] {services[1]}, 3, services[1],2, deskToken, true);
//		assertEnroll(services[1], 2, enrollToken, true); //7
//		assertCall(new String[] {services[1], services[2]}, 4, services[2], 5, deskToken, true);
//	}

}

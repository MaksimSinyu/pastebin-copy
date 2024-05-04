package com.pastebin.Pastebin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PastebinApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testCreateMillionPastes() {
		int numberOfPastes = 1_000_000;
		ResponseEntity<String> response;
		for (int i = 0; i < numberOfPastes; i++) {
			String text = "Sample text " + i;
			response = restTemplate.postForEntity("/api/v1/paste", text, String.class);
			assert response.getStatusCode().is2xxSuccessful();
		}
	}
}

package com.jvms.i18neditor;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;
import java.util.SortedMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;

import static org.mockito.Mockito.*;

class OwnResourceTest {
	
	private Resource resource;
	
	static Stream<String> badInputs() {
	  return Stream.of("", ".key", "key.", null);
	}
	
	@BeforeEach
	void init() {
		resource = new Resource(ResourceType.JSON, null, new Locale("en"));
	}
	
	@Test
	void testStoreTranslation() {
		Resource tmp = mock(Resource.class);
		when(tmp.supportsParentValues()).thenReturn(false);
		resource.storeTranslation("key", "value");
		assertEquals(resource.getTranslation("key"), "value");
	}
	
	@Test
	void testNotStoreTranslation() {
		resource = new Resource(ResourceType.Properties, null, new Locale("en"));
		resource.storeTranslation("key", "value");
		Resource tmp = mock(Resource.class);
		verify(tmp, times(0)).notifyListeners();
	}
	
	@Test
	void returnStoreTranslation() {
		SortedMap<String,String> translations = resource.getTranslations();
		resource.storeTranslation("key", null);
		assertEquals(translations, resource.getTranslations());
		resource.storeTranslation("key", "value");
		translations = resource.getTranslations();
		resource.storeTranslation("key", "value");
		assertEquals(translations, resource.getTranslations());
		resource.storeTranslation("key", "value");
		assertEquals(translations, resource.getTranslations());
		resource.storeTranslation("key", "value2");
		assertEquals(resource.getTranslation("key"), "value2");
	}

	@ParameterizedTest
	@MethodSource("badInputs")
	void testStoreTranslationsForBadInputs(String input) {
		if(input == null) {
			assertThrows(NullPointerException.class, () -> {
				resource.storeTranslation(input, null);
			});	
		} else {
			assertThrows(IllegalArgumentException.class, () -> {
				resource.storeTranslation(input, null);
			});	
		}
	}
}

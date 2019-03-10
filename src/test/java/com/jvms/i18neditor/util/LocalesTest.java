package com.jvms.i18neditor.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class LocalesTest {
	
	static Stream<String> badStrings() {
	  return Stream.of("", "---", "___", null);
	}
	
	static Stream<String> goodStrings() {
	  return Stream.of("en_US_2", "cs_CZ_0");
	}
	
	static Stream<String> unknown() {
	  return Stream.of("FFF", "xxx", "-x");
	}
	
	@ParameterizedTest
	@MethodSource("goodStrings")
	void testParseLocale(String input) {
		assertNotEquals(Locales.parseLocale(input), null);
		Locale locale = Locales.parseLocale(input);
		String[] tmp = input.split("_");
		assertEquals(tmp[0], locale.getLanguage());
		assertEquals(tmp[1], locale.getCountry());
		assertEquals(tmp[2], locale.getVariant());
	}
	
	@ParameterizedTest
	@MethodSource("unknown")
	void testParseLocaleWithUnknownLanguages(String input) {
		assertNotEquals(Locales.parseLocale(input), null);
		Locale locale = Locales.parseLocale(input);
		String tmp = input.indexOf("-") == -1 ? input : "";
		assertEquals(tmp.toLowerCase(), locale.getLanguage());
		assertEquals(input.indexOf("-") == -1 ? "" : input.split("-")[1].toUpperCase(), locale.getCountry());
		assertEquals("", locale.getVariant());
	}

	@ParameterizedTest
	@MethodSource("badStrings")
	void testParseLocaleInvalid(String input) {
		assertEquals(null, Locales.parseLocale(input));
	}
	
}

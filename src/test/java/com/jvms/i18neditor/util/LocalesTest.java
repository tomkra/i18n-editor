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
		assertEquals(locale.getLanguage(), tmp[0]);
		assertEquals(locale.getCountry(), tmp[1]);
		assertEquals(locale.getVariant(), tmp[2]);
	}
	
	@ParameterizedTest
	@MethodSource("unknown")
	void testParseLocaleWithUnknownLanguages(String input) {
		assertNotEquals(Locales.parseLocale(input), null);
		Locale locale = Locales.parseLocale(input);
		String tmp = input.indexOf("-") == -1 ? input : "";
		assertEquals(locale.getLanguage(), tmp.toLowerCase());
		assertEquals(locale.getCountry(), input.indexOf("-") == -1 ? "" : input.split("-")[1].toUpperCase());
		assertEquals(locale.getVariant(), "");
	}

	@ParameterizedTest
	@MethodSource("badStrings")
	void testParseLocaleInvalid(String input) {
		assertEquals(Locales.parseLocale(input), null);
	}
	
}

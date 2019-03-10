package com.jvms.i18neditor.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jvms.i18neditor.Resource;
import com.jvms.i18neditor.ResourceType;

class ResourcesTest {

private Resource resource;
	
	@BeforeEach
	void init() {
		resource = new Resource(ResourceType.JSON, null, new Locale("en"));
	}
	
	@ParameterizedTest
	@CsvSource(delimiter = ';', value = {"key;value", "first;second"})
	void toJsonTest(String key, String value) {
		resource.storeTranslation(key, value);
		List<String> keys = new ArrayList<>();
		assertEquals(new JsonObject(), Resources.toJson(resource.getTranslations(), null, keys));
		assertEquals(JsonNull.INSTANCE, Resources.toJson(resource.getTranslations(), "", keys));
		assertEquals(new JsonPrimitive(resource.getTranslations().get(key)), Resources.toJson(resource.getTranslations(), key, keys));
		
		keys.add(key);
		resource.removeTranslation(key);
		JsonObject tmp = new JsonObject();
		tmp.add(key, null);
		assertEquals(tmp, Resources.toJson(resource.getTranslations(), "", keys));
		tmp = new JsonObject();
		resource.storeTranslation(key, value);
		tmp.addProperty(key, value);
		assertEquals(tmp, Resources.toJson(resource.getTranslations(), "", keys));
	}

}

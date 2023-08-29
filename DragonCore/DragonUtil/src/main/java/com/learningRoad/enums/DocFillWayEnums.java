package com.learningRoad.enums;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum DocFillWayEnums {
	MANUAL_FILLING("1","手动填报"),
	AUTO_FILLING("0","自动填报");

	private String code;

	private String value;

	private static final Map<String, DocFillWayEnums> mappings = new HashMap<>(16);

	static
	{
		for (DocFillWayEnums docInfoEnums : values())
		{
			mappings.put(docInfoEnums.code, docInfoEnums);
		}
	}

	@Nullable
	public static DocFillWayEnums of(@Nullable String code)
	{
		return (code != null ? mappings.get(code) : null);
	}

	DocFillWayEnums(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
}

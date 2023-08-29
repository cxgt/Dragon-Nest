package com.learningRoad.enums;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum DocFillPeriodEnums {
	DAYCYCLE("1","一天一次"),
	WEEKCYCLE("2","一周一次"),
	MONTHCYCLE("3","一月一次");

	private String code;

	private String value;

	private static final Map<String, DocFillPeriodEnums> mappings = new HashMap<>(16);

	static
	{
		for (DocFillPeriodEnums docInfoEnums : values())
		{
			mappings.put(docInfoEnums.code, docInfoEnums);
		}
	}

	@Nullable
	public static DocFillPeriodEnums of(@Nullable String code)
	{
		return (code != null ? mappings.get(code) : null);
	}

	DocFillPeriodEnums(String code, String value) {
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

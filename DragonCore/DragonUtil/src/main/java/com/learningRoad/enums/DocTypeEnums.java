package com.learningRoad.enums;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum DocTypeEnums {
	DAILY_INSPECTIONS("1","日常巡检"),
	FAILURE_CLASS("2","故障类"),
	SYSTEM_CHANGE_CLASS("3","系统变更类"),
	EMERGENCY_PLAN_CATEGORY("4","应急预案类"),
	DISASTER_PREVENTION_AND_RECTIFICATION_CLASS("5","防患整治类"),
	LOG_CLASS("6","日志类");

	private String code;

	private String value;

	private static final Map<String, DocTypeEnums> mappings = new HashMap<>(16);

	static
	{
		for (DocTypeEnums docInfoEnums : values())
		{
			mappings.put(docInfoEnums.code, docInfoEnums);
		}
	}

	@Nullable
	public static DocTypeEnums of(@Nullable String code)
	{
		return (code != null ? mappings.get(code) : null);
	}

	DocTypeEnums(String code, String value) {
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

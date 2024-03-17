package com.banma_school.http;


public enum Method {
	GET("GET"), //
	HEAD("HEAD"), //
	POST("POST"), //
	PUT("PUT"), //
	DELETE("DELETE"), //
	TRACE("TRACE"), //
	CONNECT("CONNECT"), //
	UNRECOGNIZED(null); //

	private String method;

	Method(String method) {
		this.method = method;
	}
}

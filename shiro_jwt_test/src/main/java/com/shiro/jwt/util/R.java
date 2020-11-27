package com.shiro.jwt.util;

import org.apache.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static com.shiro.jwt.util.R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, UniversalExpression.MenuType.UNKNOWNEXCEPTION.getValue());
	}
	
	public static com.shiro.jwt.util.R error(String msg) {
		return error(HttpStatus.SC_OK, msg);
	}
	
	public static com.shiro.jwt.util.R error(int code, String msg) {
		com.shiro.jwt.util.R r = new com.shiro.jwt.util.R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static com.shiro.jwt.util.R ok(String msg) {
		com.shiro.jwt.util.R r = new com.shiro.jwt.util.R();
		r.put("msg", msg);
		return r;
	}
	
	public static com.shiro.jwt.util.R ok(Map<String, Object> map) {
		com.shiro.jwt.util.R r = new com.shiro.jwt.util.R();
		r.putAll(map);
		return r;
	}
	
	public static com.shiro.jwt.util.R ok() {
		return new com.shiro.jwt.util.R();
	}

	public com.shiro.jwt.util.R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}

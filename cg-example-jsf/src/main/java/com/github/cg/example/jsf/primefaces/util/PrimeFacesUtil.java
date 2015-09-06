package com.github.cg.example.jsf.primefaces.util;

import java.text.MessageFormat;
import java.util.List;

import org.primefaces.context.RequestContext;

public class PrimeFacesUtil {

	public static final String SHOW = "show()";
	public static final String HIDE = "hide()";
	
	private static void execute(String widgetVar, String function) {
		executeJavascript(MessageFormat.format("PF(''{0}'').{1}", widgetVar, function));
	}
	
	public static void showDialog(String widgetVar) {
		execute(widgetVar, SHOW);
	}

	public static void hideDialog(String widgetVar) {
		execute(widgetVar, HIDE);
	}

	public static void showAndUpdateDialog(String widgetVar, String clientId) {
		update(clientId);
		execute(widgetVar, SHOW);
	}
	
	public static void update(String id) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update(id);
	}
	
	public static void update(List<String> ids) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update(ids);
	}
	
	public static void executeJavascript(String script) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute(script);
	}

	public static boolean isAjaxRequest() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		return requestContext != null && requestContext.isAjaxRequest();
	}
}

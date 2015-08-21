package com.github.cg.example.jsf.util;

import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class FacesMessageUtils {

	public static void addError(String id, String msg) {		
		addError(id, "Error!", msg);
	}

	public static void addError(String id, String msg, Object ... args) {
		addError(id, "Error!", MessageFormat.format(msg, args));
	}
	
	public static void addError(String msg) {
		addError(null, "Error!", msg);	
	}
	
	public static void addError(String msg, Object ... args) {
		addError(null, "Error!", MessageFormat.format(msg, args));	
	}

	public static void addSucess(String msg) {
		addInfo("Success!", msg);
	}
	
	public static void addSucess(String msg, Object ... args) {
		addInfo("Success!", MessageFormat.format(msg, args));
	}
	
	public static void addInfo(String msg) {
		addInfo("Information!", msg);
	}

	public static void addInfo(String msg, Object ... args) {
		addInfo("Information!", MessageFormat.format(msg, args));
	}
	
	public static void addAlert(String msg) {
		addAlert("Alert!", msg);
	}
	
	public static void addAlert(String msg, Object ... args) {
		addAlert("Alert!", MessageFormat.format(msg, args));
	}
	
	public static void addInfo(String title, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg));
	}
	
	public static void addError(String id, String title, String msg) {
		FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg));		
	}

	public static void addAlert(String title, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, title, msg));		
	}	
}

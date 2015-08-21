package com.github.cg.example.jsf.interceptors;


import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@HandlesError
@Interceptor
public class HandlesErrorInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object managerError(InvocationContext ctx) throws Exception {
		try {
			return ctx.proceed();
		}
		catch (Exception e) {

			if (FacesContext.getCurrentInstance() != null) {
				if (e instanceof NullPointerException || e.getCause() instanceof NullPointerException) {
					e.printStackTrace();
					Writer result = new StringWriter();
					PrintWriter printWriter = new PrintWriter(result);
					e.printStackTrace(printWriter);
					FacesMessageUtils.addError(result.toString());
				}
				else {
					FacesMessageUtils.addError(e.getMessage());
				}
			}
			else {
				/* TODO - Implementar, Tratar requisicoes que nao sao do JSF */
				throw e;
			}
			return null;
		}
	}
}
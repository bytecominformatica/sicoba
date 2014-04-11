package net.servehttp.bytecom.controller;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class Util {

	public String removePage(String path) {
		return path.substring(0, path.lastIndexOf('/'));
	}

	public String getParameters(String name) {
		Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		return requestParams.get(name);
	}
}

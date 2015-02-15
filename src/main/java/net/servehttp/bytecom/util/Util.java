package net.servehttp.bytecom.util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Util {

	public String removePage(String path) {
		return path.substring(0, path.lastIndexOf('/'));
	}
}

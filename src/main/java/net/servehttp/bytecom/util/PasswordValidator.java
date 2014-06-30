package net.servehttp.bytecom.util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;

public class PasswordValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent componente, Object value)
			throws ValidatorException {
		// TODO Auto-generated method stub
		String password = value.toString();
		
		UIInput uiConfirmPassword = (UIInput)componente.getAttributes().get("confirmPassword");
		String confirmPassword = uiConfirmPassword.getSubmittedValue().toString();
		
		if(!password.equals(confirmPassword)){
			uiConfirmPassword.setValid(false);
			throw new ValidatorException(new FacesMessage("A senha está diferente da confirmação"));
		}
	}

}

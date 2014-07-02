package net.servehttp.bytecom.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
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
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_WARN,"As senhas digitadas não conferem!!",null));
		}
	}

}

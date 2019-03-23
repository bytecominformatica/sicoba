import authApi from "../resources/auth-api";
// import {AuthContext} from "../contexts/auth-context";

class AuthService {
    login = (username, password, remember = true) => {

        return authApi.login(username, password, remember);

        // console.log(response);
        // let credential = {
        //     nome: 'Clairton Luz',
        //     iniciais: 'CL',
        //     email: 'clairton.luz@bytecominformatica.com.br'
        // };

        // AuthContext.setCurrentUser(credential);
        // return response;
    }
}

const authService = new AuthService();

export default authService;
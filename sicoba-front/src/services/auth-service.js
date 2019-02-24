
class AuthService {
    login = (username, password, lembrar = true) => {
        return AuthApi.login(username, password, lembrar);
    }
}

const authService = new AuthService();

export default authService;
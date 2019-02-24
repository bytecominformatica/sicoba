class AuthApi {

    login = (username, password, lembrar = true) => {
        let token = new Buffer(`${username}:${password}`).toString('base64');
        return fetch('http://localhost:8080/api/login',
            {
                method: 'POST',
                headers: {'Authorization': `Basic ${token}`}
            }
        ).then(response => response.json());
    }
}


const authApi = new AuthApi();
export default authApi;
import React, {Component} from 'react';
import './App.css';
import MyAppBar from './ui/components/template/MyAppBar';
import themeCustomization from './ui/components/template/themaCustomization'
import {MuiThemeProvider} from '@material-ui/core';
import LoginPage from './ui/pages/login/LoginPage';
import {AuthContext} from "./contexts/auth-context";
import authService from "./services/auth-service";
import Snapshot from "./contexts/Snapshot";

class App extends Component {
    constructor(props) {
        super(props);

        this.login = async (username, password, remember) => {
            let currentUser = new Snapshot();
            try {
                let userLogged = await authService.login(username, password, remember);
                currentUser = new Snapshot({data: userLogged});
            } catch (e) {
                console.error('exception:', e);
                currentUser = new Snapshot({error: e.message});
            } finally {
                this.setState(state => ({
                    currentUser: currentUser
                }));
            }
        };

        this.logout = () => {
            this.setState(state => ({
                currentUser: new Snapshot()
            }));
        };

        // State also contains the updater function so it will
        // be passed down into the context provider
        this.state = {
            currentUser: new Snapshot(),
            login: this.login,
            logout: this.logout,
        };
    }

    render() {
        let logged = this.state.currentUser.hasData();
        return (
            <AuthContext.Provider value={this.state}>
                {logged ? (
                    <MuiThemeProvider theme={themeCustomization}>
                        <AuthContext.Provider value={this.state}>
                            <nav>
                                <MyAppBar/>
                            </nav>
                            <main>
                                {this.props.children}
                            </main>
                        </AuthContext.Provider>
                    </MuiThemeProvider>
                ) : <LoginPage/>}
            </AuthContext.Provider>
        );
    }
}

export default App;

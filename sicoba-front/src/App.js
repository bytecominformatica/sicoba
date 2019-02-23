import React, {Component} from 'react';
import './App.css';
import MyAppBar from './ui/components/template/MyAppBar';
import themeCustomization from './ui/components/template/themaCustomization'
import {MuiThemeProvider} from '@material-ui/core';
import LoginPage from './ui/pages/LoginPage';
import {AuthContext} from "./contexts/auth-context";

class App extends Component {
    constructor(props) {
        super(props);

        this.login = (credential) => {
            this.setState(state => ({
                currentUser: credential
            }));
        };

        this.logout = () => {
            this.setState(state => ({
                currentUser: undefined
            }));
        };

        // State also contains the updater function so it will
        // be passed down into the context provider
        this.state = {
            currentUser: undefined,
            login: this.login,
            logout: this.logout,
        };
    }

    render() {
        let logged = !!this.state.currentUser;
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

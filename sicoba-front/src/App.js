import React, { Component } from 'react';
import './App.css';
import MyAppBar from './ui/components/template/MyAppBar';
import themeCustomization from './ui/components/template/themaCustomization'
import { MuiThemeProvider } from '@material-ui/core';
import LoginPage from './ui/pages/LoginPage';

class App extends Component {

  render() {
    let logged = true;
    const screen = logged ? (
      <MuiThemeProvider theme={themeCustomization}>
        <nav>
          <MyAppBar />
        </nav>
        <main>
          {this.props.children}
        </main>
      </MuiThemeProvider>
    ) : <LoginPage />
    return screen;
  }
}

export default App;

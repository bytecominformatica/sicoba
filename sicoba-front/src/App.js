import React, { Component } from 'react';
import './App.css';
import MyAppBar from './ui/components/template/MyAppBar';
import themeCustomization from './ui/components/template/themaCustomization'
import { MuiThemeProvider } from '@material-ui/core';

class App extends Component {

  render() {
    return (
      <MuiThemeProvider theme={themeCustomization}>
        <nav>
          <MyAppBar />
        </nav>
        <main>
          {this.props.children}
        </main>
      </MuiThemeProvider>
    );
  }
}

export default App;

import React, { Component } from 'react';
import './App.css';
import Button from '@material-ui/core/Button'
import MyAppBar from './ui/components/template/MyAppBar';
import themeCustomization from './ui/components/template/themaCustomization'
import { MuiThemeProvider, Fab } from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add'

class App extends Component {

  render() {
    return (
      <MuiThemeProvider theme={themeCustomization}>
        <header>
          <MyAppBar />
        </header>
        <nav>nav</nav>
        <main>
          <h1>Sicoba</h1>
          <Button variant='contained' color='primary'>
            Hello world
          </Button>
        </main>
        <footer>
          Footer
        <Fab aria-label="Add" color="secondary">
            <AddIcon />
          </Fab>
        </footer>
      </MuiThemeProvider>
    );
  }
}

export default App;

import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {
    BrowserRouter as Router,
} from "react-router-dom";
import HomePage from './ui/pages/HomePage';
import AboutPage from './ui/pages/AboutPage';
import NotFound from './ui/components/template/NotFound';
import Routes from './routes';

ReactDOM.render(

    <Router>
        <App>
            <Routes />
        </App>
    </Router>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();

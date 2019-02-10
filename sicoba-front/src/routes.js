import React from 'react'
import {
    Route,
    Switch,
} from "react-router-dom";
import HomePage from './ui/pages/HomePage';
import AboutPage from './ui/pages/AboutPage';
import NotFound from './ui/components/template/NotFound';

export default () => {
    return (
        <Switch>
            <Route path="/" exact component={HomePage} />
            <Route path="/clientes" component={AboutPage} />
            <Route component={NotFound} />
        </Switch>
    )
}

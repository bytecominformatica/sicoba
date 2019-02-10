import React from 'react'
import {
    Route,
    Switch,
} from "react-router-dom";
import HomePage from './ui/pages/HomePage';
import ClienteListPage from './ui/pages/cliente/ClienteListPage';
import NotFound from './ui/pages/NotFoundPage';

export default () => {
    return (
        <Switch>
            <Route path="/" exact component={HomePage} />
            <Route path="/clientes" exact component={ClienteListPage} />
            <Route component={NotFound} />
        </Switch>
    )
}

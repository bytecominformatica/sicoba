import React from "react";
import Snapshot from "./Snapshot";

export const AuthContext = React.createContext({
    currentUser: new Snapshot(),
    // currentUser:{username:'clairton', nome: 'Clairton Carneiro Luz', email: 'clairton.c.l@gmail.com', iniciais: 'CL'}


    login: async (username, password, remember = false) => {

    },

    logout: () => {
    },
});
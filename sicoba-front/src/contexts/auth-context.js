import React from "react";

export const AuthContext = React.createContext({
    currentUser: undefined,
    // currentUser:{username:'clairton', nome: 'Clairton Carneiro Luz', email: 'clairton.c.l@gmail.com', iniciais: 'CL'}

    login: () => {
    },

    logout: () => {
    },
});
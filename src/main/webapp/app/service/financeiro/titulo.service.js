/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Titulo', ['$resource', function ($resource) {
            return $resource('api/titulos/:id', {id: '@id'},
                {
                    'vencidos': {
                        method: 'GET',
                        url: 'api/titulos/vencidos',
                        isArray: true
                    },
                    'novo': {
                        method: 'GET',
                        url: 'api/titulos/cliente/:clienteId/nova',
                        params: {clienteId: '@clienteId'}
                    },
                    buscarPorCliente: {
                        method: 'GET',
                        url: 'api/titulos/cliente/:clienteId',
                        params: {clienteId: '@clienteId'},
                        isArray: true
                    },
                    criarCarne: {
                        method: 'POST',
                        url: 'api/titulos/carne',
                        isArray: true
                    }
                });
        }]);
}());

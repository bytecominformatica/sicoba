/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Contrato', ['$resource', function ($resource) {
            return $resource('api/contratos/:id', {id: '@id'},
                {
                    novos: {
                        method: 'GET',
                        url: 'api/contratos/novos',
                        isArray: true
                    },
                    buscarPorCliente: {
                        method: 'GET',
                        url: 'api/contratos/cliente/:clienteId',
                        params: {clienteId: '@clienteId'}
                    }
                });
        }]);
}());

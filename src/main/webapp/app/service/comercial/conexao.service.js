/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Conexao', ['$resource', function ($resource) {
            return $resource('api/conexoes/:id', {id: '@id'},
                {
                    buscarPorCliente: {
                        method: 'GET',
                        url: 'api/conexoes/cliente/:clienteId',
                        params: {id: '@id'}
                    },
                    buscarPorIp: {
                        method: 'GET',
                        url: 'api/conexoes/ip/:ip',
                        params: {ip: '@ip'}
                    },
                    buscarIpLivre: {
                        method: 'GET',
                        url: 'api/conexoes/ip/livre'
                    }
                });
        }]);
}());

/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Titulo', ['$resource', function ($resource) {
            return $resource('api/titulos/:id', {id: '@id'},
                {
                    vencidos: {
                        method: 'GET',
                        url: 'api/titulos/vencidos',
                        isArray: true
                    },
                    novo: {
                        method: 'GET',
                        url: 'api/titulos/cliente/:clienteId/new',
                        params: {clienteId: '@clienteId'}
                    },
                    buscarPorCliente: {
                        method: 'GET',
                        url: 'api/titulos/cliente/:clienteId',
                        params: {clienteId: '@clienteId'},
                        isArray: true
                    },
                    gerarBoletos: {
                        method: 'GET',
                        url: 'api/titulos/boletos',
                        headers: {
                            accept: 'application/pdf'
                        },
                        responseType: 'arraybuffer',
                        cache: false,
                        transformResponse: function (data) {
                            var pdf;
                            if (data) {
                                pdf = new Blob([data], {type: 'application/pdf'});
                            }
                            return {
                                file: pdf
                            };
                        }
                    },
                    criarCarne: {
                        method: 'POST',
                        url: 'api/titulos/carne',
                        isArray: true
                    },
                    buscarPorDataOcorrencia: {
                        method: 'GET',
                        url: 'api/titulos/ocorrencia',
                        isArray: true
                    },
                    buscarPorDataVencimento: {
                        method: 'GET',
                        url: 'api/titulos/vencimento',
                        isArray: true
                    }
                });
        }]);
}());

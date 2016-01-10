/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Cliente', ['$resource', function ($resource) {
            return $resource('api/clientes/:id', {id: '@id'},
                {
                    semTitulo: {
                        method: 'GET',
                        url: 'api/clientes/sem_titulo',
                        isArray: true
                    },
                    ultimosAlterados: {
                        method: 'GET',
                        url: 'api/clientes/ultimos_alterados',
                        isArray: true
                    }
                });
        }]);
}());

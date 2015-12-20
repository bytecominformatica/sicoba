/**
 * Created by clairton on 19/12/15.
 */
'use strict';

angular.module('sicobaApp')
    .factory('Cliente', ['$resource', function($resource) {
        return $resource('api/clientes/:id', {id: '@id'},
            {
                semMensalidade: {
                    method:'GET',
                    url: 'api/clientes/sem_mensalidade',
                    isArray: true
                },
                ultimosAlterados: {
                    method:'GET',
                    url: 'api/clientes/ultimos_alterados',
                    isArray: true
                }
            });
    }]);

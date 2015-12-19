/**
 * Created by clairton on 19/12/15.
 */
'use strict';

angular.module('sicobaApp')
    .factory('Cliente', ['$resource', function($resource) {
        return $resource('/clientes/:id', {id: '@id'},
            {
                'semMensalidade': {
                    method:'GET',
                    url: 'api/clientes/sem_mensalidade',
                    isArray: true
                }
            });
    }]);

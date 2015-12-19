/**
 * Created by clairton on 19/12/15.
 */
'use strict';

angular.module('sicobaApp')
    .factory('Contrato', ['$resource', function($resource) {
        return $resource('api/contratos/:id', {id: '@id'},
            {
                'novos': {
                    method:'GET',
                    url: 'api/contratos/novos',
                    isArray: true
                }
            });
    }]);

/**
 * Created by clairton on 19/12/15.
 */
'use strict';

angular.module('sicobaApp')
    .factory('Mensalidade', ['$resource', function($resource) {
        return $resource('/mensalidades/:id', {id: '@id'},
            {
                'atrasados': {
                    method:'GET',
                    url: 'api/mensalidades/atrasada',
                    isArray: true
                }
            });
    }]);

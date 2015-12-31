/**
 * Created by clairton on 19/12/15.
 */
'use strict';

angular.module('sicobaApp')
    .factory('Cidade', ['$resource', function($resource) {
        return $resource('api/cidades/:id', {id: '@id'},
            {});
    }]);

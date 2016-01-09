/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Mensalidade', ['$resource', function ($resource) {
            return $resource('api/mensalidades/:id', {id: '@id'},
                {
                    'atrasados': {
                        method: 'GET',
                        url: 'api/mensalidades/atrasada',
                        isArray: true
                    }
                });
        }]);
}());

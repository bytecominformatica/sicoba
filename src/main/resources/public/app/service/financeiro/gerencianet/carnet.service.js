/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Carnet', ['$resource', function ($resource) {
            return $resource('api/carnets/:id', {id: '@id'},
                {
                    gerar: {
                        method: 'POST',
                        url: 'api/carnet/gerar',
                        isArray: true
                    }
                });
        }]);
}());
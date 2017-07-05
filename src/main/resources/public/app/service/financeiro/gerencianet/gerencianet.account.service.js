/**
 * Created by clairton on 01/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('GerencianetAccount', ['$resource', function ($resource) {
            return $resource('api/gerencianet/accounts/:id', {id: '@id'},
                {});
        }]);
}());

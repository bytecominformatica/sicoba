/**
 * Created by clairton on 01/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Plano', ['$resource', function ($resource) {
            return $resource('api/planos/:id', {id: '@id'},
                {});
        }]);
}());

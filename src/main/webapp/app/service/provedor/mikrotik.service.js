/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Mikrotik', ['$resource', function ($resource) {
            return $resource('api/mikrotiks/:id', {id: '@id'},
                {});
        }]);
}());

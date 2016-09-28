/**
 * Created by clairton on 01/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Cedente', ['$resource', function ($resource) {
            return $resource('api/cedentes/:id', {id: '@id'}, {});
        }]);
}());

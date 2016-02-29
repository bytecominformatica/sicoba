/**
 * Created by clairton on 01/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('User', ['$resource', function ($resource) {
            return $resource('api/users/:id', {id: '@id'},
                {
                    logged: {
                        url: '/user'
                    },
                    findByUsername: {
                        url: '/api/users/username/:username',
                        params: {username: '@username'}
                    }
                });
        }]);
}());

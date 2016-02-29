/**
 * Created by clairton on 27/02/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Login', function ($http) {
            return {
                authenticate: function (credentials) {
                    var headers = credentials ? {
                        authorization: 'Basic ' + btoa(credentials.username + ":" + credentials.password)
                    } : {};

                    return $http.get('user', {headers: headers});
                }
            };
        });
}());

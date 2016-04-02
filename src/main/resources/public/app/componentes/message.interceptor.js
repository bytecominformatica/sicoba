/**
 * Created by clairton on 06/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('MessageInterceptor', function ($rootScope, $q) {
            return {
                request: function (config) {
                    $rootScope.messages = [];
                    return config;
                },
                responseError: function (rejection) {
                    if (rejection.status !== 401) {
                        $rootScope.messages.push({
                            title: 'Error:',
                            body: rejection.message,
                            type: 'alert-danger'
                        });
                    }

                    return $q.reject(rejection);
                }
            };
        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('MessageInterceptor');
        });
}());

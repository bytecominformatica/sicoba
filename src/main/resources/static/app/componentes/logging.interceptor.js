/**
 * Created by clairton on 06/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('MyLoggingInterceptor', function ($rootScope, $q) {
            return {
                request: function (config) {
                    $rootScope.messages = [];
                    return config;
                },
                responseError: function (rejection) {
                    console.log('Error in response ', rejection);
                    // if (rejection.status === 403) {
                    //                Show a login dialog
                    // }

                    if (rejection.data && rejection.data.parameterViolations) {
                        rejection.data.parameterViolations.forEach(function (violation) {
                            $rootScope.messages.push({
                                title: 'Error:',
                                body: violation.value + ': ' + violation.message,
                                type: 'alert-danger'
                            });
                        });
                    } else {
                        var message = rejection.message || rejection.data.message;
                        $rootScope.messages.push({title: 'Error:', body: rejection.data.message, type: 'alert-danger'});
                    }

                    return $q.reject(rejection);
                }
            };
        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('MyLoggingInterceptor');
        });
}());

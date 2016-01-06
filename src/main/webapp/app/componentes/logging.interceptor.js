/**
 * Created by clairton on 06/01/16.
 */
'use strict';

angular.module('sicobaApp')
    .factory('MyLoggingInterceptor', function ($rootScope, $q) {
        return {
            responseError: function (rejection) {
                console.log('Error in response ', rejection);
                // if (rejection.status === 403) {
                //                Show a login dialog
                // }

                if ($rootScope.messages === undefined) {
                    $rootScope.messages = [];
                }

                $rootScope.messages.push({title: 'Error:', body: rejection.data.error, type: 'alert-danger'});

                return $q.reject(rejection);
            }
        }
    })
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('MyLoggingInterceptor');
    });

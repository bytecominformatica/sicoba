/**
 * Created by clairton on 06/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('HttpInterceptor', function ($rootScope, $location) {

            return {
                request: function (config) {
                    return config;
                },
                responseError: function (rejection) {
                    if (rejection.status === 401) {
                        $location.path('/login');
                    }
                }
            };

            //function _isLoginPageContent(url) {
            //    return url.indexOf('login/index.html') > -1 ||
            //        url.indexOf('alert.html') > -1 ||
            //        url.indexOf('navbar.html') > -1 ||
            //        url.indexOf('menu.json') > -1 ||
            //        url.indexOf('menu.html') > -1 ||
            //        url.indexOf('/user') > -1;
            //}

        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('HttpInterceptor');
        });
}());

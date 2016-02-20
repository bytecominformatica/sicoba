/**
 * Created by clairton on 06/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('HttpInterceptor', function ($rootScope, $location, $cookies) {

            var isRequestServerSide = _isRequestServerSide;
            var isHtml = _isHtml;
            var isLoginPage = _isLoginPage;
            var isMenu = _isMenu;

            return {
                request: function (config) {
                    var auth_token = $cookies.get('Authorization');
                    if (isRequestServerSide(config.url)) {
                        config.url = 'http://localhost:8080/' + config.url;

                        if (auth_token) {
                            config.headers.authorization = auth_token;
                        }
                    }

                    if (auth_token) {
                        $rootScope.authenticated = true;
                    }

                    return config;
                },
                responseError: function (rejection) {
                    if (rejection.status === 401) {
                        $location.path('/login');
                    }
                }
            };


            function _isRequestServerSide(url) {
                return !(isHtml(url) || isMenu(url));
            }

            function _isHtml(url) {
                return url.indexOf('.html') > -1;
            }

            function _isLoginPage(url) {
                return url.indexOf('/login') > -1;
            }

            function _isMenu(url) {
                return url.indexOf('menu.json') > -1;
            }
        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('HttpInterceptor');
        });
}());

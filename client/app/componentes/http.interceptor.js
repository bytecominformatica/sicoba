/**
 * Created by clairton on 06/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('HttpInterceptor', function ($location) {
            return {
                request: function (config) {
                    if (isRequestServerSide(config.url)) {
                        config.url = 'http://localhost:8080/' + config.url;
                    }
                    return config;
                },
                responseError: function (rejection) {
                    if (rejection.status === 401) {
                        console.log("acesso nao autorizado para " + rejection.config.url);
                        $location.path('/login');
                    }
                }
            };

            function isRequestServerSide(url) {
                return !(isHtml(url) || isMenu(url));
            }

            function isHtml(url) {
                return url.indexOf('.html') > -1;
            }

            function isMenu(url) {
                return url.indexOf('menu.json') > -1;
            }
        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('HttpInterceptor');
        });
}());

/**
 * Created by clairton on 06/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('HttpInterceptor', function ($rootScope, $location, $cookies) {

            //var isRequestServerSide = _isRequestServerSide;
            //var isHtml = _isHtml;
            //var isLoginPage = _isLoginPage;
            //var isMenu = _isMenu;

            return {
                request: function (config) {
                    //if (isRequestServerSide(config.url)) {
                    //    config.url = 'http://localhost:5000/' + config.url;
                    //    config.headers['X-XSRF-TOKEN'] = $cookies.get('XSRF-TOKEN');
                    //}

                    return config;
                },
                responseError: function (rejection) {
                    if (rejection.status === 401) {
                        $location.path('/login');
                    }
                }
            };

            //function _isRequestServerSide(url) {
            //    return !(isHtml(url) || isMenu(url));
            //}
            //
            //function _isHtml(url) {
            //    return url.indexOf('.html') > -1;
            //}
            //
            //function _isLoginPage(url) {
            //    return url.indexOf('/login') > -1;
            //}
            //
            //function _isMenu(url) {
            //    return url.indexOf('menu.json') > -1;
            //}
        })
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('HttpInterceptor');
        });
}());

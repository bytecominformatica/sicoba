(function () {
    'use strict';

    angular.module('sicobaApp', [
            'ngRoute',
            'ngResource',
            'ngAnimate',
            'ngCookies',
            'ngFileUpload',
            'ui.bootstrap',
            'ngMask',
            'angular-confirm',
            'angular-loading-bar'
        ])
        .config(function ($routeProvider, $httpProvider) {
            $routeProvider.otherwise({redirectTo: '/'});
            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        });
}());

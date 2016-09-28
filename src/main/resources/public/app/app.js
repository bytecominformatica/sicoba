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
            'ngFileSaver',
            'angular-confirm'
        ])
        .config(function ($routeProvider, $httpProvider) {
            $routeProvider.otherwise({redirectTo: '/'});
            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        });
}());

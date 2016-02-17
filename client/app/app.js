(function () {
    'use strict';

    angular.module('sicobaApp', [
            'ngRoute',
            'ngResource',
            'ngAnimate',
            'ngFileUpload',
            'ui.bootstrap',
            'ngMask',
            'angular-confirm',
            'angular-loading-bar'
        ])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.otherwise({redirectTo: '/'});
        }]);
}());

'use strict';

angular.module('sicobaApp')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'app/views/login',
            controller: 'LoginCtrl'
        });
    }]);

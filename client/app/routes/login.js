(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/login', {
                templateUrl: 'app/views/login/index.html',
                controller: 'LoginCtrl'
            });
        }]);
}());

(function () {
    'use strict';

angular.module('sicobaApp')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'app/views/home/index.html',
            controller: 'HomeCtrl'
        });
    }]);
}());

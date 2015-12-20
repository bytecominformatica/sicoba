'use strict';

angular.module('sicobaApp', [
    'ngRoute',
    'ngResource',
    'ngAnimate',
    'angular-loading-bar'
]).
config(['$routeProvider', function ($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/'});
}]);
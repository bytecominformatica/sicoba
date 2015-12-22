'use strict';

angular.module('sicobaApp', [
    'ngRoute',
    'ngResource',
    'ngAnimate',
    'ui.bootstrap',
    'angular-loading-bar'
]).
config(['$routeProvider', function ($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/'});
}]);
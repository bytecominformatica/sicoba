'use strict';

angular.module('sicobaApp', [
    'ngRoute',
    'ngResource'
]).
config(['$routeProvider', function ($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/'});
}]);
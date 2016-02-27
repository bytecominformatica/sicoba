(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/logout', {
                template: '<div> Bye bye!</div>',
                controller: 'LogoutCtrl'
            });
        }]);
}());

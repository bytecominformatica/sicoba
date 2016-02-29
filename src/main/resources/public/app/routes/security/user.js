(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/profile', {
                templateUrl: 'app/views/security/user/profile.html',
                controller: 'ProfileCtrl'
            });
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/mikrotik', {
                    templateUrl: 'app/views/provedor/mikrotik/mikrotik.html',
                    controller: 'MikrotikCtrl'
                })
                .when('/mikrotik/:id', {
                    templateUrl: 'app/views/provedor/mikrotik/mikrotik.html',
                    controller: 'MikrotikCtrl'
                })
                .when('/mikrotiks', {
                    templateUrl: 'app/views/provedor/mikrotik/mikrotik.list.html',
                    controller: 'MikrotikListCtrl'
                });
        }]);
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/cliente', {
                    templateUrl: 'app/views/comercial/cliente/cliente.html',
                    controller: 'ClienteCtrl'
                })
                .when('/cliente/:id', {
                    templateUrl: 'app/views/comercial/cliente/cliente.html',
                    controller: 'ClienteCtrl'
                })
                .when('/clientes', {
                    templateUrl: 'app/views/comercial/cliente/cliente.list.html',
                    controller: 'ClienteListCtrl'
                });
        }]);
}());

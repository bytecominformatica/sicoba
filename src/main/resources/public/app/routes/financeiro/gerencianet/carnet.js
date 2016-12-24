(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/carnets', {
                    templateUrl: 'app/views/financeiro/gerencianet/carnet/carnet.list.html',
                    controller: 'CarnetListCtrl'
                })
                .when('/carnets/:id', {
                    templateUrl: 'app/views/financeiro/gerencianet/carnet/carnet.html',
                    controller: 'CarnetCtrl'
                })
                .when('/carnet/new', {
                    templateUrl: 'app/views/financeiro/gerencianet/carnet/carnet.html',
                    controller: 'CarnetCtrl'
                });
        }]);
}());

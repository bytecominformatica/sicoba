(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/gerencianet/account', {
                    templateUrl: 'app/views/financeiro/gerencianet/account/gerencianet.account.html',
                    controller: 'GerencianetAccountCtrl'
                })
                .when('/gerencianet/account/:id', {
                    templateUrl: 'app/views/financeiro/gerencianet/account/gerencianet.account.html',
                    controller: 'GerencianetAccountCtrl'
                })
                .when('/gerencianet/accounts', {
                    templateUrl: 'app/views/financeiro/gerencianet/account/gerencianet.account.list.html',
                    controller: 'GerencianetAccountListCtrl'
                });
        }]);
}());

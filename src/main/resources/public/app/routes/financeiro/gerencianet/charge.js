/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/charges', {
                    templateUrl: 'app/views/financeiro/gerencianet/charge/charge.list.html',
                    controller: 'ChargeListCtrl'
                })
                .when('/charges/:id', {
                    templateUrl: 'app/views/financeiro/gerencianet/charge/charge.html',
                    controller: 'ChargeCtrl'
                })
                .when('/charge/new', {
                    templateUrl: 'app/views/financeiro/gerencianet/charge/charge.html',
                    controller: 'ChargeCtrl'
                });
        }]);
}());

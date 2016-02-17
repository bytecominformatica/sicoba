/**
 * Created by clairton on 13/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/equipamentos', {
                    templateUrl: 'app/views/estoque/equipamento/equipamento.list.html',
                    controller: 'EquipamentoListCtrl'
                })
                .when('/equipamento', {
                    templateUrl: 'app/views/estoque/equipamento/equipamento.html',
                    controller: 'EquipamentoCtrl'
                })
                .when('/equipamento/:id', {
                    templateUrl: 'app/views/estoque/equipamento/equipamento.html',
                    controller: 'EquipamentoCtrl'
                });
        }]);
}());

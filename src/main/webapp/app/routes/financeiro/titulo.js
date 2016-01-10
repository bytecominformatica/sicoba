/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/titulos/cliente/:clienteId', {
                    templateUrl: 'app/views/financeiro/titulo/titulo.list.html',
                    controller: 'TituloListCtrl'
                })
                .when('/titulo/:id', {
                    templateUrl: 'app/views/financeiro/titulo/titulo.html',
                    controller: 'TituloCtrl'
                })
                .when('/titulo/cliente/:clienteId', {
                    templateUrl: 'app/views/financeiro/titulo/titulo.html',
                    controller: 'TituloCtrl'
                })
                .when('/titulo/cliente/:clienteId/carne', {
                    templateUrl: 'app/views/financeiro/titulo/carne.html',
                    controller: 'CarneCtrl'
                });
        }]);
}());

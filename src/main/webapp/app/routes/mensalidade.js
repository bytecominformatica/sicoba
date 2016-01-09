/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/mensalidade/cliente/:clienteId', {
                    templateUrl: 'app/views/financeiro/mensalidade/mensalidade.html',
                    controller: 'MensalidadeCtrl'
                })
                .when('/mensalidades/cliente/:clienteId', {
                    templateUrl: 'app/views/financeiro/mensalidade/mensalidade.list.html',
                    controller: 'MensalidadeListCtrl'
                })
                .when('/mensalidade/:id', {
                    templateUrl: 'app/views/financeiro/mensalidade/mensalidade.html',
                    controller: 'MensalidadeCtrl'
                });
        }]);
}());

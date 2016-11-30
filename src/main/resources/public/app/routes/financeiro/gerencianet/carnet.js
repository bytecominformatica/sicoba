/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/titulo/cliente/:clienteId/carnet', {
                    templateUrl: 'app/views/financeiro/gerencianet/carnet.html',
                    controller: 'CarnetCtrl'
                });
        }]);
}());

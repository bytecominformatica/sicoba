(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/contrato/cliente/:clienteId', {
                    templateUrl: 'app/views/comercial/contrato.html',
                    controller: 'ContratoCtrl'
                });
        }]);
}());

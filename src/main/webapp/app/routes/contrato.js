(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/contrato/cliente/:clienteId', {
                    templateUrl: 'app/views/contrato/contrato.html',
                    controller: 'ContratoCtrl'
                });
        }]);
}());

'use strict';

angular.module('sicobaApp')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/contrato/cliente/id', {
                templateUrl: 'app/views/contrato/contrato.html',
                controller: 'ContratoCtrl'
            });
    }]);

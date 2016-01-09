(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/conexao/cliente/:clienteId', {
                    templateUrl: 'app/views/comercial/conexao.html',
                    controller: 'ConexaoCtrl'
                });
        }]);
}());

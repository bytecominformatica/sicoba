(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/conexao/cliente/:clienteId', {
                    templateUrl: 'app/views/conexao/conexao.html',
                    controller: 'ConexaoCtrl'
                });
        }]);
}());

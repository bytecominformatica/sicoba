'use strict';

angular.module('sicobaApp')
    .controller('ClienteListCtrl', function ($scope, Cliente) {
        $scope.buscarPorNome = _buscarPorNome;

        _init();

        function _init() {
            $scope.clientes = Cliente.ultimosAlterados();
        }

        function _buscarPorNome(nome) {
            $scope.clientes = Cliente.query({nome: nome});
        }

    });
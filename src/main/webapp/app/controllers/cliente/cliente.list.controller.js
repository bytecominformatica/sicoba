'use strict';

angular.module('sicobaApp')
    .controller('ClienteListCtrl', function ($scope, Cliente, Conexao) {
        $scope.buscarPorNome = _buscarPorNome;

        _init();

        function _init() {
            Cliente.ultimosAlterados(function (data) {
                $scope.clientes = data;
                _buscarConexoes($scope.clientes);
            });
        }

        function _buscarPorNome(nome) {
            $scope.clientes = Cliente.query({nome: nome});
        }

        function _buscarConexoes(clientes) {
            clientes.forEach(function (cliente) {
                console.log(cliente.id);
                cliente.conexao = Conexao.buscarPorCliente({id: cliente.id});
            });
        }

    });
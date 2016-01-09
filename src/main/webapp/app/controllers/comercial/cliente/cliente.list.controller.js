(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ClienteListCtrl', function ($scope, Cliente, Conexao) {
            $scope.buscarPorNome = _buscarPorNome;
            $scope.buscarPorIp = _buscarPorIp;

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

            function _buscarPorIp(ip) {
                Conexao.buscarPorIp({ip: ip}, function (conexao) {
                    if (conexao) {
                        conexao.cliente.conexao = conexao;
                        $scope.clientes = [conexao.cliente];
                    } else {
                        $scope.clientes = [];
                    }
                });
            }

            function _buscarConexoes(clientes) {
                clientes.forEach(function (cliente) {
                    cliente.conexao = Conexao.buscarPorCliente({clienteId: cliente.id});
                });
            }

        });
}());

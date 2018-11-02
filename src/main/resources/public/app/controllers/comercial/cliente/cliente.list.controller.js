(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ClienteListCtrl', function ($scope, $rootScope, Cliente, Conexao) {
            $scope.buscarPorCliente = _buscarPorCliente;
            $scope.buscarPorIp = _buscarPorIp;
            $scope.blockLateCustomers = _blockLateCustomers;

            _init();

            function _init() {
                buscarUltimosAlterados();
            }

            function buscarUltimosAlterados() {
                Cliente.ultimosAlterados(function (data) {
                    $scope.clientes = data;
                    _buscarConexoes($scope.clientes);
                });
            }

            function _buscarPorCliente(cliente) {
                if (cliente.status === '') delete cliente.status;
                if (Object.keys(cliente).length > 0) {
                    $scope.clientes = Cliente.query(cliente);
                } else {
                    buscarUltimosAlterados();
                }
            }

            function _buscarPorIp(ip) {
                Conexao.get({ip: ip}, function (conexao) {
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

            function _blockLateCustomers() {
                Cliente.blockLateCustomers(function (data) {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Operação realizada com sucesso.',
                        type: 'alert-success'
                    }];

                });
            }

        });
}());

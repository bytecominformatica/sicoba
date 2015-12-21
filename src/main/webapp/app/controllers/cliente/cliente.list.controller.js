'use strict';

angular.module('sicobaApp')
    .controller('ClienteListCtrl', function ($scope, Cliente, Conexao) {
        $scope.buscarPorNome = _buscarPorNome;
        $scope.getStyle = _getStyle;

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

        function _getStyle(cliente) {
            var style = {};
            switch (cliente.status) {
                case 'ATIVO':
                    style.color = 'green';
                    break;
                case 'INATIVO':
                    style.color = 'orange';
                    break;
                default:
                    // CANCELADO
                    style.color = 'red';
            }
            return style;
        }

    });
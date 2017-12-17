(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ConexaoCtrl', function ($scope, $rootScope, $routeParams, Cliente, Conexao, Contrato, Mikrotik) {

            $scope.save = _save;
            $scope.remove = _remove;
            $scope.pegarMacDoEquipamento = _pegarMacDoEquipamento;

            _init();

            function _init() {
                $scope.clienteId = $routeParams.clienteId;
                _carregarConexao();
                $scope.contrato = Contrato.buscarPorCliente({clienteId: $routeParams.clienteId});
                $scope.mikrotiks = Mikrotik.query();
            }

            function _carregarConexao() {
                Conexao.buscarPorCliente({clienteId: $routeParams.clienteId}, function (conexao) {
                    if (conexao.id) {
                        $scope.conexao = conexao;
                    } else {
                        _criarNovaConexao();
                    }
                });
            }

            function _pegarMacDoEquipamento() {
                console.log('tests', $scope.contrato);
                if ($scope.contrato.equipamento) {
                    $scope.conexao.mac = $scope.contrato.equipamento.mac;
                    $rootScope.messages = [{
                        title: 'Mac capturado do equipamento de instalação',
                        type: 'alert-success'
                    }];
                } else if ($scope.contrato.equipamentoWifi) {
                    $scope.conexao.mac = $scope.contrato.equipamentoWifi.mac;
                    $rootScope.messages = [{title: 'Mac capturado do equipamento wifi', type: 'alert-success'}];
                } else {
                    $rootScope.messages = [{title: 'Nenhum equipamento encontrado', type: 'alert-success'}];
                }
            }

            function _criarNovaConexao() {
                Cliente.get({id: $routeParams.clienteId}, function (cliente) {
                    $scope.conexao = {
                        cliente: cliente
                    };

                    var indexOfSpace = cliente.nome.indexOf(' ');
                    if (indexOfSpace > 0) {
                        $scope.conexao.nome = $scope.conexao.senha = cliente.nome.substring(0, indexOfSpace) + cliente.id;
                    } else {
                        $scope.conexao.nome = $scope.conexao.senha = cliente.nome + cliente.id;
                    }

                    Conexao.buscarIpLivre({}, function (data) {
                        $scope.conexao.ip = data.ip;
                    });

                });
            }

            function _save(conexao) {
                Conexao.save(conexao, function (data) {
                    $scope.conexao = data;
                    _sucesso();
                });
            }

            function _remove(conexao) {
                Conexao.remove({id: conexao.id}, function (data) {
                    _init();
                    _sucesso();
                });
            }

            function _sucesso() {
                $rootScope.messages = [{title: 'Sucesso', type: 'alert-success'}];
            }
        });
}());

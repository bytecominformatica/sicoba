(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ContratoCtrl', function ($scope, $rootScope, $routeParams, Cliente, Contrato, Plano, Equipamento) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                $scope.clienteId = $routeParams.clienteId;
                _carregarContrato();
                $scope.hoje = new Date();
                $scope.planos = Plano.query();
            }

            function _carregarContrato() {
                Contrato.buscarPorCliente({clienteId: $routeParams.clienteId}, function (contrato) {
                    if (contrato.dataInstalacao) contrato.dataInstalacao = moment(contrato.dataInstalacao).toDate();

                    Equipamento.disponiveisParaInstalacao(function (equipamentosList) {
                        $scope.equipamentosInstalacao = equipamentosList;
                        if (contrato.id && contrato.equipamento) $scope.equipamentosInstalacao.push(contrato.equipamento);
                    });
                    Equipamento.disponiveisParaWifi({}, function (equipamentoList) {
                        $scope.equipamentosWifi = equipamentoList;
                        if (contrato.id && contrato.equipamentoWifi) $scope.equipamentosWifi.push(contrato.equipamentoWifi);
                    });

                    if (contrato.id) {
                        $scope.contrato = contrato;
                    } else {
                        $scope.contrato = {cliente: Cliente.get({id: $routeParams.clienteId})};
                    }
                });
            }

            function _save(contrato) {
                Contrato.save(contrato, function (data) {
                    $scope.contrato = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Contrato de número ' + data.id + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(contrato) {
                Contrato.remove({id: contrato.id}, function (data) {
                    _init();
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Contrato de número ' + contrato.id + ' foi removido.',
                        type: 'alert-success'
                    }];
                });
            }
        });
}());

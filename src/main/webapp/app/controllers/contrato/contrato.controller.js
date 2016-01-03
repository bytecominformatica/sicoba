'use strict';

angular.module('sicobaApp')
    .controller('ContratoCtrl', function ($scope, $routeParams, Cliente, Contrato, Plano, Equipamento) {

        $scope.save = _save;

        _init();
        _carregarContrato();

        function _init() {
            $scope.hoje = new Date();
            $scope.planos = Plano.query();
            $scope.equipamentosInstalacao = Equipamento.disponiveisParaInstalacao();
            $scope.equipamentosWifi = Equipamento.disponiveisParaWifi();
        }

        function _carregarContrato() {
            if ($routeParams.clienteId) {
                Contrato.buscarPorCliente({clienteId: $routeParams.clienteId}, function (contrato) {
                    if (contrato) {
                        $scope.contrato = contrato
                    } else {
                        $scope.contrato.cliente = Cliente.get({id: $routeParams.clienteId});
                    }
                }, _handleErrorApi);
            }
        }

        function _save(contrato) {
            Contrato.save(contrato, function (data) {
                $scope.contrato = data;
                $scope.message = {title: 'Sucesso', type: 'alert-success'};
            }, _handleErrorApi);
        }

        function _handleErrorApi(error) {
            console.log(error);
            $scope.message = {title: 'Error:', body: error.data.error, type: 'alert-danger'};
        }
    });
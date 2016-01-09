(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('HomeCtrl', function ($scope, Mensalidade, Cliente, Contrato) {

            $scope.order = _order;

            _buscarMensalidadesAtrasada();
            _buscarClientesSemMensalidade();
            _buscarClientesInativo();
            _buscarContratosNovos();


            function _buscarMensalidadesAtrasada() {
                $scope.mensalidadesAtrasada = Mensalidade.atrasados();
            }

            function _buscarClientesSemMensalidade() {
                $scope.clientesSemMensalidade = Cliente.semMensalidade();
            }

            function _buscarClientesInativo() {
                $scope.clientesInativo = Cliente.query({status: 'INATIVO'});
            }

            function _buscarContratosNovos() {
                $scope.contratosNovos = Contrato.novos();
            }

            function _order(predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            }
        });
}());

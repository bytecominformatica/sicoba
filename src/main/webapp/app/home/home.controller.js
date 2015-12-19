'use strict';

angular.module('sicobaApp')
    .controller('HomeCtrl', function ($scope, Mensalidade, Cliente) {

        $scope.order = _order;

        _buscarMensalidadesAtrasada();
        _buscarClientesSemMensalidade();

        function _buscarMensalidadesAtrasada() {
            $scope.mensalidadesAtrasada = Mensalidade.atrasados();
        }

        function _buscarClientesSemMensalidade() {
            $scope.clientesSemMensalidade = Cliente.semMensalidade();
        }

        function _order(predicate) {
            $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
            $scope.predicate = predicate;
        }
    });
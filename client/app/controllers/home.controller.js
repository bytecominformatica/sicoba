(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('HomeCtrl', function ($scope, Titulo, Cliente, Contrato) {

            $scope.order = _order;

            _buscarTitulosVencido();
            _buscarClientesSemTitulo();
            _buscarClientesInativo();
            _buscarContratosNovos();


            function _buscarTitulosVencido() {
                $scope.titulosVencidos = Titulo.vencidos();
            }

            function _buscarClientesSemTitulo() {
                $scope.clientesSemTitulo = Cliente.semTitulo();
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

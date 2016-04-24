(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('HomeCtrl', function ($scope, Titulo, Cliente, Contrato) {

            $scope.order = _order;

            _init();

            function _init() {
                $scope.titulosVencidos = Titulo.vencidos();
                $scope.clientesSemTitulo = Cliente.semTitulo();
                $scope.clientesCancelados = Cliente.ultimosCancelados();
                $scope.clientesInativo = Cliente.query({status: 'INATIVO'});
                $scope.contratosNovos = Contrato.novos();
            }

            function _order(predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            }
        });
}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('HomeCtrl', ['$scope', 'Titulo', 'Cliente', 'Contrato', 'Charge',
            function ($scope, Titulo, Cliente, Contrato, Charge) {
                $scope.order = _order;

                _init();

                function _init() {
                    $scope.titulosVencidos = Titulo.vencidos();
                    $scope.chargesOverdue = Charge.overdue();
                    $scope.clientesSemTitulo = Cliente.semTitulo();
                    $scope.clientesCancelados = Cliente.ultimosCancelados();
                    $scope.clientesInativo = Cliente.query({status: 'INATIVO'});
                    $scope.contratosNovos = Contrato.novos();
                }

                function _order(predicate) {
                    $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                    $scope.predicate = predicate;
                }
            }]);
}());

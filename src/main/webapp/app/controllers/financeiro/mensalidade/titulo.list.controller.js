(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('TituloListCtrl', function ($scope, $routeParams, Titulo, Cliente) {

            $scope.getStatusClass = _getStatusClass;

            _init();

            function _init() {
                $scope.cliente = Cliente.get({id: $routeParams.clienteId});
                $scope.titulos = Titulo.buscarPorCliente({clienteId: $routeParams.clienteId});
            }

            function _getStatusClass(status) {
                return status === 'PENDENTE' ? 'label-warning' : 'label-success';
            }

        });
}());

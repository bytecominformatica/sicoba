(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('MensalidadeListCtrl', function ($scope, $routeParams, Mensalidade, Cliente) {

            $scope.getStatusClass = _getStatusClass;

            _init();

            function _init() {
                $scope.cliente = Cliente.get({id: $routeParams.clienteId});
                $scope.mensalidades = Mensalidade.buscarPorCliente({clienteId: $routeParams.clienteId});
            }

            function _getStatusClass(status) {
                return status === 'PENDENTE' ? 'label-warning' : 'label-success';
            }

        });
}());

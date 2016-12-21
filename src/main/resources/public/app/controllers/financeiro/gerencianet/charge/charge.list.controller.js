(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ChargeListCtrl', ['$scope', '$location', 'Charge', 'Cliente',
            function ($scope, $location, Charge, Cliente) {

                $scope.getStatusClass = _getStatusClass;

                _init();

                function _init() {
                    var clienteId = $location.search().clienteId;
                    $scope.cliente = Cliente.get({id: clienteId});
                    $scope.charges = Charge.buscarPorCliente({clienteId: clienteId});
                }

                function _getStatusClass(status) {
                    return status === 'PAID' ? 'label-success' : 'label-warning';
                }

            }]);
}());

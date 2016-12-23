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
                    $scope.charges = Charge.query({clienteId: clienteId});
                }

                function _getStatusClass(status) {
                    var result;
                    switch (status) {
                        case 'PAID':
                            result = 'label-success';
                            break;
                        case 'CANCELED':
                            result = 'label-danger';
                            break;
                        case 'REFUNDED':
                            result = 'label-danger';
                            break;
                        case 'LINK':
                            result = 'label-info';
                            break;
                        case 'WAITING':
                            result = 'label-info';
                            break;
                        default:
                            result = 'label-warning';
                    }

                    return result;
                }

            }]);
}());

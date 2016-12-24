(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CarnetListCtrl', ['$scope', '$location', 'Carnet', 'Cliente',
            function ($scope, $location, Carnet, Cliente) {

                $scope.getStatusClass = _getStatusClass;

                _init();

                function _init() {
                    var clienteId = $location.search().clienteId;
                    $scope.cliente = Cliente.get({id: clienteId});
                    $scope.carnets = Carnet.query({clienteId: clienteId});
                }

                function _getStatusClass(status) {
                    var result;
                    switch (status) {
                        case 'ACTIVE':
                            result = 'label-success';
                            break;
                        case 'CANCELED':
                            result = 'label-danger';
                            break;
                        default:
                            result = 'label-warning';
                    }

                    return result;
                }

            }]);
}());

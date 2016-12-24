(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ChargeListCtrl', ['$scope', '$rootScope', '$location', 'Charge', 'Cliente',
            function ($scope, $rootScope, $location, Charge, Cliente) {

                $scope.getStatusClass = _getStatusClass;
                $scope.refreshUrlsNotification = _refreshUrlsNotification;

                _init();

                function _init() {
                    var clienteId = $location.search().clienteId;
                    var carnetId = $location.search().carnetId;
                    $scope.cliente = Cliente.get({id: clienteId});
                    $scope.charges = Charge.query({clienteId: clienteId, carnetId: carnetId});
                }

                function _refreshUrlsNotification() {
                    Charge.refreshUrlsNotification({}, function () {
                        $rootScope.messages = [{
                            title: 'Sucesso:',
                            body: 'As URLs de notificação de todos as cobrança foram atualizadas',
                            type: 'alert-success'
                        }];
                    });
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

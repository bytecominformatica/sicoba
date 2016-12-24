(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CarnetListCtrl', ['$scope', '$rootScope', '$location', 'Carnet', 'Cliente',
            function ($scope, $rootScope, $location, Carnet, Cliente) {

                $scope.getStatusClass = _getStatusClass;
                $scope.refreshUrlsNotification = _refreshUrlsNotification;

                _init();

                function _init() {
                    var clienteId = $location.search().clienteId;
                    $scope.cliente = Cliente.get({id: clienteId});
                    $scope.carnets = Carnet.query({clienteId: clienteId});
                }

                function _refreshUrlsNotification() {
                    Carnet.refreshUrlsNotification({}, function () {
                        $rootScope.messages = [{
                            title: 'Sucesso:',
                            body: 'As URLs de notificação de todos os carnês foram atualizadas',
                            type: 'alert-success'
                        }];
                    });
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

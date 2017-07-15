(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CarnetListCtrl', ['$scope', '$rootScope', '$location', 'Carnet', 'Cliente',
            function ($scope, $rootScope, $location, Carnet, Cliente) {
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

            }]);
}());

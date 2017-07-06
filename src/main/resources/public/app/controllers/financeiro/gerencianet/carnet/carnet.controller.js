(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CarnetCtrl', ['$scope', '$rootScope', '$routeParams', '$location', 'Carnet', 'GerencianetAccount',
            function ($scope, $rootScope, $routeParams, $location, Carnet, GerencianetAccount) {

                $scope.create = _create;
                $scope.cancel = _cancel;

                _init();

                function _init() {
                    $scope.accounts = GerencianetAccount.query();
                    if ($routeParams.id) {
                        _findById($routeParams.id);
                    } else if ($location.search().clienteId) {
                        $scope.clienteId = $location.search().clienteId;
                        _newCarnet($scope.clienteId);
                    }
                }

                function _newCarnet(clienteId) {
                    $scope.carnet = Carnet.new({clienteId: clienteId});
                }

                function _findById(id) {
                    Carnet.get({id: id}, function (carnet) {
                        $scope.carnet = carnet;
                        $scope.clienteId = carnet.cliente.id;
                    });
                }

                function _create(carnet) {
                    Carnet.save(carnet, function (data) {
                        $scope.carnet = data;
                        $rootScope.messages = [{
                            title: 'Sucesso:',
                            body: 'Criado carnê de número ' + data.id,
                            type: 'alert-success'
                        }];
                        $location.path('/carnets/' + data.id);
                    });
                }

                function _cancel(carnet) {
                    Carnet.cancel({id: carnet.id}, function (data) {
                        _init();
                        $rootScope.messages = [{
                            title: 'Sucesso',
                            body: 'Cancelado carnê de número ' + data.id,
                            type: 'alert-success'
                        }];
                    });
                }
            }]);
}());

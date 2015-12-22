'use strict';

angular.module('sicobaApp')
    .controller('ClienteCtrl', function ($scope, $routeParams, Cliente) {

        $scope.save = _save;

        _init();

        function _init() {
            $scope.cliente = {status: 'ATIVO'};
            $scope.hoje = new Date();

            if ($routeParams.id) {
                $scope.cliente = Cliente.get({id: $routeParams.id});
            }
        }

        function _save(cliente) {
            Cliente.save(cliente);
        }
    });
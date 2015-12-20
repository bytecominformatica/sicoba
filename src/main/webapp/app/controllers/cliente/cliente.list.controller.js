'use strict';

angular.module('sicobaApp')
    .controller('ClienteListCtrl', function ($scope, Cliente) {
        _init();

        function _init(){
            $scope.clientes = Cliente.ultimosAlterados();
        }

    });
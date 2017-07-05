(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('GerencianetAccountListCtrl', function ($scope, GerencianetAccount) {

            $scope.gerencianetAccounts = GerencianetAccount.query();

        });
}());

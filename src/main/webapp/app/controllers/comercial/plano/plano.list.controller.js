(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('PlanoListCtrl', function ($scope, Plano) {

            $scope.planos = Plano.query();

        });
}());

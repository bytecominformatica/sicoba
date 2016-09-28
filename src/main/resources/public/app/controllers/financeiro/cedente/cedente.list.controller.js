(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CedenteListCtrl', ['$scope', 'Cedente', function ($scope, Cedente) {

            $scope.cedentes = Cedente.query();

        }]);
}());

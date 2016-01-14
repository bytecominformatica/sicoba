(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('EquipamentoListCtrl', function ($scope, Equipamento) {
            $scope.equipamentos = Equipamento.query();
        });
}());

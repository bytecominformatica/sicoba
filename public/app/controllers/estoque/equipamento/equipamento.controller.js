(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('EquipamentoCtrl', function ($scope, $rootScope, $routeParams, Equipamento) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.equipamento = Equipamento.get({id: $routeParams.id});
                } else {
                    $scope.equipamento = {tipo: "INSTALACAO", status: "OK"};
                }

                $scope.tipos = [
                    {value: "INSTALACAO", descricao: 'Instalação'},
                    {value: "WIFI", descricao: 'Wifi'}
                ];

                $scope.statusList = [
                    {value: "OK", descricao: 'OK'},
                    {value: "DEFEITO", descricao: 'Defeito'}
                ];
            }

            function _save(equipamento) {
                Equipamento.save(equipamento, function (data) {
                    $scope.equipamento = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Equipamento ' + data.marca + ' ' + data.modelo + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(equipamento) {
                Equipamento.remove({id: equipamento.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Equipamento ' + equipamento.marca + ' ' + equipamento.modelo + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.equipamento = {tipo: "INSTALACAO", status: "OK"};
                });
            }
        });
}());

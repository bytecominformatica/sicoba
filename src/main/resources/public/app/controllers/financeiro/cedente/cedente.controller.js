(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('CedenteCtrl', function ($scope, $rootScope, $routeParams, Cedente) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.cedente = Cedente.get({id: $routeParams.id});
                }
            }

            function _save(cedente) {
                Cedente.save(cedente, function (data) {
                    $scope.cedente = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Cedente ' + data.nome + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(cedente) {
                Cedente.remove({id: cedente.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Cedente ' + cedente.nome + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.cedente = {};
                });
            }
        });
}());

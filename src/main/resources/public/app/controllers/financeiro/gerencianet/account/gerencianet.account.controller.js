(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('GerencianetAccountCtrl', function ($scope, $rootScope, $routeParams, GerencianetAccount) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.gerencianetAccount = GerencianetAccount.get({id: $routeParams.id});
                }
            }

            function _save(gerencianetAccount) {
                GerencianetAccount.save(gerencianetAccount, function (data) {
                    $scope.gerencianetAccount = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'GerencianetAccount ' + data.name + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(gerencianetAccount) {
                GerencianetAccount.remove({id: gerencianetAccount.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'GerencianetAccount ' + gerencianetAccount.name + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.gerencianetAccount = {};
                });
            }
        });
}());

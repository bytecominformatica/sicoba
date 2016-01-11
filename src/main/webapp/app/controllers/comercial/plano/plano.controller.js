(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('PlanoCtrl', function ($scope, $rootScope, $routeParams, $location, Plano) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.plano = Plano.get({id: $routeParams.id});
                }
            }

            function _save(plano) {
                Plano.save(plano, function (data) {
                    $scope.plano = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Plano ' + data.nome + ' foi salvo.',
                        type: 'alert-success'
                    }];
                    _voltar();
                });
            }

            function _remove(plano) {
                Plano.remove({id: plano.id}, function () {
                    _voltar();
                });
            }

            function _voltar() {
                $location.path('/planos');
            }

        });
}());

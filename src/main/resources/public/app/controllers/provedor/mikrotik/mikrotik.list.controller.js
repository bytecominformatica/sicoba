(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('MikrotikListCtrl', function ($scope, $rootScope, Mikrotik, Conexao) {

            $scope.mikrotiks = Mikrotik.query();
            $scope.atualizarTodasConexoes = _atualizarTodasConexoes;

            function _atualizarTodasConexoes() {
                Conexao.atualizarTodos(function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Todas as conexões estão sendo atualizadas',
                        type: 'alert-success'
                    }];
                });
            }

        });
}());

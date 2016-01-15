(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('MikrotikCtrl', function ($scope, $rootScope, $routeParams, Mikrotik) {

            $scope.save = _save;
            $scope.remove = _remove;

            _init();

            function _init() {
                if ($routeParams.id) {
                    $scope.mikrotik = Mikrotik.get({id: $routeParams.id});
                } else {
                    $scope.mikrotik = {port: 8728};
                }
            }

            function _save(mikrotik) {
                Mikrotik.save(mikrotik, function (data) {
                    $scope.mikrotik = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Mikrotik ' + data.name + ' foi salvo.',
                        type: 'alert-success'
                    }];
                });
            }

            function _remove(mikrotik) {
                Mikrotik.remove({id: mikrotik.id}, function () {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Mikrotik ' + mikrotik.name + ' foi removido.',
                        type: 'alert-success'
                    }];
                    $scope.mikrotik = {port: 8728};
                });
            }
        });
}());

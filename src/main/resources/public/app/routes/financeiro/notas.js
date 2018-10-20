(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/notas/gerar', {
                    templateUrl: 'app/views/financeiro/notas/gerarnotas.html',
                    controller: 'GerarNotasCtrl'
                });
        }]);
}());

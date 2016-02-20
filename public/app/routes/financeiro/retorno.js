/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/retorno', {
                    templateUrl: 'app/views/financeiro/retorno.html',
                    controller: 'RetornoCtrl'
                });
        }]);
}());

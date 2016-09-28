/**
 * Created by clairton on 09/01/16.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/cedentes', {
                    templateUrl: 'app/views/financeiro/cedente/cedente.list.html',
                    controller: 'CedenteListCtrl'
                })
                .when('/cedente', {
                    templateUrl: 'app/views/financeiro/cedente/cedente.html',
                    controller: 'CedenteCtrl'
                })
                .when('/cedente/:id', {
                    templateUrl: 'app/views/financeiro/cedente/cedente.html',
                    controller: 'CedenteCtrl'
                });
        }]);
}());

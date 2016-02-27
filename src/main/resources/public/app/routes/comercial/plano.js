(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/plano', {
                    templateUrl: 'app/views/comercial/plano/plano.html',
                    controller: 'PlanoCtrl'
                })
                .when('/plano/:id', {
                    templateUrl: 'app/views/comercial/plano/plano.html',
                    controller: 'PlanoCtrl'
                })
                .when('/planos', {
                    templateUrl: 'app/views/comercial/plano/plano.list.html',
                    controller: 'PlanoListCtrl'
                });
        }]);
}());

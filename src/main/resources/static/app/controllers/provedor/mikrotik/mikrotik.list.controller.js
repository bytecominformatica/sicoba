(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('MikrotikListCtrl', function ($scope, Mikrotik) {

            $scope.mikrotiks = Mikrotik.query();

        });
}());

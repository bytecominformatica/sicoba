(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LogoutCtrl', function ($rootScope, $http, $location) {

            _logout();
            function _logout() {
                $http.post('logout', {}).finally(function () {
                    $rootScope.authenticated = false;
                    $location.path("/");
                });
            }

        });
}());

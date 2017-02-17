(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LogoutCtrl', function ($rootScope, $http, $location, $cookies) {
            _logout();

            function _logout() {
                $http.post('logout', {}).finally(function (data) {
                    //$location.baseUrl('/login');
                });
            }

        });
}());

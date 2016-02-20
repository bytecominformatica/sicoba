(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LogoutCtrl', function ($rootScope, $http, $location, $cookies) {
            console.log('teste lgout');

            _logout();

            function _logout() {
                console.log('teste lgout');
                $http.post('logout', {}).finally(function (data) {
                    console.log(data);
                    $cookies.remove("Authorization");
                    $rootScope.authenticated = false;
                    $location.path("/login");
                });
            }

        });
}());

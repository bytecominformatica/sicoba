(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LogoutCtrl', function ($rootScope, $http, $location, $cookies) {
            console.log('teste lgout');

            _logout();

            function _logout() {
                $http.post('logout', {}).finally(function (data) {
                    console.log(data);
                    //$location.baseUrl('/login');
                });
            }

        });
}());

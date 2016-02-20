(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LoginCtrl', function ($rootScope, $scope, $http, $location) {

            $scope.login = _login;

            function _init() {
                $scope.credentials = {};
            }

            function _authenticate(credentials, callback) {
                var auth_token = btoa(credentials.username + ":" + credentials.password);
                var headers = credentials ? {
                    authorization: "Basic " + auth_token
                } : {};

                $http.get('user', {headers: headers}).success(function (data) {
                    console.log(data);
                    if (data && data.name) {
                        $rootScope.authenticated = true;
                    } else {
                        $rootScope.authenticated = false;
                    }
                    return callback && callback();
                }).error(function () {
                    $rootScope.authenticated = false;
                    return callback && callback();
                });

            }

            function _login() {
                console.log('login');
                _authenticate($scope.credentials, function () {
                    if ($rootScope.authenticated) {
                        console.log('sucesso');
                        $location.path("/");
                        $scope.error = false;
                    } else {
                        console.log('fail');
                        $location.path("/login");
                        $scope.error = true;
                    }
                });
            }

        });
}());

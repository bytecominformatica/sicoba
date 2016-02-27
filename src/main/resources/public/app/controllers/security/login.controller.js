(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LoginCtrl', function ($rootScope, $scope, $http, $location, $cookies) {

            $scope.login = _login;
            _init();

            function _init() {
                $rootScope.pageId = 'page-not-logged';
                $scope.credentials = {};
                _authenticate();
            }

            function _authenticate(credentials, callback) {

                var headers = credentials ? {
                    authorization: 'Basic ' + btoa(credentials.username + ":" + credentials.password)
                } : {};

                $http.get('user', {headers: headers}).success(function (data) {
                    if (data.name) {
                        $rootScope.authenticated = true;
                    } else {
                        $rootScope.authenticated = false;
                    }
                    callback();
                }).error(function () {
                    $rootScope.authenticated = false;
                    callback();
                });
            }

            function _login() {
                console.log('login');
                _authenticate($scope.credentials, function () {
                    if ($rootScope.authenticated) {
                        console.log('sucesso');
                        $rootScope.pageId = 'page-wrapper';
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

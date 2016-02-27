(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LoginCtrl', function ($rootScope, $scope, $location, Login) {

            $scope.login = _login;
            _init();

            function _init() {
                $rootScope.pageId = 'page-wrapper2';
                $scope.credentials = {};
                _authenticate();
            }

            function _authenticate(credentials, callback) {
                Login.authenticate(credentials).then(function (response) {
                    if (callback) {
                        callback(response && response.data.name);
                    }
                });
            }

            function _login() {
                _authenticate($scope.credentials, function (authenticated) {
                    if (authenticated) {
                        $rootScope.pageId = 'page-wrapper';
                        $location.path("/");
                        $scope.error = false;
                    } else {
                        $rootScope.pageId = 'page-wrapper2';
                        $location.path("/login");
                        $scope.error = true;
                    }
                });
            }

        });
}());

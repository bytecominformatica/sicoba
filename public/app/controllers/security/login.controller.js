(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('LoginCtrl', function ($rootScope, $scope, $http, $location, $cookies) {

            $scope.login = _login;
            _init();

            function _init() {
                $scope.credentials = {};
                $http.get('login').success(function (data) {

                });
            }


            function _authenticate(credentials, callback) {
                var auth_token = btoa(credentials.username + ":" + credentials.password);

                var result = 'username=admin&password=admin&submit=Login&_csrf=' + $cookies.get('XSRF-TOKEN');

                var headers = credentials ? {
                    authorization: 'Basic ' + auth_token
                } : {};
                headers['Content-Type'] = 'application/x-www-form-urlencoded';

                $http.post('login', result, {headers: headers}).success(function (data) {
                    if (data && data.name) {
                        $cookies.put('Authorization', headers.authorization);
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

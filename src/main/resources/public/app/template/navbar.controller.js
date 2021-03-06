(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('NavbarCtrl', function ($scope, $http, $location) {

            $scope.getPath = _getPath;

            _loadMenu();

            function _loadMenu() {
                $http({method: 'GET', url: 'app/template/menu.json'}).success(function (data) {
                    $scope.menus = data;
                });
            }

            function _getPath() {
                return '#' + $location.path();
            }
        });
}());

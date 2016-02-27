(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('NavbarCtrl', function ($scope, $http, $location) {

            $scope.getPath = _getPath;

            _loadMenu();

            function _loadMenu() {
                console.log('menu');
                $http({method: 'GET', url: 'app/template/menu.json'}).success(function (data) {
                    $scope.menus = data;
                    console.log('menu ss');
                });
            }

            function _getPath() {
                return '#' + $location.path();
            }
        });
}());

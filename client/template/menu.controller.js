'use strict';

angular.module('sicobaApp')
    .controller('MenuCtrl', function ($scope, $http, $location) {

        $scope.getPath = _getPath;

        _loadMenu();

        function _loadMenu() {
            $http({method: 'GET', url: '/template/menu.json'}).success(function (data) {
                $scope.menus = data;
            });
        }

        function _getPath() {
            return '#' + $location.path();
        }
    });
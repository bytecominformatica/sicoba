(function () {
    'use strict';

    angular.module('sicobaApp')
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/logout', {
                    template: '<div> Bye bye!</div>',
                    controller: function ($scope, $http) {
                        console.log('teste2');
                        $http.post('/logout');
                        console.log('teste');
                    }
                });
        }]);
}());

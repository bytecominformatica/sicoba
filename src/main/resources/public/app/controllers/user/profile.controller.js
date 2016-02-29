(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('ProfileCtrl', function ($scope, $rootScope, $routeParams, User) {

            $scope.save = _save;

            _init();

            function _init() {
                User.logged({}, function(userLogged){
                    $scope.profile = User.findByUsername({username: userLogged.name});
                });
            }

            function _save(profile) {
                User.save(profile, function (data) {
                    $scope.profile = data;
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: 'Seu perfil foi atualizado.',
                        type: 'alert-success'
                    }];
                });
            }
        });
}());

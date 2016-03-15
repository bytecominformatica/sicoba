(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('RetornoCtrl', function ($scope, $rootScope, Upload) {

            $scope.enviar = _enviar;

            function _enviar(files) {
                files.forEach(function (file) {
                    Upload.upload({
                        url: 'api/retornos/upload',
                        data: {file: file}
                    }).then(function (resp) {
                        file.retorno = resp.data;
                    }, function (resp) {
                        console.log(resp);
                        file.error = resp.data.message;
                    }, function (evt) {
                        file.progress = parseInt(100.0 * evt.loaded / evt.total);
                    });
                });
            }
        });
}());

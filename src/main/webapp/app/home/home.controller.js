'use strict';

angular.module('sicobaApp')
    .controller('HomeCtrl', function ($scope, Mensalidade) {

        _buscarMensalidadesAtrasada();

        function _buscarMensalidadesAtrasada() {
            $scope.mensalidadesAtrasada = Mensalidade.atrasados();
        }
    });
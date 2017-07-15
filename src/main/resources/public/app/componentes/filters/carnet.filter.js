(function () {
    'use strict';

    angular.module('sicobaApp')
        .filter('carnetStatusName', function () {
            return function (data) {
                return _getStatusName(data);
            };
        })
        .filter('carnetStatusDescription', function () {
            return function (data) {
                return _getStatusDescription(data);
            };
        })
        .filter('carnetStatusLabelClass', function () {
            return function (data) {
                return _getStatusLabelClass(data);
            };
        });

    var allStatus = {
        ACTIVE: {
            name: 'Ativo',
            labelClass: 'label-success',
            description: 'Carnê ativo.'
        },
        EXPIRED: {
            name: 'Expirado',
            labelClass: 'label-warning',
            description: 'Carnê expirado. A data de vencimento da última parcela do carnê foi ultrapassada.'
        },
        CANCELED: {
            name: 'Cancelado',
            labelClass: 'label-danger',
            description: 'Carnê cancelado.'
        },
        UP_TO_DATE: {
            name: 'Em dia',
            labelClass: 'label-success',
            description: 'O carnê encontra-se em dia.'
        },
        UNPAID: {
            name: 'Não pago',
            labelClass: 'label-warning',
            description: 'O carnê encontra-se inadimplente.'
        },
        FINISHED: {
            name: 'Finalizado',
            labelClass: 'label-success',
            description: 'O carnê está finalizado.'
        }
    };

    function _getStatusLabelClass(carnet) {
        return allStatus[carnet.status].labelClass;
    }

    function _getStatusName(carnet) {
        return allStatus[carnet.status].name;
    }

    function _getStatusDescription(carnet) {
        return allStatus[carnet.status].description;
    }

}());

(function () {
    'use strict';

    angular.module('sicobaApp')
        .filter('chargeStatusName', function () {
            return function (data) {
                return _getStatusName(data);
            };
        })
        .filter('chargeStatusDescription', function () {
            return function (data) {
                return _getStatusDescription(data);
            };
        })
        .filter('chargeStatusLabelClass', function () {
            return function (data) {
                return _getStatusLabelClass(data);
            };
        });

    var allStatus = {
        NEW: {
            name: 'Novo',
            labelClass: 'label-warning',
            description: 'Cobrança gerada, aguardando definição da forma de pagamento.'
        },
        WAITING: {
            name: 'Aguardando',
            labelClass: 'label-info',
            description: 'Forma de pagamento selecionada, aguardando a confirmação do pagamento.'
        },
        PAID: {name: 'Pago', labelClass: 'label-success', description: 'Pagamento confirmado.'},
        UNPAID: {name: 'Não Pago', labelClass: 'label-warning', description: 'Boleto não foi pago até o momento.'},
        REFUNDED: {
            name: 'Devolvido',
            labelClass: 'label-warning',
            description: 'Pagamento devolvido pelo lojista ou pelo intermediador Gerencianet.'
        },
        CONTESTED: {
            name: 'Contestado',
            labelClass: 'label-warning',
            description: 'Pagamento em processo de contestação.'
        },
        CANCELED: {
            name: 'Cancelado',
            labelClass: 'label-danger',
            description: 'Cobrança cancelada pelo vendedor ou pelo pagador.'
        },
        LINK: {name: 'Link', labelClass: 'label-info', description: 'Cobrança associada a um link de pagamento.'}
    };

    function _getStatusLabelClass(charge) {
        return charge.manualPayment ? allStatus.PAID.labelClass : allStatus[charge.status].labelClass;
    }

    function _getStatusName(charge) {
        return charge.manualPayment ? "Pagamento manual" : allStatus[charge.status].name;
    }

    function _getStatusDescription(charge) {
        return charge.manualPayment ? "Pagamento efetuado diretamente na empresa." : allStatus[charge.status].description;
    }

}());

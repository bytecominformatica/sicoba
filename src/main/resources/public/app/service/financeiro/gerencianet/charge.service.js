/**
 * Created by clairton on 19/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .factory('Charge', ['$resource', function ($resource) {
            return $resource('api/charges/:id/:action', {id: '@id'},
                {
                    new: {
                        method: 'GET',
                        url: 'api/charges/new'
                    },
                    overdue: {
                        method: 'GET',
                        params: {id: "overdue"},
                        isArray: true
                    },
                    current: {
                        method: 'GET',
                        params: {id: "current"},
                        isArray: true
                    },
                    bankingBillet: {
                        method: 'POST',
                        params: {action: "pay"}
                    },
                    paymentLink: {
                        method: 'POST',
                        params: {action: "link"}
                    },
                    cancel: {
                        method: 'PUT',
                        params: {action: "cancel"}
                    },
                    manualPayment: {
                        method: 'PUT',
                        params: {action: "manualpayment"}
                    },
                    updateExpireAt: {
                        method: 'PUT',
                        params: {action: "billet"}
                    },
                    refreshUrlsNotification: {
                        method: 'PUT',
                        params: {id: "all", action: "metadata"}
                    },
                    findByPaymentDate: {
                        method: 'GET',
                        url: 'api/charges/paymentdate',
                        isArray: true
                    },
                    findByExpirationDate: {
                        method: 'GET',
                        url: 'api/charges/expirationdate',
                        isArray: true
                    }
                });
        }]);
}());

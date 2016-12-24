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
                    refreshUrlsNotification: {
                        method: 'PUT',
                        params: {id: "all", action: "metadata"}
                    }
                });
        }]);
}());

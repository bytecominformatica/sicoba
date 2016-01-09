/**
 * Created by clairton on 29/12/15.
 */
(function () {
    'use strict';

    angular.module('sicobaApp')
        .directive('sicobaAlert', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/directives/alert/alert.html'
            };
        });

}());

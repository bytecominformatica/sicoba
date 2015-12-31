/**
 * Created by clairton on 29/12/15.
 */
angular.module('sicobaApp')
    .directive('sicobaAlert', function () {
        return {
            restrict: 'E',
            templateUrl: 'app/directives/alert/alert.html'
        };
    });


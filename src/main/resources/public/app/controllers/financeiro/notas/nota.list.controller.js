(function () {
    'use strict';

    angular.module('sicobaApp')
        .controller('NotaListCtrl', ['$scope', '$rootScope', '$http', 'Notas', 'GerencianetAccount', function ($scope, $rootScope, $http, Notas, GerencianetAccount) {
            $scope.findItensByDateOfProvision = _findItensByDateOfProvision;
            $scope.toogle = _toogle;
            $scope.totalSelecionado = _totalSelecionado;
            $scope.gerarArquivoSyncNfe = _gerarArquivoSyncNfe;
            $scope.removerSelecionados = _removerSelecionados;

            _init();

            function _init() {
                $scope.params = {
                    start: new Date(),
                    end: new Date()
                };

                $scope.statusList = [
                    {value: 'NEW', description: 'Novo'},
                    {value: 'WAITING', description: 'Aguardando'},
                    {value: 'PAID', description: 'Pago'},
                    {value: 'PAYMENT MANUAL', description: 'Pagamento Manual'},
                    {value: 'UNPAID', description: 'Não Pago'},
                    {value: 'REFUNDED', description: 'Devolvido'},
                    {value: 'CONTESTED', description: 'Contestado'},
                    {value: 'CANCELED', description: 'Cancelado'},
                    {value: 'LINK', description: 'Link'}
                ];

                $scope.accounts = GerencianetAccount.query();
            }

            function _toogle() {
                if ($scope.nfeItemList) {
                    $scope.nfeItemList.forEach(c => {
                        c.selected = $scope.allSelected;
                    })
                }
            }

            function _totalSelecionado() {
                if (!$scope.nfeItemList) return 0;
                return getItensSelecionadas().length;
            }

            function getItensSelecionadas() {
                return $scope.nfeItemList.filter(c => c.selected);
            }

            function _gerarArquivoSyncNfe() {
                let itensSelecionados = getItensSelecionadas();
                $http.post('api/notas/syncnfe/files', itensSelecionados, {responseType: 'arraybuffer'})
                    .then(function (response) {
                        var file = new Blob([response.data], {type: 'application/zip'});
                        var url = window.URL.createObjectURL(file);
                        var a = document.createElement('a');
                        a.href = url;
                        a.download = 'syncnfe.zip';
                        a.click();
                        setTimeout(() => window.URL.revokeObjectURL(url), 100);
                    });
            }

            function _removerSelecionados() {
                let itensSelecionados = getItensSelecionadas();
                let idsSelecionados = itensSelecionados.map(it => it.id);

                let params = "";

                if (idsSelecionados.length) {
                    params = idsSelecionados
                        .reduce((prev, current) => prev + '&id=' + current, '')
                        .replace("&", "");
                }

                Notas.removeAll({params: params}, () => {
                    $rootScope.messages = [{
                        title: 'Sucesso:',
                        body: `${itensSelecionados.length} nota(s) removidas com sucesso`,
                        type: 'alert-success'
                    }];

                    _findItensByDateOfProvision($scope.params);
                });
            }

            function _findItensByDateOfProvision(params) {
                var params2 = {
                    start: moment(params.start).format('YYYY-MM-DD'),
                    end: moment(params.end).format('YYYY-MM-DD'),
                    status: params.status
                };

                if (params.gerencianetAccount)
                    params2.gerencianetAccount = params.gerencianetAccount.id;

                $scope.nfeItemList = Notas.findItensByDateOfProvision(params2);
            }

        }]);
}());

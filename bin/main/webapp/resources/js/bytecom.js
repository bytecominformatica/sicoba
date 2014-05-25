jQuery(function($) {
	$(".data").mask("99/99/9999");
	$(".phone").mask("(99)9999-9999");
	$(".cpf").mask("999.999.999-99");
	$(".cnpj").mask("99.999.999/9999-99");
	$.mask.definitions['X'] = "[A-Fa-f0-9]";
	$(".mac").mask("XX:XX:XX:XX:XX:XX");
});

function submitLink(event, linkId){
	if (event.keyCode == 13) {document.getElementById(linkId).click(); return false; }
};

jQuery(function($) {
	$(".focus").focus();
});

$(function() {
    $(".data").datepicker({
        dateFormat: 'dd/mm/yy',
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
        dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
        nextText: 'Próximo',
        prevText: 'Anterior'
    });
});


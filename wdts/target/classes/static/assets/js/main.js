
function close_sidebar() {
    $('.sidebar').addClass('sidebar-mini').addClass('overflow-hidden');
    window.setTimeout(function(){
        $('.sidebar').removeClass('overflow-hidden');
    },300);
}
function toggle_sidebar() {
    if($(window).width() < 1000) {
        close_sidebar();
    }else{
        $('.sidebar').removeClass('sidebar-mini');
    }
}
$('.toggle-sidebar').click(function () {
    if($('.sidebar').hasClass('sidebar-mini')) {
        $('.sidebar').removeClass('sidebar-mini');
    }else {
        close_sidebar();
    }
});
$(window).ready(function () {
    toggle_sidebar();
});
$(window).resize(function () {
    toggle_sidebar();
});

$(".datetimepicker").datetimepicker({
    format: "yyyy-mm-dd hh:ii:ss",
    language: 'zh-CN',
    autoclose: true,
    minuteStep: 5
});

$(".datepicker").datetimepicker({
    format: "yyyy-mm-dd",
    language: 'zh-CN',
    autoclose: true,
    minView: 2
});
$('[data-toggle="tooltip"]').tooltip();
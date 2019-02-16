$(document).ajaxSend(function (e, xhr, options) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    xhr.setRequestHeader(header, token);
    var _context_path = $("meta[name='_context_path']").attr("content");
    if ($.trim(_context_path) != '') {
        var url = options.url;
        options.url = _context_path + url+"/";
    }
});
$(document).ajaxError(function (e, xhr, options) {
    if (xhr.status == 403) {
        layer.msg('权限不足');
    }
    if (xhr.status == 500) {
        layer.msg('出错啦');
    }
});
$(document).ajaxSuccess(function (event, xhr, options) {
    if (options.dataType == 'json') {
        var result = xhr.responseJSON;
        var code = result.code;
        $(".has-error").removeClass('has-error');
        if (code == 600) {
            result.data.forEach(function (item) {
                $("[name='" + item.field + "']").addClass("has-error");
                $("[name='" + item.rejectedValue + "']").addClass("has-error");
            });
            $(".has-error").on('change', function () {
                var field = $(this).attr('name');
                $("[name='" + field + "']").removeClass('has-error');
            })
        } else if (code == 404 || code == 500 || code == 601) {
            layer.alert(result.message, {icon: 2});
        }
    }
});
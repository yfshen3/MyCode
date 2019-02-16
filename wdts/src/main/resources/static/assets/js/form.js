function serializeForm($form) {
    var array = [];
    var hasError = false;
    $form.find('input[type=hidden],input[type=text],textarea,select').each(function () {
        var name = $(this).attr('name');
        var value = $(this).val();

        if (!name || $.trim(name) == '') {
            $(this).addClass('has-error');
            console.error("模板错误: name 为空");
            hasError = true;
            return true;
        }
        var obj = {
            "pId": $("#pId").val(),
            "name": name,
            "value": value
        };
        array.push(obj);
    });
    $form.find('input[type=radio],input[type=checkbox]').each(function () {
        var isChecked = $(this).is(":checked");
        var name = $(this).attr('name');
        var value = $(this).val();
        if (!name || $.trim(name) == '') {
            $(this).addClass('has-error');
            console.error("模板错误: name 为空");
            hasError = true;
            return true;
        }
        var obj;
        if (!isChecked) {
            value ='';
        }
        obj = {
            "pId": $("#pId").val(),
            "name": name,
            "value": value
        };
        array.push(obj);
    });
    if (hasError) {
        alert("模板错误");
        return [];
    }
    return array;
}
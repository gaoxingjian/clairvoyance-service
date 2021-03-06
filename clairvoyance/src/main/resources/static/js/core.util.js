/*CoreUtil*/
/*工具类，类似java静态工具类*/
var CoreUtil = (function () {
    var coreUtil = {};
    /*ajax请求*/
    coreUtil.sendAjax = function (url, params, ft, method, headers, noAuthorityFt, contentType, async) {
        var roleSaveLoading = top.layer.msg('Data submission, please wait', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            url: url,
            cache: false,
            async: async == undefined ? true : async,
            data: params,
            type: method == undefined ? "POST" : method,
            contentType: contentType == undefined ? 'application/json; charset=UTF-8' : contentType,
            dataType: "json",
            beforeSend: function (request) {
                if (headers == undefined) {

                } else if (headers) {
                    request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
                    request.setRequestHeader("refresh_token", CoreUtil.getData("refresh_token"));
                } else {
                    request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
                }

            },
            success: function (res) {
                top.layer.close(roleSaveLoading);
                if (typeof ft == "function") {
                    if (res.code == 401001) { //凭证过期重新登录
                        layer.msg("Certificate expired, please login again")
                        CoreUtil.clearData();
                        top.window.location.href = "/login"
                    } else if (res.code == 401002) {  //根据后端提示刷新token
                        /*记录要重复刷新的参数*/
                        var reUrl = url;
                        var reParams = params;
                        var reFt = ft;
                        var reMethod = method;
                        var reHeaders = headers;
                        var reNoAuthorityFt = noAuthorityFt;
                        var reContentType = contentType;
                        var reAsync = async;
                        /*刷新token  然后存入缓存*/
                        CoreUtil.sendAjax("/user/token", null, function (res) {
                            if (res.code == 0) {
                                CoreUtil.setData("access_token", res.data);
                                /*刷新成功后继续重复请求*/
                                CoreUtil.sendAjax(reUrl, reParams, reFt, reMethod, reHeaders, reNoAuthorityFt, reContentType, reAsync);
                            } else {
                                layer.msg("Certificate expired, please login again");
                                top.window.location.href = "/login"
                            }
                        }, "GET", true)
                    } else if (res.code == 0) {
                        if (ft != null && ft != undefined) {
                            ft(res);
                        }

                    } else if (res.code == 401008) {//无权限响应
                        if (ft != null && ft != undefined) {
                            noAuthorityFt(res);
                        }

                    } else {
                        layer.msg(res.msg)
                    }

                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                top.layer.close(roleSaveLoading);
                if (XMLHttpRequest.status == 404) {
                    top.window.location.href = "/404";
                } else {
                    layer.msg("There seems to be something wrong with the server! Please try later");
                }
            }
        });
    };
    /*表单数据封装成 json String*/
    coreUtil.formJson = function (frm) {  //frm：form表单的id
        var o = {};
        var a = $("#" + frm).serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON.stringify(o);
    };
    /*存入本地缓存*/
    coreUtil.setData = function (key, value) {
        layui.data('LocalData', {
            key: key,
            value: value
        })
    };
    /*从本地缓存拿数据*/
    coreUtil.getData = function (key) {
        var localData = layui.data('LocalData');
        return localData[key];
    };
    /*清除本地缓存*/
    coreUtil.clearData = function () {
        layui.data('LocalData', null)
    }


    coreUtil.formattime = function (val) {
        var date = new Date(val);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = month > 9 ? month : ('0' + month);
        var day = date.getDate();
        day = day > 9 ? day : ('0' + day);
        var hh = date.getHours();
        hh = hh > 9 ? hh : ('0' + hh);
        var mm = date.getMinutes();
        mm = mm > 9 ? mm : ('0' + mm);
        var ss = date.getSeconds();
        ss = ss > 9 ? ss : ('0' + ss);
        var time = year + '-' + month + '-' + day + ' ' + hh + ':' + mm + ':' + ss;
        return time;
    };

    coreUtil.load = function () {

        if (CoreUtil.getData("access_token")) {
            CoreUtil.sendAjax("/user", null, function (res) {
                $("#top").html(res.data.username);
                $("#top").attr("href", "#");
                let ulstring = "<li><button class='btn btn-success' style='background-color:#84A9AC;border: 0' onclick='logoutDialog()'>Logout</button></li>";
                $("#top").popover({
                    trigger: 'manual',//manual 触发方式
                    placement: 'bottom',
                    html: 'true',
                    content: ulstring,  //这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
                    animation: false
                }).on("mouseenter", function () {
                    var _this = this;
                    $(this).popover("show");
                    $(this).siblings(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }).on("mouseleave", function () {
                    var _this = this;
                    setTimeout(function () {
                        if (!$(".popover:hover").length) {
                            $(_this).popover("hide")
                        }
                    }, 100);
                });

            }, "GET", false)
        }else {
            $("#top").html("Login");
        }


    }


    return coreUtil;
})(CoreUtil, window);


let logoutDialog = function () {
    layer.open({
        content: 'Confirm Logout?',
        yes: function (index, layero) {
            layer.close(index); //如果设定了yes回调，需进行手工关闭
            CoreUtil.sendAjax("/user/logout", null, function (res) {
                CoreUtil.clearData();
                top.window.location.href = "/login"
            }, "GET", true)
        }
    })
}